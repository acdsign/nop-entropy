## 1. 表格过滤
问题：希望为表格增加过滤条件，比如只显示type=2的数据，或者只显示用户有权限看到的数据。
存在以下多种解决方案：

### A. 通过数据权限实现过滤
后台内置的findPage、findList和findFirst动作都会应用数据权限检查接口 IDataAuthChecker。
通过nop.auth.enable-data-auth来启用数据权限，缺省为true

* 通过auth模块的数据权限菜单配置数据权限，它对应于NopAuthRoleDataAuth表。
* 在/nop/main/auth/main.data-auth.xml文件中配置数据权限

````xml
<data-auth>
    <objs>
        <obj name="MyEntity">
            <role-auths>
                <role-auth roleId="manager">
                    <filter>
                        <eq name="type" value="1" />
                    </filter>
                </role-auth>
            </role-auths>
        </obj>
    </objs>
</data-auth>
````
具体数据权限配置参见 [auth.md](../auth/auth.md)


### B. 在前台拼接过滤条件
XView模型中定义的grid可以配置filter条件，使用该grid生成的表格查询数据时会自动携带过滤条件。
````
<grid id="list">
  <cols>
    ...
  </cols>
  
  <filter>
    <eq name="type" value="1" />
  </filter>
</grid>    
````

### C. 后台XMeta对象配置过滤条件
xmeta配置文件中可以配置filter过滤条件。如果在meta中配置，则新增、修改的时候也会按照这里的过滤条件自动设置。例如
````
<filter>
   <eq name="type" value="1" />
</filter>
````
则新增和修改的时候都会固定设置type=1。

基于已有的xmeta，可以新建xmeta。例如 MyEntity_ext.xmeta 
```
<meta x:extends="MyEntity.xmeta">
  <filter>
     <eq name="type" value="1" />
  </filter>
</meta>
```
前台调用时使用对象名MyEntity_ext就会自动应用这里的meta。例如 
````
query{
   MyEntity_ext__findPage{
      ...
   }
}
````

### D. 在后台BizModel中增加新的方法
````
class MyEntityBizModel extends CrudBizModel<MyEntity>{
    @BizQuery
    @GraphQLReturn(bizObjName = BIZ_OBJ_NAME_THIS_OBJ)
    public PageBean<T> findPage_ext(@Name("query") @Description("@i18n:biz.query|查询条件") QueryBean query,
                                FieldSelectionBean selection, IServiceContext context) {
        if (query != null)
            query.setDisableLogicalDelete(false);

        return doFindPage(query, this::addExtQuery, selection, context);
    }
    
    protected void defaultPrepareQuery(@Name("query") QueryBean query, IServiceContext context) {
        query.addFilter(FilterBeans.eq(MyEntity.PROP_NAME_status,1));
    }
}
````
在前台可以继承已有的页面，然后定制其中的api数据请求链接
````
<form id="edit">
  <cells>
    <cell id="productId">
       <gen-control>
          return {
                "x:extends":"xxx.page.yaml",
                initApi: {
                   url: "@query:MyEntity__findPage_ext/{@gridSelection}"
                }
            }
       </gen-control>
    </cell>
  </cells>
</form>
````