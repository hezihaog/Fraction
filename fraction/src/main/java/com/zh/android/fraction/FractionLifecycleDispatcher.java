package com.zh.android.fraction;

/**
 * 生命周期事件分发器
 */
public class FractionLifecycleDispatcher {
    private final FractionManager mFractionManager;

    public FractionLifecycleDispatcher(FractionManager fractionManager) {
        this.mFractionManager = fractionManager;
    }

    /**
     * 分发onCreate()事件
     */
    void dispatchOnCreate() {
        synchronized (this) {
            for (Fraction fraction : mFractionManager.mFractions) {
                fraction.performFractionCreate();
            }
        }
    }

    /**
     * 分发onStart()事件
     */
    void dispatchOnStart() {
        synchronized (this) {
            for (Fraction fraction : mFractionManager.mFractions) {
                fraction.performFractionStart();
            }
        }
    }

    /**
     * 分发onResume()事件
     */
    void dispatchOnResume() {
        synchronized (this) {
            for (Fraction fraction : mFractionManager.mFractions) {
                fraction.performFractionResume();
            }
        }
    }

    /**
     * 分发onPause()事件
     */
    void dispatchOnPause() {
        synchronized (this) {
            for (Fraction fraction : mFractionManager.mFractions) {
                fraction.performFractionPause();
            }
        }
    }

    /**
     * 分发onStop()事件
     */
    void dispatchOnStop() {
        synchronized (this) {
            for (Fraction fraction : mFractionManager.mFractions) {
                fraction.performFractionStop();
            }
        }
    }

    /**
     * 分发onDestroy()事件
     */
    void dispatchOnDestroy() {
        synchronized (this) {
            for (Fraction fraction : mFractionManager.mFractions) {
                fraction.performFractionDestroy();
            }
        }
    }
}