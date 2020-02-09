package com.work.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.work.R;
import com.work.adapters.ExpandableAdapter;
import com.work.response.BaseResponseData;
import com.work.utils.CommandUtil;

public class ExpandableRecyclerViewActivity extends BaseActivity {

    RecyclerView mRecyclerView;

    private ExpandableAdapter mAdapter;
    private BaseResponseData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_recycler_view);

        mRecyclerView = findViewById(R.id.recycler_view);
        setData();
        setAdapter();
    }

    /**
     * set Dummy Data
     *
     * @author hmju
     */
    private void setData() {
        mData = CommandUtil.getInstance().getFileToJson(mContext, "json_expandable_activity.txt", BaseResponseData.class);
    }

    /**
     * setAdapter
     *
     * @author hmju
     */
    private void setAdapter() {
        mAdapter = new ExpandableAdapter(mContext);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setData(mData);
    }
}
