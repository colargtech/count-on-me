package com.colargtech.countonme.ui.count;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colargtech.countonme.CountOnMeApp;
import com.colargtech.countonme.R;
import com.colargtech.countonme.commons.ui.BaseFragment;
import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.ui.count.presenter.CountPresenter;
import com.colargtech.countonme.ui.count.presenter.CountView;
import com.colargtech.countonme.ui.model.ActionUI;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import rx.subjects.Subject;

/**
 * @author gdfesta
 */

public class CountFragment extends BaseFragment implements CountView {
    private static final String ACTION_ID_KEY = "ACTION_ID_KEY";

    private String actionId;

    @Inject
    CountPresenter presenter;

    public static CountFragment newInstance(String actionId) {
        Bundle args = new Bundle();
        CountFragment fragment = new CountFragment();
        args.putString(ACTION_ID_KEY, actionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionId = getArguments().getString(ACTION_ID_KEY);
        presenter.onCreate(this);
        attachCallbacks(presenter);
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();
        CountOnMeApp.get(getContext()).getHomeComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.count_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_increase_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.increaseOne();
            }
        });

        view.findViewById(R.id.btn_increase_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.increaseCustom();
            }
        });

        view.findViewById(R.id.btn_decrease_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.decreaseOne();
            }
        });

        view.findViewById(R.id.btn_decrease_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.decreaseCustom();
            }
        });
    }

    @Override
    public void updateView(ActionUI actionUI) {
        ((TextView) getView().findViewById(R.id.action_title)).setText(actionUI.getName());
        ((TextView) getView().findViewById(R.id.count_for_date)).setText(Integer.toString(actionUI.getCountForDate(new Date())));
    }

    @Override
    public String getActionId() {
        return actionId;
    }
}
