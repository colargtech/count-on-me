package com.colargtech.countonme.model;

import java.util.List;

/**
 * @author gdfesta
 */
public class Group {
    public final String id;
    public final String name;
    public final List<Action> actions;

    public Group(String id, String name, List<Action> actions) {
        this.id = id;
        this.name = name;
        this.actions = actions;
    }
}
