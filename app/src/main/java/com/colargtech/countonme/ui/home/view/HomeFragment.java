package com.colargtech.countonme.ui.home.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.colargtech.countonme.CountOnMeApp;
import com.colargtech.countonme.R;
import com.colargtech.countonme.commons.ui.BaseFragment;
import com.colargtech.countonme.ui.home.presenter.HomePresenter;
import com.colargtech.countonme.ui.home.view.adapter.GroupDelegateAdapter;
import com.colargtech.countonme.ui.home.view.adapter.GroupsAdapter;
import com.colargtech.countonme.ui.model.Group;

import javax.inject.Inject;

/**
 * Show Groups.
 *
 * @author juancho.
 */
public class HomeFragment extends BaseFragment implements HomeView, GroupDelegateAdapter.GroupAdapterActions {

    interface HomeNavigation {
        void showDetailGroup(Group group);
    }

    @Inject
    HomePresenter homePresenter;

    private RecyclerView homeList;
    private GroupsAdapter adapter;
    private HomeNavigation homeNavigation;
    private AlertDialog addGroupDialog;

    static HomeFragment newInstance() {
        return new HomeFragment();
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

        getView().findViewById(R.id.home_fab_add_group)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogAddGroup();
                    }
                });

        homePresenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        homePresenter.detachView(getRetainInstance());
        if (addGroupDialog != null && addGroupDialog.isShowing()) {
            addGroupDialog.dismiss();
        }
    }

    /**
     * View Implementations
     */

    @Override
    public void addGroup(Group group) {
        adapter.addGroup(group);
    }

    /**
     * Delegates Actions
     */

    @Override
    public void showGroupDetail(Group group) {
        homeNavigation.showDetailGroup(group);
    }


    /**
     * Private methods
     */

    private void showDialogAddGroup() {
        final EditText etGroupName = new EditText(getContext());
        etGroupName.setHint(R.string.add_group_hint);

        addGroupDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.add_group_title)
                .setView(etGroupName)
                .setPositiveButton(R.string.add_group_accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // intentionally empty.
                    }
                })
                .setNegativeButton(R.string.add_group_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        addGroupDialog.show();
        addGroupDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etGroupName.getText())) {
                    etGroupName.setError(getResources().getString(R.string.add_group_invalid_value));
                } else {
                    homePresenter.createGroup(new Group(etGroupName.getText().toString()));
                    addGroupDialog.dismiss();
                }
            }
        });
    }
}
