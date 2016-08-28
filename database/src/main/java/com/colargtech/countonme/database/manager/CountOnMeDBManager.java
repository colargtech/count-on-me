package com.colargtech.countonme.database.manager;

import com.colargtech.countonme.database.model.ActionDB;
import com.colargtech.countonme.database.model.CountDB;
import com.colargtech.countonme.database.model.GroupDB;
import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.model.ActionCount;
import com.colargtech.countonme.model.Group;
import com.colargtech.countonme.model.Range;
import com.colargtech.rx_realm.ActionWithRealm;
import com.colargtech.rx_realm.RealmObservableUtils;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.Subject;

/**
 * @author gdfesta
 */
@Singleton
public class CountOnMeDBManager {
    private final RealmConfiguration configuration;
    private final Subject<Group, Group> groupCreateSubject;
    private final Subject<Action, Action> actionCreatedSubject;
    private final Subject<Action, Action> actionUpdateSubject;
    private final Subject<ActionDB, ActionDB> actionDBUpdateSubject;
    private final String _id = "id";

    @Inject
    public CountOnMeDBManager(RealmConfiguration configuration,
                              @Named("GroupCreate") Subject<Group, Group> groupCreateSubject,
                              @Named("ActionCreate") Subject<Action, Action> actionCreatedSubject,
                              @Named("ActionUpdate") Subject<Action, Action> actionUpdateSubject,
                              @Named("ActionDBUpdate") Subject<ActionDB, ActionDB> actionDBUpdateSubject) {
        this.configuration = configuration;
        this.groupCreateSubject = groupCreateSubject;
        this.actionDBUpdateSubject = actionDBUpdateSubject;
        this.actionUpdateSubject = actionUpdateSubject;
        this.actionCreatedSubject = actionCreatedSubject;
    }

    public Observable<Group> getAllGroups() {
        return RealmObservableUtils
                .createRealObjectObservableFromRealmResultsWithRealm(new ActionWithRealm<RealmResults<GroupDB>>() {
                    @Override
                    public RealmResults<GroupDB> call(Realm realm) {
                        return realm.where(GroupDB.class).findAll();
                    }
                }, this.configuration)
                .map(new Func1<GroupDB, Group>() {
                    @Override
                    public Group call(GroupDB groupDB) {
                        return groupDB.toGroup();
                    }
                });
    }

    public Observable<Action> getAllActions(final String groupID) {
        return RealmObservableUtils
                .createRealObjectObservableFromRealmResultsWithRealm(new ActionWithRealm<RealmResults<ActionDB>>() {
                    @Override
                    public RealmResults<ActionDB> call(Realm realm) {
                        return realm.where(GroupDB.class).equalTo(_id, groupID).findFirst().getActions().sort("name");
                    }
                }, this.configuration)
                .map(new Func1<ActionDB, Action>() {
                    @Override
                    public Action call(ActionDB actionDB) {
                        return actionDB.toAction();
                    }
                });
    }

    public Observable<Group> getGroup(final String groupID) {
        return RealmObservableUtils
                .createObservableWithRealm(new ActionWithRealm<GroupDB>() {
                    @Override
                    public GroupDB call(Realm realm) {
                        return realm.where(GroupDB.class).equalTo(_id, groupID).findFirst();
                    }
                }, this.configuration)
                .map(new Func1<GroupDB, Group>() {
                    @Override
                    public Group call(GroupDB groupDB) {
                        return groupDB.toGroup();
                    }
                });
    }

    public void createGroup(final String name) {
        RealmObservableUtils
                .createObservableWithinRealmTransaction(new ActionWithRealm<GroupDB>() {
                    @Override
                    public GroupDB call(Realm realm) {
                        GroupDB groupDB = new GroupDB();
                        groupDB.setId(UUID.randomUUID().toString());
                        groupDB.setName(name);
                        groupDB.setActions(new RealmList<ActionDB>());
                        realm.copyToRealmOrUpdate(groupDB);
                        return groupDB;
                    }
                }, this.configuration)
                .map(new Func1<GroupDB, Group>() {
                    @Override
                    public Group call(GroupDB groupDB) {
                        return groupDB.toGroup();
                    }
                })
                .subscribe(new Action1<Group>() {
                    @Override
                    public void call(Group group) {
                        groupCreateSubject.onNext(group);
                    }
                });
    }

    public Observable<Void> deleteGroup(final String groupID) {
        return RealmObservableUtils.createObservableWithinRealmTransaction(new ActionWithRealm<Void>() {
            @Override
            public Void call(Realm realm) {
                GroupDB groupDB = realm.where(GroupDB.class).equalTo(_id, groupID).findFirst();

                for (int i = groupDB.getActions().size() - 1; i >= 0; i--) {
                    ActionDB actionDb = groupDB.getActions().get(i);
                    actionDb.getCounts().deleteAllFromRealm();
                    actionDb.deleteFromRealm();
                }
                groupDB.deleteFromRealm();
                return null;
            }
        }, this.configuration);
    }

