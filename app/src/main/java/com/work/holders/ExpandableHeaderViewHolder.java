package com.work.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import com.work.R;
import com.work.structs.GradeStruct;
import com.work.structs.PeopleStruct;
import com.work.utils.Logger;

/**
 * 확장 가능한 뷰홀더 중 헤더 뷰 홀더
 * Created by hmju on 2019-04-02.
 */
public class ExpandableHeaderViewHolder extends BaseExpandableViewHolder<GradeStruct> implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.title_text_view)
    TextView mTitleTextView;
    @BindView(R.id.more_check_box)
    CheckBox mMoreImageView;
    @BindView(R.id.child_recycler_view)
    RecyclerView mChildRecyclerView;

    ExpandableChildAdapter mAdapter;

    public ExpandableHeaderViewHolder(View itemView) {
        super(itemView);
        initView();
    }

    public static ExpandableHeaderViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_expandable_header, parent, false);
        return new ExpandableHeaderViewHolder(view);
    }

    private void initView() {
        if (mMoreImageView != null) {
            mMoreImageView.setOnCheckedChangeListener(this);
        }

        mAdapter = new ExpandableChildAdapter();
        mChildRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mChildRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBindView(int pos, @NonNull GradeStruct data) {
        setData(pos, data);
    }

    private void setData(int pos, GradeStruct data) {
        if (data == null) {
            return;
        }

        if (pos == 0) {
            mMoreImageView.setChecked(true);
        }

        if (mTitleTextView != null) {
            mTitleTextView.setText(data.getGrade());
        }

        if (mAdapter != null) {
            mAdapter.setData(data.getList());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        switch (button.getId()) {
            case R.id.more_check_box:
                // 체크박스가 선택 되었을때.
                if (isChecked) {
                    Logger.d("More Check Enabled");
                    mChildRecyclerView.setVisibility(View.VISIBLE);
                }
                // 체크박스가 선택 헤제 되었을때.
                else {
                    Logger.d("More Check Disabled");
                    mChildRecyclerView.setVisibility(View.GONE);
                }
                break;
        }
    }

    /**
     * Expands Child Adapter Class
     *
     * @author jsieun
     */
    private class ExpandableChildAdapter extends RecyclerView.Adapter<ExpandableChildViewHolder> {

        ArrayList<PeopleStruct> mList;
        int mSize = 0;

        private ExpandableChildAdapter() {
        }

        /**
         * 데이터 셋팅
         *
         * @param list
         */
        public void setData(ArrayList<PeopleStruct> list) {
            mList = list;
            mSize = list.size();
        }

        @NonNull
        @Override
        public ExpandableChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
            return ExpandableChildViewHolder.newInstance(viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull ExpandableChildViewHolder viewHolder, int pos) {
            viewHolder.onBindView(pos, mList.get(pos));
        }

        @Override
        public int getItemCount() {
            return mSize;
        }
    }
}
