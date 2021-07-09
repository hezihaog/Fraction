package com.zh.android.fraction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 类似Fragment的ViewController
 */
public abstract class Fraction {
    /**
     * 唯一Tag
     */
    String mTag;
    /**
     * 存放Fraction中view的容器Id
     */
    int mContainerId;
    /**
     * 是否已经添加了
     */
    boolean isAdd = false;
    /**
     * 是否可见
     */
    boolean isVisible = false;
    /**
     * 管理器
     */
    private FractionManager mFractionManager;
    /**
     * 子Fraction管理器
     */
    private FractionManager mChildFractionManager;
    /**
     * 根View
     */
    private View mRoot;

    protected View performCreateView(LayoutInflater inflater, ViewGroup container) {
        mRoot = onCreateView(inflater, container);
        return mRoot;
    }

    /**
     * 创建根View并返回
     */
    protected abstract View onCreateView(LayoutInflater inflater, ViewGroup container);

    /**
     * View创建完毕
     */
    public void onViewCreated(View view) {
    }

    void performFractionCreate() {
        onCreate();
    }

    void performFractionStart() {
        onStart();
    }

    public void performFractionResume() {
        onResume();
    }

    public void performFractionPause() {
        onPause();
    }

    void performFractionStop() {
        onStop();
    }

    void performFractionDestroy() {
        onDestroy();
    }

    public void onAttach(Context context) {
    }

    public void onCreate() {
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }

    /**
     * 设置FractionManager
     */
    void setFractionManager(FractionManager fractionManager) {
        this.mFractionManager = fractionManager;
        this.mChildFractionManager = new FractionManager(fractionManager, this);
    }

    public FractionManager getFractionManager() {
        return mFractionManager;
    }

    public FractionManager getChildFractionManager() {
        return mChildFractionManager;
    }

    /**
     * 设置是否可见
     *
     * @param visible 是否可见
     */
    void visibleChange(boolean visible) {
        if (visible) {
            onShow();
        } else {
            onHide();
        }
    }

    /**
     * 可见时回调
     */
    protected void onShow() {
    }

    /**
     * 不可见时回调
     */
    protected void onHide() {
    }

    /**
     * 判断是否可见
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * 获取Fraction中的根View
     */
    public View getView() {
        return mRoot;
    }

    public Context getContext() {
        return mRoot.getContext();
    }
}