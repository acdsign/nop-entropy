package io.nop.wf.core.model._gen;

import io.nop.commons.collections.KeyedList; //NOPMD - suppressed UnusedImports - Used for List Prop
import io.nop.core.lang.json.IJsonHandler;



// tell cpd to start ignoring code - CPD-OFF
/**
 * generate from [32:6:0:0]/nop/schema/wf/wf.xdef <p>
 * 
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable",
    "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement"})
public abstract class _WfEndModel extends io.nop.core.resource.component.AbstractComponentModel {
    
    /**
     *  
     * xml name: output
     * 返回给父流程的值
     */
    private KeyedList<io.nop.wf.core.model.WfReturnVarModel> _outputs = KeyedList.emptyList();
    
    /**
     *  
     * xml name: source
     * 
     */
    private io.nop.core.lang.eval.IEvalAction _source ;
    
    /**
     * 
     * xml name: output
     *  返回给父流程的值
     */
    
    public java.util.List<io.nop.wf.core.model.WfReturnVarModel> getOutputs(){
      return _outputs;
    }

    
    public void setOutputs(java.util.List<io.nop.wf.core.model.WfReturnVarModel> value){
        checkAllowChange();
        
        this._outputs = KeyedList.fromList(value, io.nop.wf.core.model.WfReturnVarModel::getName);
           
    }

    
    public io.nop.wf.core.model.WfReturnVarModel getOutput(String name){
        return this._outputs.getByKey(name);
    }

    public boolean hasOutput(String name){
        return this._outputs.containsKey(name);
    }

    public void addOutput(io.nop.wf.core.model.WfReturnVarModel item) {
        checkAllowChange();
        java.util.List<io.nop.wf.core.model.WfReturnVarModel> list = this.getOutputs();
        if (list == null || list.isEmpty()) {
            list = new KeyedList<>(io.nop.wf.core.model.WfReturnVarModel::getName);
            setOutputs(list);
        }
        list.add(item);
    }
    
    public java.util.Set<String> keySet_outputs(){
        return this._outputs.keySet();
    }

    public boolean hasOutputs(){
        return !this._outputs.isEmpty();
    }
    
    /**
     * 
     * xml name: source
     *  
     */
    
    public io.nop.core.lang.eval.IEvalAction getSource(){
      return _source;
    }

    
    public void setSource(io.nop.core.lang.eval.IEvalAction value){
        checkAllowChange();
        
        this._source = value;
           
    }

    

    public void freeze(boolean cascade){
        if(frozen()) return;
        super.freeze(cascade);

        if(cascade){ //NOPMD - suppressed EmptyControlStatement - Auto Gen Code
        
           this._outputs = io.nop.api.core.util.FreezeHelper.deepFreeze(this._outputs);
            
        }
    }

    protected void outputJson(IJsonHandler out){
        super.outputJson(out);
        
        out.put("outputs",this.getOutputs());
        out.put("source",this.getSource());
    }
}
 // resume CPD analysis - CPD-ON
