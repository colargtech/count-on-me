package com.colargtech.countonme.ui.model;

import com.colargtech.adapters.ViewType;
import com.colargtech.countonme.ui.adapter.AdapterConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gdfesta
 */
public class GroupUI implements ViewType {
    private final String id;
    private final String name;

    public GroupUI(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getViewType() {
        return AdapterConstants.GROUP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupUI groupUI = (GroupUI) o;

        return id != null ? id.equals(groupUI.id) : groupUI.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
