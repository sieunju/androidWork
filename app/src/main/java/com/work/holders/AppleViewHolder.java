package com.work.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.work.R;
import com.work.structs.AppleStruct;
import com.work.utils.Logger;

/**
 * Created by hmju on 2019-02-21.
 */
public class AppleViewHolder extends BaseViewHolder<AppleStruct> {

    public AppleViewHolder(View itemView) {
        super(itemView);
    }

    public static AppleViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_apple, parent, false);
        view.setTag("tag_apple");
        return new AppleViewHolder(view);
    }

    @Override
    public void onBindView(int pos, AppleStruct data) {
    }
}
