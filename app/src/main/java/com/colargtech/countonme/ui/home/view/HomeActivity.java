package com.colargtech.countonme.ui.home.view;

import android.os.Bundle;

import com.colargtech.countonme.R;
import com.colargtech.countonme.commons.ui.BaseActivity;
import com.colargtech.countonme.ui.action.ActionActivity;
import com.colargtech.countonme.ui.model.GroupUI;

public class HomeActivity extends BaseActivity implements HomeFragment.HomeNavigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            changeFragment(HomeFragment.newInstance());
        }
        setTitle(R.string.app_name);
    }

    @Override
    public void showDetailGroup(GroupUI groupUI) {
        startActivity(ActionActivity.newIntent(this, groupUI.getId()));
    }
}
