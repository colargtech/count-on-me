package com.colargtech.countonme.ui.home.presenter;

import com.colargtech.countonme.commons.rx.MvpRxBasePresenter;
import com.colargtech.countonme.database.manager.CountOnMeDBManager;
import com.colargtech.countonme.database.model.GroupDB;
import com.colargtech.countonme.ui.home.view.HomeView;
import com.colargtech.countonme.ui.model.Group;

import javax.inject.Inject;
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
    private final Subject<GroupDB, GroupDB> subject;


    @Inject
    public HomePresenter(CountOnMeDBManager countOnMeDBManager, Subject<GroupDB, GroupDB> subject) {
        this.countOnMeDBManager = countOnMeDBManager;
        this.subject = subject;
    }

    @Override
    public void attachView(HomeView view) {
        super.attachView(view);
        addSubscription(subscribe(countOnMeDBManager.getAllGroups()));
        addSubscription(subscribe(subject));
    }

    public void createGroup(Group group) {
        countOnMeDBManager.createGroup(group.getName());
    }

    private Subscription subscribe(Observable<GroupDB> observable) {
        return observable.map(
                new Func1<GroupDB, Group>() {
                    @Override
                    public Group call(GroupDB groupDB) {
                        return new Group(groupDB.getId(), groupDB.getName());
                    }
                })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Group>() {
                    @Override
                    public void call(Group group) {
                        if (isViewAttached()) {
                            getView().addGroup(group);
                        }
                    }
                });
    }
}
