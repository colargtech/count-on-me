package com.colargtech.countonme.ui.action;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.colargtech.countonme.R;
import com.colargtech.countonme.commons.ui.BaseActivity;
import com.colargtech.countonme.ui.action.create_action.CreateActionActivity;
import com.colargtech.countonme.ui.count.CountFragment;
import com.colargtech.countonme.ui.model.ActionUI;

import static com.colargtech.countonme.ui.action.create_action.CreateActionActivity.ACTION_CREATED;

/**
 * @author gdfesta
 */
public class ActionActivity extends BaseActivity implements ActionsFragment.ActionsNavigation {

    public static final int GROUP_REMOVED = 100;
    public static final String GROUP_REMOVED_ID = "GROUP_REMOVED_ID";

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment homeFrag = getSupportFragmentManager().findFragmentByTag(ActionsFragment.class.getSimpleName());
        if (homeFrag != null) {
            homeFrag.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void createNewAction(String groupID) {
        startActivityForResult(CreateActionActivity.newIntent(this, groupID), ACTION_CREATED);
    }

    @Override
    public void showActionDetail(ActionUI actionUI) {
        changeFragment(CountFragment.newInstance(actionUI));
    }

    @Override
    public void onDeleteGroup(String groupId) {
        Toast.makeText(this, R.string.group_deleted_msg, Toast.LENGTH_SHORT).show();
        Intent returnIntent = new Intent();
        returnIntent.putExtra(GROUP_REMOVED_ID, groupId);
        setResult(GROUP_REMOVED, returnIntent);
        finish();
    }
}
