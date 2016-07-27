package com.colargtech.countonme.ui.action;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colargtech.countonme.CountOnMeApp;
import com.colargtech.countonme.R;
import com.colargtech.countonme.commons.ui.BaseFragment;
import com.colargtech.countonme.ui.action.adapter.ActionDelegateAdapter;
import com.colargtech.countonme.ui.action.adapter.ActionsAdapter;
import com.colargtech.countonme.ui.action.presenter.ActionPresenter;
import com.colargtech.countonme.ui.action.presenter.ActionView;
import com.colargtech.countonme.ui.action.presenter.ActionsPresenter;
import com.colargtech.countonme.ui.action.presenter.ActionsView;
import com.colargtech.countonme.ui.model.ActionUI;

import java.util.UUID;

import javax.inject.Inject;

/**
 * @author gdfesta
 */

public class ActionsFragment extends BaseFragment implements ActionDelegateAdapter.ActionAdapterActions, ActionView, ActionsView {

    interface ActionsNavigation {
        void showActionDetail(ActionUI actionUI);
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
                        actionsPresenter.createAction(groupID, UUID.randomUUID().toString());
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
}
