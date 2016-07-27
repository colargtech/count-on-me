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
import com.colargtech.countonme.model.ActionCount;
import com.colargtech.countonme.model.Range;
import com.colargtech.countonme.ui.action.presenter.ActionPresenter;
import com.colargtech.countonme.ui.action.presenter.ActionView;
import com.colargtech.countonme.ui.count.presenter.CountPresenter;
import com.colargtech.countonme.ui.count.presenter.CountView;
import com.colargtech.countonme.ui.model.ActionUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * @author gdfesta
 */

public class CountFragment extends BaseFragment implements CountView, ActionView {
    private static final String ACTION_ID_KEY = "ACTION_ID_KEY";

    private ActionUI actionUI;

    @Inject
    CountPresenter countPresenter;

    @Inject
    ActionPresenter actionPresenter;

    public static CountFragment newInstance(ActionUI actionUI) {
        Bundle args = new Bundle();
        CountFragment fragment = new CountFragment();
        args.putParcelable(ACTION_ID_KEY, actionUI);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionUI = getArguments().getParcelable(ACTION_ID_KEY);
        countPresenter.onCreate(this);
        actionPresenter.onCreate(this);
        attachCallbacks(actionPresenter);
        attachCallbacks(countPresenter);
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
        updateActionUI(actionUI);
        view.findViewById(R.id.btn_increase_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countPresenter.increaseOne();
            }
        });

        view.findViewById(R.id.btn_increase_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countPresenter.increaseCustom();
            }
        });

        view.findViewById(R.id.btn_decrease_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countPresenter.decreaseOne();
            }
        });

        view.findViewById(R.id.btn_decrease_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countPresenter.decreaseCustom();
            }
        });
    }

    @Override
    public void updateActionCount(ActionCount actionCount) {
        ((TextView) getView().findViewById(R.id.count_for_date)).setText(Long.toString(actionCount.count));
    }

    public String getActionUI() {
        return actionUI.getId();
    }

    @Override
    public List<Range> getRanges() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        ArrayList<Range> ranges = new ArrayList<>();
        try {
            ranges.add(new Range(format.parse("2015/01/01"), format.parse("2017/01/01")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ranges;
    }

    @Override
    public void updateActionUI(ActionUI actionUI) {
        ((TextView) getView().findViewById(R.id.action_title)).setText(actionUI.getName());
    }
}
