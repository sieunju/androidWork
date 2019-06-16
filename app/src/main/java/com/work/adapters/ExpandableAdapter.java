package com.work.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.work.holders.BaseExpandableViewHolder;
import com.work.holders.ExpandableHeaderViewHolder;
import com.work.holders.ExpandableOnlyTextViewHolder;

/**
 * 펼처짐이 가능한 어답터 Class
 * Created by hmju on 2019-04-02.
 */
public class ExpandableAdapter extends BaseExpandableAdapter {

    public ExpandableAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseExpandableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_TITLE_ONLY:
                return ExpandableOnlyTextViewHolder.newInstance(viewGroup);
            case TYPE_HEADER:
                return ExpandableHeaderViewHolder.newInstance(viewGroup);
            default:
                return null;
        }
    }
}
