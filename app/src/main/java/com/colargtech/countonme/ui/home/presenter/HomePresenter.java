package com.colargtech.countonme.ui.home.presenter;

import com.colargtech.countonme.commons.rx.MvpRxBasePresenter;
import com.colargtech.countonme.database.manager.CountOnMeDBManager;
import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.model.Group;
import com.colargtech.countonme.ui.home.view.HomeView;
import com.colargtech.countonme.ui.model.ActionUI;
import com.colargtech.countonme.ui.model.GroupUI;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

/**
 * @author juancho.
 */
@Singleton
public class HomePresenter extends MvpRxBasePresenter<HomeView> {

    private final CountOnMeDBManager countOnMeDBManager;
    private final Subject<Group, Group> groupCreateSubject;
    //TODO This should not be here, it should be on the presenter of the action activity but i put it here as an example
    private final Subject<Action, Action> actionUpdateSubject;


    @Inject
    public HomePresenter(CountOnMeDBManager countOnMeDBManager, @Named("GroupCreate") Subject<Group, Group> groupCreateSubject,
                         @Named("ActionUpdate") Subject<Action, Action> actionUpdateSubject) {
        this.countOnMeDBManager = countOnMeDBManager;
        this.groupCreateSubject = groupCreateSubject;
        this.actionUpdateSubject = actionUpdateSubject;
    }

    @Override
    public void onCreate(HomeView view) {
        super.onCreate(view);
        addSubscription(subscribe(countOnMeDBManager.getAllGroups()));
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO fix this because we are receiving again while resubscribe
        addSubscription(subscribe(groupCreateSubject));
        addSubscription(subscribeUpdate(actionUpdateSubject));
    }

    public void createGroup(String name) {
        countOnMeDBManager.createGroup(name);
    }

    private Subscription subscribe(Observable<Group> observable) {
        return observable.map(
                new Func1<Group, GroupUI>() {
                    @Override
                    public GroupUI call(Group group) {
                        return new GroupUI(group.id, group.name);
                    }
                })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GroupUI>() {
                    @Override
                    public void call(GroupUI groupUI) {
                        if (isViewAttached()) {
                            getView().addGroup(groupUI);
                        }
                    }
                });
    }


    private Subscription subscribeUpdate(Observable<Action> actionSub) {
        return actionSub.map(
                new Func1<Action, ActionUI>() {
                    @Override
                    public ActionUI call(Action action) {
                        ActionUI.Builder builder = new ActionUI.Builder(action.name, action.period, action.incrementBy);
                        return builder.build();
                    }
                })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ActionUI>() {
                    @Override
                    public void call(ActionUI actionUI) {
                        if (isViewAttached()) {
                        }
                    }
                });
    }
}
