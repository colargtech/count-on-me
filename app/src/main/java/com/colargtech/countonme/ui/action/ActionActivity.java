package com.colargtech.countonme.ui.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.colargtech.countonme.commons.ui.BaseActivity;
import com.colargtech.countonme.model.Group;
import com.colargtech.countonme.ui.count.CountFragment;
import com.colargtech.countonme.ui.model.ActionUI;

/**
 * @author gdfesta
 */

public class ActionActivity extends BaseActivity implements ActionsFragment.ActionsNavigation {

    public static Intent newIntent(Context context, String groupID) {
        Intent intent = new Intent(context, ActionActivity.class);
        intent.putExtra(ActionsFragment.GROUP_KEY, groupID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            changeFragment(ActionsFragment.newInstance(getIntent().getStringExtra(ActionsFragment.GROUP_KEY)));
        }
    }

    @Override
    public void showActionDetail(ActionUI actionUI) {
        changeFragment(CountFragment.newInstance(actionUI));
    }
}
