package com.colargtech.countonme.ui.home.view.adapter;

import android.support.v4.util.SparseArrayCompat;

import com.colargtech.countonme.commons.adapter.AdapterConstants;
import com.colargtech.countonme.commons.adapter.BaseDelegateAdapter;
import com.colargtech.countonme.commons.adapter.ViewTypeDelegateAdapter;
import com.colargtech.countonme.ui.model.Group;

import java.util.List;

/**
 * @author juancho.
 */

public class GroupsAdapter extends BaseDelegateAdapter {

    @Override
    protected SparseArrayCompat<ViewTypeDelegateAdapter> getDelegateAdapters() {
        SparseArrayCompat<ViewTypeDelegateAdapter> adapters = new SparseArrayCompat<>(1);
        adapters.put(AdapterConstants.GROUP, new GroupDelegateAdapter());
        return adapters;
    }

    public void setGroups(List<Group> groups) {
        items.addAll(groups);
        notifyItemRangeInserted(0, items.size());
    }
}