    public void createAction(final String groupId, final String name) {
        RealmObservableUtils
                .createObservableWithinRealmTransaction(new ActionWithRealm<ActionDB>() {
                    @Override
                    public ActionDB call(Realm realm) {
                        GroupDB groupDB = realm.where(GroupDB.class).equalTo(_id, groupId).findFirst();
                        ActionDB actionDB = new ActionDB();
                        actionDB.setId(UUID.randomUUID().toString());
                        actionDB.setName(name);
                        groupDB.addAction(actionDB);
                        realm.copyToRealmOrUpdate(groupDB);
                        return actionDB;
                    }
                }, this.configuration)
                .map(new Func1<ActionDB, Action>() {
                    @Override
                    public Action call(ActionDB groupDB) {
                        return groupDB.toAction();
                    }
                })
                .subscribe(new Action1<Action>() {
                    @Override
                    public void call(Action action) {
                        actionCreatedSubject.onNext(action);
                    }
                });
    }

    public void addCount(final String actionId, final Date date, final long counts) {
        RealmObservableUtils
                .createObservableWithinRealmTransaction(new ActionWithRealm<ActionDB>() {
                    @Override
                    public ActionDB call(Realm realm) {
                        ActionDB actionDB = realm.where(ActionDB.class).equalTo(_id, actionId).findFirst();
                        for (long count = 0; count < counts; count++) {
                            CountDB countDB = new CountDB();
                            countDB.setId(UUID.randomUUID().toString());
                            countDB.setDate(date);
                            actionDB.getCounts().add(countDB);
                        }
                        realm.copyToRealmOrUpdate(actionDB);
                        return actionDB;
                    }
                }, this.configuration)
                .subscribe(new Action1<ActionDB>() {
                    @Override
                    public void call(ActionDB actionDB) {
                        actionDBUpdateSubject.onNext(actionDB);
                    }
                });
    }

    public void addCustom(final String actionId, final Date date) {
        RealmObservableUtils
                .createObservableWithinRealmTransaction(new ActionWithRealm<ActionDB>() {
                    @Override
                    public ActionDB call(Realm realm) {
                        ActionDB actionDB = realm.where(ActionDB.class).equalTo(_id, actionId).findFirst();
                        long counts = actionDB.getIncrementBy();
                        for (long count = 0; count < counts; count++) {
                            CountDB countDB = new CountDB();
                            countDB.setId(UUID.randomUUID().toString());
                            countDB.setDate(date);
                            actionDB.getCounts().add(countDB);
                        }
                        realm.copyToRealmOrUpdate(actionDB);
                        return actionDB;
                    }
                }, this.configuration)
                .subscribe(new Action1<ActionDB>() {
                    @Override
                    public void call(ActionDB actionDB) {
                        actionDBUpdateSubject.onNext(actionDB);
                    }
                });
    }

    public void removeCounts(final String actionId, final Date date, final long counts) {
        //TODO
    }

    public void decreaseCustom(String actionId, Date date) {
        //TODO
    }

    public Observable<ActionCount> getCountForAction(final String actionID, final Range range) {
        return RealmObservableUtils
                .createObservableWithRealm(new ActionWithRealm<RealmResults<CountDB>>() {
                    @Override
                    public RealmResults<CountDB> call(Realm realm) {
                        ActionDB actionDB = realm.where(ActionDB.class).equalTo("id", actionID).findFirst();
                        return actionDB.getCounts().where().between("date", range.from, range.to).findAll();
                    }
                }, this.configuration)
                .map(new Func1<RealmResults<CountDB>, ActionCount>() {
                    @Override
                    public ActionCount call(RealmResults<CountDB> countDBs) {
                        return new ActionCount(actionID, range, countDBs.size());
                    }
                });
    }

    public Observable<ActionCount> subscribeUpdateOnActionCount(final String actionId, final Range range) {
        return actionDBUpdateSubject.filter(new Func1<ActionDB, Boolean>() {
            @Override
            public Boolean call(ActionDB actionDB) {
                return actionDB.getId().equals(actionId);
            }
        }).map(new Func1<ActionDB, RealmResults<CountDB>>() {
            @Override
            public RealmResults<CountDB> call(ActionDB actionDB) {
                return actionDB.getCounts().where().between("date", range.from, range.to).findAll();
            }
        }).map(new Func1<RealmResults<CountDB>, ActionCount>() {
            @Override
            public ActionCount call(RealmResults<CountDB> countDBs) {
                return new ActionCount(actionId, range, countDBs.size());
            }
        });
    }
}
