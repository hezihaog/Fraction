package com.zh.android.fraction.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zh.android.fraction.Fraction;

/**
 * 首页
 */
public class HomeFraction extends Fraction {
    public static HomeFraction newInstance() {
        return new HomeFraction();
    }

    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fraction_home, container, false);
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        Button homeBtn = view.findViewById(R.id.home_btn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFractionManager().beginTransaction()
                        .replace(R.id.live_container, LiveFraction.newInstance())
                        .commit();
            }
        });
    }
}