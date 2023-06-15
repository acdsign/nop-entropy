package io.nop.batch.jdbc.consumer;

import io.nop.batch.core.IBatchConsumer;
import io.nop.core.lang.sql.SQL;
import io.nop.core.reflect.bean.BeanTool;
import io.nop.dao.dialect.IDialect;
import io.nop.dao.jdbc.IJdbcTemplate;
import io.nop.dao.jdbc.JdbcBatcher;
import io.nop.dataset.binder.IDataParameterBinder;

import java.util.List;
import java.util.Map;

public class JdbcInsertBatchConsumer<S, C> implements IBatchConsumer<S, C> {
    private final IJdbcTemplate jdbcTemplate;
    private final IDialect dialect;
    private final String tableName;

    private final Map<String, IDataParameterBinder> colBinders;

    public JdbcInsertBatchConsumer(IJdbcTemplate jdbcTemplate, IDialect dialect, String tableName,
                                   Map<String, IDataParameterBinder> colBinders) {
        this.jdbcTemplate = jdbcTemplate;
        this.dialect = dialect;
        this.tableName = tableName;
        this.colBinders = colBinders;
    }

    @Override
    public void consume(List<S> items, C context) {
        SQL sql = SQL.begin().name("batch-insert").insertInto(tableName).end();

        jdbcTemplate.runWithConnection(sql, conn -> {
            JdbcBatcher batcher = new JdbcBatcher(conn, dialect, jdbcTemplate.getDaoMetrics());
            for (S item : items) {
                SQL insert = buildInsert(item);
                batcher.addCommand(insert,false,null);
            }
            batcher.flush();
            return null;
        });
    }

    SQL buildInsert(S record) {
        SQL.SqlBuilder sb = SQL.begin().name("insert:" + tableName);
        sb.insertInto(tableName);
        sb.append('(');
        boolean first = true;
        for (String colName : colBinders.keySet()) {
            if (!BeanTool.hasProperty(record, colName))
                continue;

            if (first) {
                first = false;
            } else {
                sb.append(',');
            }
            sb.append(colName);
        }
        sb.append(") values (");
        first = true;
        for (Map.Entry<String, IDataParameterBinder> entry : colBinders.entrySet()) {
            if (!BeanTool.hasProperty(record, entry.getKey()))
                continue;
            if (first) {
                first = false;
            } else {
                sb.append(',');
            }

            appendParam(sb, entry.getValue(), BeanTool.getProperty(record, entry.getKey()));
        }
        sb.append(')');
        return sb.end();
    }

    void appendParam(SQL.SqlBuilder sb, IDataParameterBinder binder, Object value) {
        value = binder.getStdDataType().convert(value);
        sb.typeParam(binder, value, false);
    }
}
