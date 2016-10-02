package com.colargtech.countonme.ui.action.presenter;

import com.colargtech.countonme.commons.rx.MvpRxBasePresenter;
import com.colargtech.countonme.database.manager.CountOnMeDBManager;
import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.ui.action.ActionsRxMapper;
import com.colargtech.countonme.ui.model.ActionUI;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.Subject;

/**
 * @author gdfesta.
 */
public class ActionPresenter extends MvpRxBasePresenter<ActionView> {

    private final CountOnMeDBManager countOnMeDBManager;
    private final Subject<Action, Action> actionUpdateSubject;

    @Inject
    public ActionPresenter(CountOnMeDBManager countOnMeDBManager,
                           @Named("ActionUpdate") Subject<Action, Action> actionUpdateSubject) {
        this.countOnMeDBManager = countOnMeDBManager;
        this.actionUpdateSubject = actionUpdateSubject;
    }

    @Override
    public void onResume() {
        super.onResume();
        addSubscription(subscribeUpdate(actionUpdateSubject));
    }

    public Observable<Void> deleteGroup(String groupID) {
        return this.countOnMeDBManager.deleteGroup(groupID);
    }

    private Subscription subscribeUpdate(Observable<Action> actionSub) {
        return ActionsRxMapper.fromActionToActionUI(actionSub)
                .subscribe(new Action1<ActionUI>() {
                    @Override
                    public void call(ActionUI actionUI) {
                        if (isViewAttached()) {
                            getView().updateActionUI(actionUI);
                        }
                    }
                });
    }
}
