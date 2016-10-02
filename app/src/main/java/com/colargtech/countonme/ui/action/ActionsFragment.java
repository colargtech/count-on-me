package com.colargtech.countonme.ui.action;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.colargtech.countonme.CountOnMeApp;
import com.colargtech.countonme.R;
import com.colargtech.countonme.commons.ui.BaseFragment;
import com.colargtech.countonme.ui.action.adapter.ActionDelegateAdapter;
import com.colargtech.countonme.ui.action.adapter.ActionsAdapter;
import com.colargtech.countonme.ui.action.create_action.CreateActionActivity;
import com.colargtech.countonme.ui.action.presenter.ActionPresenter;
import com.colargtech.countonme.ui.action.presenter.ActionView;
import com.colargtech.countonme.ui.action.presenter.ActionsPresenter;
import com.colargtech.countonme.ui.action.presenter.ActionsView;
import com.colargtech.countonme.ui.model.ActionUI;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * @author gdfesta
 */

public class ActionsFragment extends BaseFragment implements ActionDelegateAdapter.ActionAdapterActions, ActionView, ActionsView {

    /**
     * This interface should be implemented by any activity that open this fragment.
     * If it's in landscape by HomeActivity, this should manage this actions.
     */
    interface ActionsNavigation {
        void createNewAction(String groupID);

        void showActionDetail(ActionUI actionUI);

        void onDeleteGroup(String groupId);
    }

    final static String GROUP_KEY = "GROUP_KEY";

    static ActionsFragment newInstance(String groupID) {
        ActionsFragment actionsFragment = new ActionsFragment();
        Bundle arguments = new Bundle();
        arguments.putString(GROUP_KEY, groupID);
        actionsFragment.setArguments(arguments);
        return actionsFragment;
    }

    private String groupID;
    private ActionsAdapter adapter;
    private ActionsNavigation navigation;

    @Inject
    ActionPresenter actionPresenter;

    @Inject
    ActionsPresenter actionsPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        groupID = getArguments().getString(GROUP_KEY);
        adapter = new ActionsAdapter(this);
        actionPresenter.onCreate(this);
        actionsPresenter.onCreate(this);
        attachCallbacks(actionPresenter);
        attachCallbacks(actionsPresenter);
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        CountOnMeApp.get(getContext()).getHomeComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView homeList = (RecyclerView) view.findViewById(R.id.home_list);
        homeList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        homeList.setAdapter(adapter);
        view.findViewById(R.id.home_fab_add_group)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigation.createNewAction(groupID);
                    }
                });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            navigation = (ActionsNavigation) getActivity();
        } catch (ClassCastException e) {
            Log.d(this.getClass().getSimpleName(), "Unable to cast Activity to ActionsNavigation.");
            throw e;
        }

        baseActions.registerFragmentActivityForResult(this);
    }

    /**
     * Only called when it a new activity was created in order to create a new action.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CreateActionActivity.ACTION_CREATED) {
            String actionId = data.getStringExtra(CreateActionActivity.ACTION_CREATED_ID);
            actionsPresenter.onActionCreated(actionId);
        }
    }

    @Override
    public void showActionDetail(ActionUI actionUI) {
        navigation.showActionDetail(actionUI);
    }

    @Override
    public void addActionUI(ActionUI actionUI) {
        adapter.addAction(actionUI);
    }

    @Override
    public String getGroupID() {
        return groupID;
    }

    @Override
    public void updateActionUI(ActionUI actionUI) {
        adapter.updateActionUI(actionUI);
    }

    @Override
    public void setTitle(String title) {
        baseActions.setScreenTitle(title);
    }

    // region *************** Menu Options ***************
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.group_action_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.group_menu_delete:
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.group_delete_title)
                        .setMessage(R.string.group_delete_message)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                actionPresenter.deleteGroup(groupID)
                                        .subscribe(new Action1<Void>() {
                                            @Override
                                            public void call(Void aVoid) {
                                                navigation.onDeleteGroup(groupID);
                                            }
                                        }, new Action1<Throwable>() {
                                            @Override
                                            public void call(Throwable throwable) {
                                                Toast.makeText(getContext(), R.string.group_delete_fails, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                        })
                        .setNegativeButton(R.string.no, null)
                        .show();
                break;
        }
        return true;
    }

    // endregion
}
