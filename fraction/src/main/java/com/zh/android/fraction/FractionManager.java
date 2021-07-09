package com.zh.android.fraction;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Fraction管理器
 */
public class FractionManager {
    /**
     * 生命周期分发器
     */
    private final FractionLifecycleDispatcher mFractionLifecycleDispatcher = new FractionLifecycleDispatcher(this);
    /**
     * 父FractionManager
     */
    private FractionManager mParentFractionManager;
    /**
     * 子Fraction列表
     */
    final List<Fraction> mFractions = new LinkedList<>();
    /**
     * 放置Fraction的容器
     */
    private final FractionContainer mFractionContainer;

    public FractionManager(FractionActivity activity) {
        mFractionContainer = new FractionContainer() {
            @Override
            public View findViewById(int viewId) {
                return activity.findViewById(viewId);
            }

            @Override
            public Context getContext() {
                return activity;
            }
        };
    }

    public FractionManager(FractionManager parentFractionManager, Fraction fraction) {
        this.mParentFractionManager = parentFractionManager;
        this.mFractionContainer = new FractionContainer() {
            @Override
            public View findViewById(int viewId) {
                return fraction.getView().findViewById(viewId);
            }

            @Override
            public Context getContext() {
                return fraction.getContext();
            }
        };
    }

    private interface FractionContainer {
        View findViewById(int viewId);

        Context getContext();
    }

    /**
     * 开启事务
     */
    public FractionTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    /**
     * 获取父FractionManager
     */
    public FractionManager getParentFractionManager() {
        return mParentFractionManager;
    }

    //-------------------------- 公开操作API --------------------------

    public void attachFraction(Fraction fraction) {
        if (fraction.getView() != null) {
            if (fractionIsAdd(fraction)) {
                return;
            }
        }
        fraction.performCreateView(LayoutInflater.from(getContext()), null);
        if (fraction.getView() == null) {
            throw new IllegalStateException("Fraction.onCreateView()的返回值不能为空");
        }
        fraction.setFractionManager(this);
        fraction.onCreate();
        fraction.onViewCreated(fraction.getView());
        mFractions.add(fraction);
    }

    public void addFraction(Fraction fraction) {
        ViewGroup containerView = findViewById(fraction.mContainerId);
        if (containerView == null) {
            throw new NullPointerException("容器Id不能为空");
        }
        if (fraction.getView() != null) {
            throw new RuntimeException("Fraction 视图已被创建");
        }
        //通知依附
        fraction.onAttach(getContext());
        //创建View
        View rootView = fraction.performCreateView(LayoutInflater.from(getContext()), containerView);
        containerView.addView(rootView);
        fraction.isAdd = true;
        fraction.setFractionManager(this);
        //通知创建
        fraction.onCreate();
        fraction.onViewCreated(fraction.getView());
        //切换可见性
        fraction.visibleChange(true);
        mFractions.add(fraction);
    }

    public void showFraction(Fraction fraction) {
        if (fraction.getView() != null) {
            fraction.getView().setVisibility(View.VISIBLE);
            fraction.isVisible = true;
            fraction.visibleChange(true);
        }
    }

    public void hideFraction(Fraction fraction) {
        if (fraction.getView() != null) {
            fraction.getView().setVisibility(View.INVISIBLE);
            fraction.isVisible = false;
            fraction.visibleChange(false);
        }
    }

    public void replaceFraction(Fraction fraction) {
        //先查找Fraction是否存在，存在则先移除
        Fraction currentFraction = findFractionById(fraction.mContainerId);
        if (currentFraction != null) {
            if (currentFraction == fraction) {
                return;
            }
            removeFraction(fraction);
        }
        addFraction(fraction);
    }

    public void removeFraction(Fraction fraction) {
        //遍历查找目标Fraction
        Fraction target = null;
        for (Fraction fa : mFractions) {
            if (fa == fraction) {
                target = fa;
                break;
            }
        }
        if (target == null) {
            return;
        }
        //移除处理
        target.onDestroy();
        ViewGroup container = findViewById(target.mContainerId);
        if (container != null) {
            container.removeView(target.getView());
        }
        target.isVisible = false;
        target.visibleChange(false);
        mFractions.remove(target);
    }

    /**
     * 判断Fraction是否添加了
     */
    boolean fractionIsAdd(Fraction fraction) {
        for (Fraction fa : mFractions) {
            if (fa == fraction) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据Id查找Fraction
     */
    public Fraction findFractionById(int id) {
        for (int i = mFractions.size() - 1; i > -1; i--) {
            if (mFractions.get(i).mContainerId == id) {
                return mFractions.get(i);
            }
        }
        return null;
    }

    /**
     * 根据Tag查找Fraction
     */
    public Fraction findFractionByTag(String tag) {
        for (int i = mFractions.size() - 1; i > -1; i--) {
            if (TextUtils.equals(mFractions.get(i).mTag, tag)) {
                return mFractions.get(i);
            }
        }
        return null;
    }

    public boolean findFraction(Fraction fraction) {
        for (int i = mFractions.size() - 1; i > -1; i--) {
            if (fraction == mFractions.get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找放置Fraction的View容器
     *
     * @param viewId View容器Id
     */
    private ViewGroup findViewById(int viewId) {
        return (ViewGroup) mFractionContainer.findViewById(viewId);
    }

    public FractionLifecycleDispatcher getFractionLifecycleDispatcher() {
        return mFractionLifecycleDispatcher;
    }

    public Context getContext() {
        return mFractionContainer.getContext();
    }
}