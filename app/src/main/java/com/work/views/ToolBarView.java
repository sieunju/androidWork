package com.work.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.work.R;

import butterknife.ButterKnife;

/**
 * Created by hmju on 2019-03-21.
 */
public class ToolBarView extends RelativeLayout {

    public ToolBarView(Context context) {
        this(context, null);
    }

    public ToolBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this, false);
        ButterKnife.bind(this, view);
        addView(view);
    }
}
