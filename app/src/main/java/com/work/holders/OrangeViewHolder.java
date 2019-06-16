package com.work.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.work.R;
import com.work.structs.OrangeStruct;

/**
 * Created by hmju on 2019-02-21.
 */
public class OrangeViewHolder extends BaseViewHolder<OrangeStruct> {

    public OrangeViewHolder(View itemView) {
        super(itemView);
    }

    public static OrangeViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_orange, parent, false);
        return new OrangeViewHolder(view);
    }

    @Override
    public void onBindView(int pos, OrangeStruct data) {
    }
}
