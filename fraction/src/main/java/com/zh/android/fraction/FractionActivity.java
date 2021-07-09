package com.zh.android.fraction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 要使用Fraction，需要使用的Activity
 */
public class FractionActivity extends AppCompatActivity {
    private final FractionManager mFractionManager = new FractionManager(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFractionManager.getFractionLifecycleDispatcher().dispatchOnCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFractionManager.getFractionLifecycleDispatcher().dispatchOnStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFractionManager.getFractionLifecycleDispatcher().dispatchOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFractionManager.getFractionLifecycleDispatcher().dispatchOnPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFractionManager.getFractionLifecycleDispatcher().dispatchOnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFractionManager.getFractionLifecycleDispatcher().dispatchOnDestroy();
    }

    public FractionManager getFractionManager() {
        return mFractionManager;
    }
}