package com.colargtech.countonme.ui.home.presenter;

import com.colargtech.countonme.commons.mvp.MvpBasePresenter;
import com.colargtech.countonme.database.manager.CountOnMeDBManager;
import com.colargtech.countonme.database.model.GroupDB;
import com.colargtech.countonme.ui.home.view.HomeView;
import com.colargtech.countonme.ui.model.Group;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author juancho.
 */
@Singleton
public class HomePresenter extends MvpBasePresenter<HomeView> {

    private final CountOnMeDBManager countOnMeDBManager;

    @Inject
    public HomePresenter(CountOnMeDBManager countOnMeDBManager) {
        this.countOnMeDBManager = countOnMeDBManager;
    }

    public void init() {
        countOnMeDBManager.getAllGroups()
                .map(new Func1<GroupDB, Group>() {
                    @Override
                    public Group call(GroupDB groupDB) {
                        return new Group(groupDB.getId(), groupDB.getName());
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Group>>() {
                    @Override
                    public void call(List<Group> groups) {
                        if (isViewAttached()) {
                            getView().showGroups(groups);
                        }
                    }
                });
    }
}
