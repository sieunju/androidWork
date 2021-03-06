package com.work;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.work.activity.BaseActivity;
import com.work.activity.ExpandableRecyclerViewActivity;
import com.work.activity.LicenseActivity;
import com.work.activity.ProgressBarActivity;
import com.work.activity.ResizeRecyclerViewActivity;

import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends BaseActivity {

    LinearLayout mSelectLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSelectLinearLayout = findViewById(R.id.select_dialog_listview);

        setData();
    }

    private void setData() {
        LinkedList<String> mList = new LinkedList<>();
        Queue<String> que = new LinkedList<String>();
        mList.add("Open License");
        mList.add("Custom ProgressView");
        mList.add("Parallax Resize View Holder");
        mList.add("Expandable Recycler View");

        Button selectButton = null;

        for (int i = 0; i < mList.size(); i++) {
            selectButton = new Button(this);
            selectButton.setText(mList.get(i));
            selectButton.setTag(i);
            selectButton.setOnClickListener(mSelectButtonListener);
            mSelectLinearLayout.addView(selectButton);
        }
    }


    Button.OnClickListener mSelectButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (Integer.parseInt(view.getTag().toString())) {
                case 0:
                    startActivity(new Intent(mContext, LicenseActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(mContext, ProgressBarActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(mContext, ResizeRecyclerViewActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(mContext, ExpandableRecyclerViewActivity.class));
                    break;
            }
        }
    };
}
