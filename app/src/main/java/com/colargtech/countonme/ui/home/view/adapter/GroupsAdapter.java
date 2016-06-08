package com.colargtech.countonme.ui.home.view.adapter;

import android.support.v4.util.SparseArrayCompat;

import com.colargtech.countonme.commons.adapter.AdapterConstants;
import com.colargtech.countonme.commons.adapter.BaseDelegateAdapter;
import com.colargtech.countonme.ui.model.Group;

import java.util.List;

/**
 * @author juancho.
 */

public class GroupsAdapter extends BaseDelegateAdapter {

    public GroupsAdapter(GroupDelegateAdapter.GroupAdapterActions groupAdapterActions) {
        delegateAdapters = new SparseArrayCompat<>(1);
        delegateAdapters.put(AdapterConstants.GROUP, new GroupDelegateAdapter(groupAdapterActions));
    }

    public void setGroups(List<Group> groups) {
        items.addAll(groups);
        notifyItemRangeInserted(0, items.size());
    }
}
