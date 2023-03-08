/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.core.resource.record.csv;

import io.nop.api.core.exceptions.NopException;
import io.nop.commons.record.IRecordOutput;
import io.nop.commons.util.StringHelper;
import io.nop.core.reflect.bean.BeanTool;
import io.nop.core.resource.IResource;
import io.nop.core.resource.ResourceHelper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

public class CsvRecordOutput<T> implements IRecordOutput<T> {
    private Collection<String> headers;
    private final CSVPrinter writer;
    private boolean headersWritten;
    private long writeCount;

    public CsvRecordOutput(IResource resource, String encoding, CSVFormat format, Collection<String> headers,
                           boolean supportZip) {
        this.headers = headers;
        Writer out = ResourceHelper.toWriter(resource, encoding, supportZip);
        try {
            this.writer = new CSVPrinter(out, format);
        } catch (Exception e) {
            throw NopException.adapt(e);
        }
    }

    @Override
    public void setHeaders(List<String> headers) {
        if (headers != null)
            this.headers = headers;
    }

    public long getWriteCount() {
        return writeCount;
    }

    @Override
    public void writeBatch(Collection<? extends T> records) {
        try {
            if (!headersWritten) {
                writeHeaders();
                headersWritten = true;
            }
            if (records != null) {
                for (T record : records) {
                    write(record);
                }
            }
        } catch (IOException e) {
            throw NopException.adapt(e);
        }
    }

    @Override
    public void write(T record) {
        try {
            if (!headersWritten) {
                writeHeaders();
                headersWritten = true;
            }

            String[] row = new String[headers.size()];
            int index = 0;
            for (String header : headers) {
                Object value = BeanTool.getComplexProperty(record, header);
                row[index++] = toString(value);
            }
            writer.printRecord(row);
            writeCount++;
        } catch (IOException e) {
            throw NopException.adapt(e);
        }
    }

    private String toString(Object value) {
        return StringHelper.toString(value, "");
    }

    private void writeHeaders() throws IOException {
        writer.printRecord(headers.toArray());
    }

    @Override
    public void flush() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw NopException.adapt(e);
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}