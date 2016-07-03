package com.colargtech.countonme.ui.home.view.adapter;

import android.support.v4.util.SparseArrayCompat;

import com.colargtech.adapters.BaseDelegateAdapter;
import com.colargtech.countonme.ui.adapter.AdapterConstants;
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

    public void addGroup(Group group) {
        int position = items.size();
        items.add(group);
        notifyItemInserted(position);
    }
}
