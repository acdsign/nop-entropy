package io.nop.ioc.model._gen;

import io.nop.commons.collections.KeyedList; //NOPMD - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from [220:6:0:0]/nop/schema/beans.xdef <p>
 * 指定parent属性时，从parent对应的bean继承配置。但是class/primary/abstract/autowire-candidate/lazy-init/depends-on等属性不会被继承
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement"})
public abstract class _BeanModel extends io.nop.ioc.model.BeanValue {
    
    /**
     *  
     * xml name: abstract
     * 
     */
    private boolean _abstract  = false;
    
    /**
     *  
     * xml name: id
     * 
     */
    private java.lang.String _id ;
    
    /**
     *  
     * xml name: ioc:allow-override
     * 允许覆盖已有的bean的定义。一般情况下bean的id不允许重复。特殊情况下需要强制覆盖时使用这个属性。
     */
    private boolean _iocAllowOverride  = false;
    
    /**
     *  
     * xml name: ioc:default
     * 是否缺省bean。如果设置为true，则相当于自动为bean增加condition-on-missing-bean的条件。
     * 仅当容器中不存在对应名称的bean的定义的时候，此定义才生效。
     */
    private boolean _iocDefault  = false;
    
    /**
     *  
     * xml name: ioc:pointcut
     * 如果bean是interceptor，会检查容器中所有ioc:aop=true的bean，作用于具有指定注解的方法上
     */
    private io.nop.ioc.model.BeanPointcutModel _iocPointcut ;
    
    /**
     *  
     * xml name: ioc:priority
     * 
     */
    private java.lang.Integer _iocPriority ;
    
    /**
     *  
     * xml name: ioc:tags
     * 
     */
    private java.util.Set<java.lang.String> _iocTags ;
    
    /**
     *  
     * xml name: name
     * 类似于alias,可以为bean起多个名称
     */
    private java.util.Set<java.lang.String> _name ;
    
    /**
     *  
     * xml name: primary
     * 
     */
    private boolean _primary  = false;
    
    /**
     * 
     * xml name: abstract
     *  
     */
    
    public boolean isAbstract(){
      return _abstract;
    }

    
    public void setAbstract(boolean value){
        checkAllowChange();
        
        this._abstract = value;
           
    }

    
    /**
     * 
     * xml name: id
     *  
     */
    
    public java.lang.String getId(){
      return _id;
    }

    
    public void setId(java.lang.String value){
        checkAllowChange();
        
        this._id = value;
           
    }

    
    /**
     * 
     * xml name: ioc:allow-override
     *  允许覆盖已有的bean的定义。一般情况下bean的id不允许重复。特殊情况下需要强制覆盖时使用这个属性。
     */
    
    public boolean isIocAllowOverride(){
      return _iocAllowOverride;
    }

    
    public void setIocAllowOverride(boolean value){
        checkAllowChange();
        
        this._iocAllowOverride = value;
           
    }

    
    /**
     * 
     * xml name: ioc:default
     *  是否缺省bean。如果设置为true，则相当于自动为bean增加condition-on-missing-bean的条件。
     * 仅当容器中不存在对应名称的bean的定义的时候，此定义才生效。
     */
    
    public boolean isIocDefault(){
      return _iocDefault;
    }

    
    public void setIocDefault(boolean value){
        checkAllowChange();
        
        this._iocDefault = value;
           
    }

    
    /**
     * 
     * xml name: ioc:pointcut
     *  如果bean是interceptor，会检查容器中所有ioc:aop=true的bean，作用于具有指定注解的方法上
     */
    
    public io.nop.ioc.model.BeanPointcutModel getIocPointcut(){
      return _iocPointcut;
    }

    
    public void setIocPointcut(io.nop.ioc.model.BeanPointcutModel value){
        checkAllowChange();
        
        this._iocPointcut = value;
           
    }

    
    /**
     * 
     * xml name: ioc:priority
     *  
     */
    
    public java.lang.Integer getIocPriority(){
      return _iocPriority;
    }

    
    public void setIocPriority(java.lang.Integer value){
        checkAllowChange();
        
        this._iocPriority = value;
           
    }

    
    /**
     * 
     * xml name: ioc:tags
     *  
     */
    
    public java.util.Set<java.lang.String> getIocTags(){
      return _iocTags;
    }

    
    public void setIocTags(java.util.Set<java.lang.String> value){
        checkAllowChange();
        
        this._iocTags = value;
           
    }

    
    /**
     * 
     * xml name: name
     *  类似于alias,可以为bean起多个名称
     */
    
    public java.util.Set<java.lang.String> getName(){
      return _name;
    }

    
    public void setName(java.util.Set<java.lang.String> value){
        checkAllowChange();
        
        this._name = value;
           
    }

    
    /**
     * 
     * xml name: primary
     *  
     */
    
    public boolean isPrimary(){
      return _primary;
    }

    
    public void setPrimary(boolean value){
        checkAllowChange();
        
        this._primary = value;
           
    }

    

    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
           this._iocPointcut = io.nop.api.core.util.FreezeHelper.deepFreeze(this._iocPointcut);
            
        }
    }

    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.put("abstract",this.isAbstract());
        out.put("id",this.getId());
        out.put("iocAllowOverride",this.isIocAllowOverride());
        out.put("iocDefault",this.isIocDefault());
        out.put("iocPointcut",this.getIocPointcut());
        out.put("iocPriority",this.getIocPriority());
        out.put("iocTags",this.getIocTags());
        out.put("name",this.getName());
        out.put("primary",this.isPrimary());
    }
}
 // resume CPD analysis - CPD-ON
