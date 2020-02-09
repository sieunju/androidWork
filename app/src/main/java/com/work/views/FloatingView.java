package com.work.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.work.R;

/**
 * Created by hmju on 2019-03-21.
 */
public class FloatingView extends LinearLayout {

    Button mTopButton;

    public FloatingView(Context context) {
        this(context, null);
    }

    public FloatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_floating_btn, this, false);
        mTopButton = view.findViewById(R.id.top_button);
        addView(view);
    }


}
