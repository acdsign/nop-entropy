/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.xlang.exec;

import io.nop.api.core.util.Guard;
import io.nop.api.core.util.SourceLocation;
import io.nop.core.lang.eval.IEvalFunction;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.lang.eval.IExecutableExpression;
import io.nop.core.lang.eval.IExpressionExecutor;

public class ResolvedObjFunctionExecutable extends AbstractObjFunctionExecutable {
    protected final IEvalFunction func;

    protected ResolvedObjFunctionExecutable(SourceLocation loc, IExecutableExpression objExpr, String funcName,
                                            boolean optional,
                                            IExecutableExpression[] args, IEvalFunction func) {
        super(loc, objExpr, funcName, optional, args);
        this.func = Guard.notNull(func, "func is null");
    }

    public static ResolvedObjFunctionExecutable build(SourceLocation loc, IExecutableExpression objExpr,
                                                      String funcName, boolean optional,
                                                      IExecutableExpression[] args, IEvalFunction func) {
        switch (args.length) {
            case 0:
                return new NoArgExecutable(loc, objExpr, funcName, optional, args, func);
            case 1:
                return new OneArgExecutable(loc, objExpr, funcName, optional, args, func);
            case 2:
                return new TwoArgExecutable(loc, objExpr, funcName, optional, args, func);
            case 3:
                return new ThreeArgExecutable(loc, objExpr, funcName, optional, args, func);
            default:
                return new ResolvedObjFunctionExecutable(loc, objExpr, funcName, optional, args, func);
        }
    }

    @Override
    public Object execute(IExpressionExecutor executor, IEvalScope scope) {
        Object obj = executor.execute(objExpr, scope);
        if (obj == null)
            return null;

        Object[] argValues = new Object[args.length];
        for (int i = 0, n = args.length; i < n; i++) {
            argValues[i] = executor.execute(args[i], scope);
        }

        return doInvoke(func, obj, argValues, scope);
    }

    static class NoArgExecutable extends ResolvedObjFunctionExecutable {
        public NoArgExecutable(SourceLocation loc, IExecutableExpression objExpr, String funcName,
                               boolean optional,
                               IExecutableExpression[] args, IEvalFunction func) {
            super(loc, objExpr, funcName, optional, args, func);
        }

        @Override
        public Object execute(IExpressionExecutor executor, IEvalScope scope) {
            Object obj = executor.execute(objExpr, scope);
            if (obj == null)
                return null;
            return doInvoke0(func, obj, scope);
        }
    }

    static class OneArgExecutable extends ResolvedObjFunctionExecutable {
        private final IExecutableExpression argExpr;

        public OneArgExecutable(SourceLocation loc, IExecutableExpression objExpr, String funcName,
                                boolean optional,
                                IExecutableExpression[] args, IEvalFunction func) {
            super(loc, objExpr, funcName, optional, args, func);
            this.argExpr = args[0];
        }

        @Override
        public Object execute(IExpressionExecutor executor, IEvalScope scope) {
            Object obj = executor.execute(objExpr, scope);
            if (obj == null)
                return null;

            Object arg = executor.execute(argExpr, scope);

            return doInvoke1(func, obj, arg, scope);
        }
    }

    static class TwoArgExecutable extends ResolvedObjFunctionExecutable {
        private final IExecutableExpression argExpr1;
        private final IExecutableExpression argExpr2;

        public TwoArgExecutable(SourceLocation loc, IExecutableExpression objExpr, String funcName,
                                boolean optional,
                                IExecutableExpression[] args, IEvalFunction func) {
            super(loc, objExpr, funcName, optional, args, func);
            this.argExpr1 = args[0];
            this.argExpr2 = args[1];
        }

        @Override
        public Object execute(IExpressionExecutor executor, IEvalScope scope) {
            Object obj = executor.execute(objExpr, scope);
            if (obj == null)
                return null;

            Object arg1 = executor.execute(argExpr1, scope);
            Object arg2 = executor.execute(argExpr2, scope);

            return doInvoke2(func, obj, arg1, arg2, scope);
        }
    }

    static class ThreeArgExecutable extends ResolvedObjFunctionExecutable {
        private final IExecutableExpression argExpr1;
        private final IExecutableExpression argExpr2;
        private final IExecutableExpression argExpr3;

        public ThreeArgExecutable(SourceLocation loc, IExecutableExpression objExpr, String funcName,
                                  boolean optional,
                                  IExecutableExpression[] args, IEvalFunction func) {
            super(loc, objExpr, funcName, optional, args, func);
            this.argExpr1 = args[0];
            this.argExpr2 = args[1];
            this.argExpr3 = args[2];
        }

        @Override
        public Object execute(IExpressionExecutor executor, IEvalScope scope) {
            Object obj = executor.execute(objExpr, scope);
            if (obj == null)
                return null;

            Object arg1 = executor.execute(argExpr1, scope);
            Object arg2 = executor.execute(argExpr2, scope);
            Object arg3 = executor.execute(argExpr3, scope);

            return doInvoke3(func, obj, arg1, arg2, arg3, scope);
        }
    }
}
