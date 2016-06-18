package com.colargtech.countonme.ui.home.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
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
import com.squareup.sqlbrite.BriteDatabase;

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
    private GroupsAdapter adapter;
    private HomeNavigation homeNavigation;
    private AlertDialog alertCreateGroup;

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

        RecyclerView homeList = (RecyclerView) getView().findViewById(R.id.home_list);
        homeList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new GroupsAdapter(this);
        homeList.setAdapter(adapter);

        FloatingActionButton addGroupButton = (FloatingActionButton) getView().findViewById(R.id.home_fab_add_group);
        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddGroupDialog();
            }
        });

        BriteDatabase db = ((CountOnMeApp) getActivity().getApplication()).getDb();
        homePresenter = new HomePresenter(db);
        homePresenter.attachView(this);
        homePresenter.init();
    }

    private void showAddGroupDialog() {
        // Snackbar.make(v, "Add Group!", Snackbar.LENGTH_SHORT).show();
        // homeNavigation.createGroup();
        final EditText etGroupName = new EditText(getContext());
        etGroupName.setImeActionLabel("Go", KeyEvent.KEYCODE_ENTER);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle("Group Name:");
        dialogBuilder.setView(etGroupName);
        dialogBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // This intentionally empty to properly manage the positive button.
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertCreateGroup = dialogBuilder.show();
        alertCreateGroup.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Check empty
                if (TextUtils.isEmpty(etGroupName.getText())) {
                    etGroupName.setError("Invalid value.");
                } else {
                    homePresenter.createGroup(etGroupName.getText().toString());
                    alertCreateGroup.dismiss();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homePresenter.detachView(getRetainInstance());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (alertCreateGroup != null) {
            alertCreateGroup.dismiss();
        }
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
