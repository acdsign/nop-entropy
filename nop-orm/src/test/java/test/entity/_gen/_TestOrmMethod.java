package test.entity._gen;

import io.nop.api.core.convert.ConvertHelper;
import io.nop.orm.IOrmEntitySet;
import io.nop.orm.model.IEntityModel;
import io.nop.orm.support.DynamicOrmEntity;
import io.nop.orm.support.OrmEntitySet;
import test.entity.TestOrmMethod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * : TEST_ORM_METHOD
 */
public class _TestOrmMethod extends DynamicOrmEntity {

    /* : SID INTEGER */
    public static final String PROP_NAME_sid = "sid";
    public static final int PROP_ID_sid = 1;

    /* : NAME VARCHAR */
    public static final String PROP_NAME_name = "name";
    public static final int PROP_ID_name = 2;

    private static int _PROP_ID_BOUND = 3;

    /* relation: */
    public static final String PROP_NAME_methodParams = "methodParams";

    public static final List<String> PK_PROP_NAMES = Arrays.asList(PROP_NAME_sid);
    public static final int[] PK_PROP_IDS = new int[]{PROP_ID_sid};

    private static final String[] PROP_ID_TO_NAME = new String[3];
    private static final Map<String, Integer> PROP_NAME_TO_ID = new HashMap<>();

    static {

        PROP_ID_TO_NAME[PROP_ID_sid] = PROP_NAME_sid;
        PROP_NAME_TO_ID.put(PROP_NAME_sid, PROP_ID_sid);

        PROP_ID_TO_NAME[PROP_ID_name] = PROP_NAME_name;
        PROP_NAME_TO_ID.put(PROP_NAME_name, PROP_ID_name);

    }

    /* : SID */
    private java.lang.Integer _sid;

    /* : NAME */
    private java.lang.String _name;

    public _TestOrmMethod() {
    }

    protected TestOrmMethod newInstance() {
        return new TestOrmMethod();
    }

    @Override
    public TestOrmMethod cloneInstance() {
        TestOrmMethod entity = newInstance();
        orm_forEachInitedProp((value, propId) -> {
            entity.onInitProp(propId);
        });
        return entity;
    }

    @Override
    public String orm_entityName() {
        // 如果存在实体模型对象，则以模型对象上的设置为准
        IEntityModel entityModel = orm_entityModel();
        if (entityModel != null)
            return entityModel.getName();
        return "test.entity.TestOrmMethod";
    }

    @Override
    public int orm_propIdBound() {
        IEntityModel entityModel = orm_entityModel();
        if (entityModel != null)
            return entityModel.getPropIdBound();
        return _PROP_ID_BOUND;
    }

    @Override
    public Object orm_id() {

        return buildSimpleId(PROP_ID_sid);

    }

    @Override
    public boolean orm_isPrimary(int propId) {

        return propId == PROP_ID_sid;

    }

    @Override
    public String orm_propName(int propId) {
        if (propId >= PROP_ID_TO_NAME.length)
            return super.orm_propName(propId);
        String propName = PROP_ID_TO_NAME[propId];
        if (propName == null)
            return super.orm_propName(propId);
        return propName;
    }

    @Override
    public int orm_propId(String propName) {
        Integer propId = PROP_NAME_TO_ID.get(propName);
        if (propId == null)
            return super.orm_propId(propName);
        return propId;
    }

    @Override
    public Object orm_propValue(int propId) {
        switch (propId) {

            case PROP_ID_sid:
                return getSid();

            case PROP_ID_name:
                return getName();

            default:
                return super.orm_propValue(propId);
        }
    }

    @Override
    public void orm_propValue(int propId, Object value) {
        switch (propId) {

            case PROP_ID_sid: {
                java.lang.Integer typedValue = null;
                if (value != null) {
                    typedValue = ConvertHelper.toInteger(value, err -> newTypeConversionError(PROP_NAME_sid));
                }
                setSid(typedValue);
                break;
            }

            case PROP_ID_name: {
                java.lang.String typedValue = null;
                if (value != null) {
                    typedValue = ConvertHelper.toString(value, err -> newTypeConversionError(PROP_NAME_name));
                }
                setName(typedValue);
                break;
            }

            default:
                super.orm_propValue(propId, value);
        }
    }

    @Override
    public void orm_internalSet(int propId, Object value) {
        switch (propId) {

            case PROP_ID_sid: {
                onInitProp(propId);
                this._sid = (java.lang.Integer) value;
                orm_id(); // 如果是设置主键字段，则触发watcher
                break;
            }

            case PROP_ID_name: {
                onInitProp(propId);
                this._name = (java.lang.String) value;

                break;
            }

            default:
                super.orm_internalSet(propId, value);
        }
    }

    /**
     * : SID
     */
    public java.lang.Integer getSid() {
        onPropGet(PROP_ID_sid);
        return _sid;
    }

    /**
     * : SID
     */
    public void setSid(java.lang.Integer value) {
        if (onPropSet(PROP_ID_sid, value)) {
            this._sid = value;
            internalClearRefs(PROP_ID_sid);
            orm_id();
        }
    }

    /**
     * : NAME
     */
    public java.lang.String getName() {
        onPropGet(PROP_ID_name);
        return _name;
    }

    /**
     * : NAME
     */
    public void setName(java.lang.String value) {
        if (onPropSet(PROP_ID_name, value)) {
            this._name = value;
            internalClearRefs(PROP_ID_name);

        }
    }

    private final OrmEntitySet<test.entity.TestOrmMethodParam> _methodParams = new OrmEntitySet<>(this,
            PROP_NAME_methodParams, null, null, test.entity.TestOrmMethodParam.class);

    /**
     * 。 refPropName: , keyProp: {rel.keyProp}
     */
    public IOrmEntitySet<test.entity.TestOrmMethodParam> getMethodParams() {
        return _methodParams;
    }

}
