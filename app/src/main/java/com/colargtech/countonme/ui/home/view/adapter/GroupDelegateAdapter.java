package com.colargtech.countonme.ui.home.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colargtech.adapters.ViewTypeDelegateAdapter;
import com.colargtech.countonme.R;
import com.colargtech.countonme.ui.model.GroupUI;

/**
 * @author juancho.
 */

public class GroupDelegateAdapter implements ViewTypeDelegateAdapter<GroupDelegateAdapter.GroupViewHolder, GroupUI> {

    public interface GroupAdapterActions {
        void showGroupDetail(GroupUI groupUI);
    }

    private GroupAdapterActions groupAdapterActions;

    public GroupDelegateAdapter(GroupAdapterActions groupAdapterActions) {
        this.groupAdapterActions = groupAdapterActions;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent) {
        return new GroupViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, GroupUI item) {
        holder.bind(item);
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {

        private TextView name;

        GroupViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_group_item, parent, false));
            name = (TextView) itemView.findViewById(R.id.group_name);
        }

        void bind(final GroupUI groupUI) {
            name.setText(groupUI.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    groupAdapterActions.showGroupDetail(groupUI);
                }
            });
        }
    }
}
