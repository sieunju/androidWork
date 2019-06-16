package com.work.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.work.holders.AppleViewHolder;
import com.work.holders.BaseViewHolder;
import com.work.holders.GrapeViewHolder;
import com.work.holders.OrangeViewHolder;
import com.work.holders.ResizeViewHolder;

/**
 * RecyclerView Adapter Class
 * (과일 구조체로 있는 어답터)
 * Created by hmju on 2019-02-20.
 */
public class FruitAdapter extends BaseAdapter {

    public FruitAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_APPLE:
                return AppleViewHolder.newInstance(parent);
            case TYPE_ORANGE:
                return OrangeViewHolder.newInstance(parent);
            case TYPE_GRAPE:
                return GrapeViewHolder.newInstance(parent);
            case TYPE_RESIZE:
                return ResizeViewHolder.newInstance(parent);
            default:
                return null;
        }
    }
}
