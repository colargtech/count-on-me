package com.colargtech.countonme.ui.home.view.adapter;

import android.support.v4.util.SparseArrayCompat;

import com.colargtech.adapters.BaseDelegateAdapter;
import com.colargtech.adapters.ViewType;
import com.colargtech.countonme.ui.adapter.AdapterConstants;
import com.colargtech.countonme.ui.model.GroupUI;

/**
 * @author juancho.
 */
public class GroupsAdapter extends BaseDelegateAdapter {

    public GroupsAdapter(GroupDelegateAdapter.GroupAdapterActions groupAdapterActions) {
        delegateAdapters = new SparseArrayCompat<>(1);
        delegateAdapters.put(AdapterConstants.GROUP, new GroupDelegateAdapter(groupAdapterActions));
    }

    public void addGroup(GroupUI groupUI) {
        if (!items.contains(groupUI)) {
            int position = items.size();
            items.add(groupUI);
            notifyItemInserted(position);
        }
    }

    public void removeGroup(String groupId) {
        for (int i = 0; i < items.size(); i++) {
            ViewType item = items.get(i);
            if (item instanceof GroupUI && ((GroupUI) item).getId().equals(groupId)) {
                items.remove(item);
                notifyItemRemoved(i);
                break;
            }
        }
    }
}
