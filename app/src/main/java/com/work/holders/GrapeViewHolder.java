package com.work.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.work.R;
import com.work.structs.GrapeStruct;

/**
 * Created by hmju on 2019-02-24.
 */
public class GrapeViewHolder extends BaseViewHolder<GrapeStruct> {

    public GrapeViewHolder(View itemView) {
        super(itemView);
    }

    public static GrapeViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_grape, parent, false);
        return new GrapeViewHolder(view);
    }

    @Override
    public void onBindView(int pos, GrapeStruct data) {
    }
}
