package com.colargtech.countonme.database.model;

import com.colargtech.countonme.model.Action;
import com.colargtech.countonme.model.Period;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import rx.Observable;

/**
 * @author gdfesta
 */

public class ActionDB extends RealmObject {
    @PrimaryKey
    private String id;

    @Required
    private String name;

    private RealmList<CountDB> counts;

    private int incrementBy;

    private int maxPerPeriod;

    public ActionDB() {
        counts = new RealmList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<CountDB> getCounts() {
        return counts;
    }

    public void setCounts(RealmList<CountDB> counts) {
        this.counts = counts;
    }

    public int getMaxPerPeriod() {
        return maxPerPeriod;
    }

    public void setMaxPerPeriod(int maxPerPeriod) {
        this.maxPerPeriod = maxPerPeriod;
    }

    public int getIncrementBy() {
        return incrementBy;
    }

    public void setIncrementBy(int incrementBy) {
        this.incrementBy = incrementBy;
    }

    public Action toAction() {
        Action.Builder builder = new Action.Builder(id, name, Period.DAY, incrementBy);
        return builder.build();
    }
}
