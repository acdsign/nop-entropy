package io.nop.task.step;

import io.nop.task.ITaskContext;
import io.nop.task.ITaskStepImplementor;
import io.nop.task.ITaskStepState;
import io.nop.task.TaskStepResult;

public class SimpleTaskStep extends AbstractTaskStep {
    private ITaskStepImplementor stepImplementor;

    public void setStepImplementor(ITaskStepImplementor stepImplementor) {
        this.stepImplementor = stepImplementor;
    }

    @Override
    protected void initStepState(ITaskStepState state, ITaskContext context) {
        stepImplementor.prepareState(this, state, context);
    }

    @Override
    protected TaskStepResult doExecute(ITaskStepState state, ITaskContext context) {
        return stepImplementor.execute(this, state, context);
    }
}