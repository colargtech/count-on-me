package com.colargtech.countonme.ui.home.view;

import android.os.Bundle;

import com.colargtech.countonme.commons.ui.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            changeFragment(HomeFragment.newInstance());
        }
    }
}
