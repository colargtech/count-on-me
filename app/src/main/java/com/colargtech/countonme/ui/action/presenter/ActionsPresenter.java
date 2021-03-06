package com.colargtech.countonme.ui.action.presenter;

import com.colargtech.countonme.commons.rx.MvpRxBasePresenter;
import com.colargtech.countonme.database.manager.CountOnMeDBManager;
import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.model.Group;
import com.colargtech.countonme.ui.action.ActionsRxMapper;
import com.colargtech.countonme.ui.model.ActionUI;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

/**
 * @author gdfesta.
 */
public class ActionsPresenter extends MvpRxBasePresenter<ActionsView> {

    private final CountOnMeDBManager countOnMeDBManager;
    private final Subject<Action, Action> actionCreateSubject;

    @Inject
    public ActionsPresenter(CountOnMeDBManager countOnMeDBManager, @Named("ActionCreate") Subject<Action, Action> actionCreateSubject) {
        this.countOnMeDBManager = countOnMeDBManager;
        this.actionCreateSubject = actionCreateSubject;
    }

    @Override
    public void onCreate(ActionsView view) {
        super.onCreate(view);
        addSubscription(subscribe(countOnMeDBManager.getAllActions(view.getGroupID())));
        countOnMeDBManager
                .getGroup(view.getGroupID())
                .map(new Func1<Group, String>() {
                    @Override
                    public String call(Group group) {
                        return group.name;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String groupName) {
                        if (isViewAttached()) {
                            getView().setTitle(groupName);
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        addSubscription(subscribe(actionCreateSubject));
    }

    public void onActionCreated(String actionId) {
        addSubscription(subscribe(countOnMeDBManager.getActionById(actionId)));
    }

    private Subscription subscribe(Observable<Action> observable) {
        return ActionsRxMapper.fromActionToActionUI(observable)
                .subscribe(new Action1<ActionUI>() {
                    @Override
                    public void call(ActionUI actionUI) {
                        if (isViewAttached()) {
                            getView().addActionUI(actionUI);
                        }
                    }
                });
    }
}
