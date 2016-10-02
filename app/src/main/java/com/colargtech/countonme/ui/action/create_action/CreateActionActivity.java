package com.colargtech.countonme.ui.action.create_action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.colargtech.countonme.R;
import com.colargtech.countonme.commons.ui.BaseActivity;

/**
 * @author juancho.
 */
public class CreateActionActivity extends BaseActivity implements CreateActionFragment.CreateActionNavigation {

    public static final int ACTION_CREATED = 200;
    public static final String ACTION_CREATED_ID = "ACTION_CREATED_ID";

    public static Intent newIntent(Context context, String groupID) {
        Intent intent = new Intent(context, CreateActionActivity.class);
        intent.putExtra(CreateActionFragment.GROUP_KEY, groupID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            changeFragment(CreateActionFragment.newInstance(getIntent().getStringExtra(CreateActionFragment.GROUP_KEY)));
        }
    }

    @Override
    public void onActionCreated(String actionId) {
        Toast.makeText(this, R.string.action_created_msg, Toast.LENGTH_SHORT).show();
        Intent returnIntent = new Intent();
        returnIntent.putExtra(ACTION_CREATED_ID, actionId);
        setResult(ACTION_CREATED, returnIntent);
        finish();
    }

    @Override
    public void onCancel() {
        finish();
    }
}
