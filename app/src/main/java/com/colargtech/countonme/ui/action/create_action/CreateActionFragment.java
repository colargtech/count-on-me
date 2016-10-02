package com.colargtech.countonme.ui.action.create_action;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.colargtech.countonme.CountOnMeApp;
import com.colargtech.countonme.R;
import com.colargtech.countonme.commons.ui.BaseFragment;
import com.colargtech.countonme.ui.action.create_action.presenter.CreateActionPresenter;
import com.colargtech.countonme.ui.model.PeriodUI;

import java.util.List;

import javax.inject.Inject;

/**
 * @author juancho.
 */
public class CreateActionFragment extends BaseFragment implements CreateActionView {

    /**
     * This interface should be implemented by any activity that open this fragment.
     * If it's in landscape by ActionActivity, this should manage this actions.
     */
    interface CreateActionNavigation {
        void onActionCreated(String actionId);

        void onCancel();
    }

    @Inject
    CreateActionPresenter createActionPresenter;

    final static String GROUP_KEY = "GROUP_KEY";
    private String groupID;
    private CreateActionNavigation navigation;

    private EditText actionName;
    private Spinner periodSpinner;
    private Spinner incrementBySpinner;
    private Spinner maxPerDaySpinner;


    public static CreateActionFragment newInstance(String groupID) {
        CreateActionFragment fragment = new CreateActionFragment();
        Bundle arguments = new Bundle();
        arguments.putString(GROUP_KEY, groupID);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        CountOnMeApp.get(getContext()).getHomeComponent().inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            navigation = (CreateActionNavigation) context;
        } catch (ClassCastException e) {
            Log.d(this.getClass().getSimpleName(), "Unable to cast Activity to CreateActionNavigation.");
            throw e;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupID = getArguments().getString(GROUP_KEY);
        createActionPresenter.onCreate(this);
        attachCallbacks(createActionPresenter);
        baseActions.setScreenTitle(R.string.action_create_new);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.action_new, container, false);

        actionName = (EditText) view.findViewById(R.id.etActionName);
        periodSpinner = (Spinner) view.findViewById(R.id.spPeriod);
        incrementBySpinner = (Spinner) view.findViewById(R.id.spIncrementBy);
        maxPerDaySpinner = (Spinner) view.findViewById(R.id.spMaxPerDay);
        Button create = (Button) view.findViewById(R.id.btCreate);
        Button cancel = (Button) view.findViewById(R.id.btCancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigation.onCancel();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateParams();
                createActionPresenter.createAction(groupID,
                        actionName.getText().toString(),
                        (PeriodUI) periodSpinner.getSelectedItem(),
                        (Integer) incrementBySpinner.getSelectedItem(),
                        (Integer) maxPerDaySpinner.getSelectedItem());
            }
        });

        return view;
    }

    private void validateParams() {

    }

    public void setIncrementByValues(List<Integer> values) {
        incrementBySpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, values));
    }

    @Override
    public void setPeriodValues(List<PeriodUI> values) {
        periodSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, values));
    }

    @Override
    public void setMaxPerDayValues(List<Integer> values) {
        maxPerDaySpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, values));
    }

    @Override
    public void onActionCreated(String actionId) {
        navigation.onActionCreated(actionId);
    }
}
