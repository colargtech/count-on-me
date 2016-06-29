package com.colargtech.countonme.database.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author gdfesta
 */

public class GroupDB extends RealmObject {
    @PrimaryKey
    private String id;

    @Required
    private String name;

    private RealmList<ActionDB> actions;

    public GroupDB() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<ActionDB> getActions() {
        return actions;
    }

    public void setActions(RealmList<ActionDB> actions) {
        this.actions = actions;
    }
}
