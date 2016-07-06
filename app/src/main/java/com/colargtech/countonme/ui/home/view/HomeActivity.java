package com.colargtech.countonme.ui.home.view;

import android.os.Bundle;
import android.widget.Toast;

import com.colargtech.countonme.R;
import com.colargtech.countonme.commons.ui.BaseActivity;
import com.colargtech.countonme.ui.count.CountActivity;
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
        Toast.makeText(this, "Show Group with name: " + groupUI.getName(), Toast.LENGTH_SHORT).show();
    }
}
