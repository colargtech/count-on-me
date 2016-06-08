package com.colargtech.countonme.ui.home.view;

import android.os.Bundle;
import android.widget.Toast;

import com.colargtech.countonme.commons.ui.BaseActivity;
import com.colargtech.countonme.ui.model.Group;

public class HomeActivity extends BaseActivity implements HomeFragment.HomeNavigation {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            changeFragment(HomeFragment.newInstance());
        }
    }


    @Override
    public void createGroup() {
        // TODO
    }

    @Override
    public void showDetailGroup(Group group) {
        // TODO
        Toast.makeText(this, "Show Group with name: " + group.getName(), Toast.LENGTH_SHORT).show();
    }
}
