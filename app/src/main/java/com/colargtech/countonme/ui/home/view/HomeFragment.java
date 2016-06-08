package com.colargtech.countonme.ui.home.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colargtech.countonme.R;
import com.colargtech.countonme.commons.ui.BaseFragment;
import com.colargtech.countonme.ui.home.presenter.HomePresenter;
import com.colargtech.countonme.ui.home.view.adapter.GroupDelegateAdapter;
import com.colargtech.countonme.ui.home.view.adapter.GroupsAdapter;
import com.colargtech.countonme.ui.model.Group;

import java.util.List;

/**
 * @author juancho.
 */
public class HomeFragment extends BaseFragment implements HomeView, GroupDelegateAdapter.GroupAdapterActions {

    interface HomeNavigation {
        void createGroup();

        void showDetailGroup(Group group);
    }

    private HomePresenter homePresenter;
    private RecyclerView homeList;
    private GroupsAdapter adapter;
    private FloatingActionButton addGroupButton;
    private HomeNavigation homeNavigation;

    static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            homeNavigation = (HomeNavigation) getActivity();
        } catch (ClassCastException e) {
            Log.d(this.getClass().getSimpleName(), "Unable to cast Activity to HomeNavigation.");
            throw e;
        }

        homeList = (RecyclerView) getView().findViewById(R.id.home_list);
        homeList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new GroupsAdapter(this);
        homeList.setAdapter(adapter);

        addGroupButton = (FloatingActionButton) getView().findViewById(R.id.home_fab_add_group);
        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Add Group!", Snackbar.LENGTH_SHORT).show();
                homeNavigation.createGroup();

            }
        });

        homePresenter = new HomePresenter();
        homePresenter.attachView(this);
        homePresenter.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homePresenter.detachView(getRetainInstance());
    }

    /**
     * View Implementations
     */

    @Override
    public void showGroups(List<Group> groups) {
        adapter.setGroups(groups);
    }

    /**
     * Delegates Actions
     */

    @Override
    public void showGroupDetail(Group group) {
        homeNavigation.showDetailGroup(group);
    }
}
