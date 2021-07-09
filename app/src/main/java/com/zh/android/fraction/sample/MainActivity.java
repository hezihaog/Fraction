package com.zh.android.fraction.sample;

import android.os.Bundle;

import com.zh.android.fraction.FractionActivity;

public class MainActivity extends FractionActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFractionManager().beginTransaction()
                .add(R.id.fraction_container, HomeFraction.newInstance())
                .commit();
    }
}