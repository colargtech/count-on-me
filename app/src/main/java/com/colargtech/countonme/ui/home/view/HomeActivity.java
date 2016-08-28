package com.colargtech.countonme.ui.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

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
        // if we are on portrait:
        startActivityForResult(ActionActivity.newIntent(this, groupUI.getId()), ActionActivity.GROUP_REMOVED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment homeFrag = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
        if (homeFrag != null) {
            homeFrag.onActivityResult(requestCode, resultCode, data);
        }
    }
}
