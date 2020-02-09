package com.work.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.work.R;
import com.work.structs.PeopleStruct;

/**
 * Created by hmju on 2019-04-03.
 */
public class ExpandableChildViewHolder extends BaseExpandableViewHolder<PeopleStruct> {

    TextView mChildTextView;

    public ExpandableChildViewHolder(View itemView) {
        super(itemView);
        mChildTextView = itemView.findViewById(R.id.child_text_view);
    }

    public static ExpandableChildViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_expandable_child, parent, false);
        return new ExpandableChildViewHolder(view);
    }

    @Override
    public void onBindView(int pos, @NonNull PeopleStruct data) {
        setData(data);
    }

    private void setData(PeopleStruct data) {
        if (data == null) {
            return;
        }

        if (mChildTextView != null) {
            mChildTextView.setText(String.format("%s,%s", data.getName(), data.getAge()));
        }
    }
}
