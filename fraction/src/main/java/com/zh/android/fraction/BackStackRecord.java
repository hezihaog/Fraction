package com.zh.android.fraction;

import java.util.ArrayList;
import java.util.List;

/**
 * Fraction的事务实现
 */
public class BackStackRecord extends FractionTransaction {
    /**
     * Fraction管理器
     */
    private final FractionManager mFractionManager;

    /**
     * 待执行的操作列表
     */
    protected final List<Op> mOps = new ArrayList<>();

    /**
     * 事务操作命令
     */
    static int OP_ADD = 1;
    static int OP_REPLACE = 2;
    static int OP_REMOVE = 3;
    static int OP_HIDE = 4;
    static int OP_SHOW = 5;
    static int OP_ATTACH = 6;

    /**
     * 事务操作
     */
    static class Op {
        int cmd;
        Fraction fraction;

        public Op(int cmd, Fraction fraction) {
            this.cmd = cmd;
            this.fraction = fraction;
        }
    }

    public BackStackRecord(FractionManager fractionManager) {
        this.mFractionManager = fractionManager;
    }

    @Override
    public void commit() {
        executeOp();
    }

    /**
     * 执行Op操作
     */
    private void executeOp() {
        for (Op mOp : mOps) {
            if (mOp.cmd == OP_ADD) {
                mFractionManager.addFraction(mOp.fraction);
            } else if (mOp.cmd == OP_REMOVE) {
                mFractionManager.removeFraction(mOp.fraction);
            } else if (mOp.cmd == OP_REPLACE) {
                mFractionManager.replaceFraction(mOp.fraction);
            } else if (mOp.cmd == OP_HIDE) {
                mFractionManager.hideFraction(mOp.fraction);
            } else if (mOp.cmd == OP_SHOW) {
                mFractionManager.showFraction(mOp.fraction);
            } else if (mOp.cmd == OP_ATTACH) {
                mFractionManager.attachFraction(mOp.fraction);
            }
        }
        mOps.clear();
    }

    @Override
    public FractionTransaction add(int containerId, Fraction fraction) {
        doAddOp(containerId, fraction, null);
        return this;
    }

    @Override
    public FractionTransaction add(int containerId, Fraction fraction, String tag) {
        doAddOp(containerId, fraction, tag);
        return this;
    }

    @Override
    public FractionTransaction attach(Fraction fraction) {
        doAttachOp(fraction);
        return this;
    }

    @Override
    public FractionTransaction replace(int containerId, Fraction fraction) {
        doReplaceOp(containerId, fraction, null);
        return this;
    }

    @Override
    public FractionTransaction replace(int containerId, Fraction fraction, String tag) {
        doReplaceOp(containerId, fraction, tag);
        return this;
    }

    @Override
    public FractionTransaction remove(Fraction fraction) {
        doRemoveOp(fraction);
        return this;
    }

    @Override
    public FractionTransaction show(Fraction fraction) {
        doShowOp(fraction);
        return this;
    }

    @Override
    public FractionTransaction hide(Fraction fraction) {
        doHideOp(fraction);
        return this;
    }

    //------------------------------ 具体操作 ------------------------------

    void doShowOp(Fraction fraction) {
        if (fraction == null) {
            throw new NullPointerException("fraction is null");
        }
        addOp(new Op(OP_SHOW, fraction));
    }

    void doHideOp(Fraction fraction) {
        if (fraction == null) {
            throw new NullPointerException("fraction is null");
        }
        addOp(new Op(OP_HIDE, fraction));
    }

    void doRemoveOp(Fraction fraction) {
        if (fraction == null) {
            throw new NullPointerException("fraction is null");
        }
        addOp(new Op(OP_REMOVE, fraction));
    }

    void doReplaceOp(int containerId, Fraction fraction, String tag) {
        if (fraction == null) {
            throw new NullPointerException("fraction is null");
        }
        if (tag != null) {
            fraction.mTag = tag;
        }
        fraction.mContainerId = containerId;
        addOp(new Op(OP_REPLACE, fraction));
    }

    void doAddOp(int containerId, Fraction fraction, String tag) {
        if (fraction == null) {
            throw new NullPointerException("fraction is null");
        }
        if (tag != null) {
            fraction.mTag = tag;
        }
        fraction.mContainerId = containerId;
        addOp(new Op(OP_ADD, fraction));
    }

    void doAttachOp(Fraction fraction) {
        if (fraction == null) {
            throw new NullPointerException("fraction is null");
        }
        addOp(new Op(OP_ATTACH, fraction));
    }

    void addOp(Op op) {
        mOps.add(op);
    }
}