package com.colargtech.countonme.database.model;

import com.colargtech.countonme.model.Action;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author gdfesta
 */

public class ActionDB extends RealmObject {
    @PrimaryKey
    private String id;

    @Required
    private String name;

    private RealmList<CountDB> counts;

    private int maxPerPeriod;

    public ActionDB() {
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

    public Action toAction() {
        return null;
    }
}
