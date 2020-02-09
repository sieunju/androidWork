package com.work.holders;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.work.R;
import com.work.utils.Logger;

/**
 * BaseViewHolder Class
 * Created by hmju on 2019-02-20.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public abstract void onBindView(int pos, @NonNull T data);

    protected Context mContext;
    protected View mRootView;
    protected T mData;

    protected RelativeLayout mRootLayout;
    protected TextView mRootTitleTextView;
    protected TextView mRootMsgTextView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mRootView = itemView;
        initCommonView(itemView);
    }

    private void initCommonView(View view){
        mRootLayout = view.findViewById(R.id.root_rl_layout);
        mRootTitleTextView = view.findViewById(R.id.root_tv_title);
        mRootMsgTextView = view.findViewById(R.id.root_tv_msg);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_rl_layout:
                    Logger.d("전체 레이아웃 클릭!");
                    break;
                case R.id.root_tv_title:
                    Logger.d("타이틀 텍스트 클릭!");
                    break;
                case R.id.root_tv_msg:
                    Logger.d("메시지 텍스트 클릭!");
                    break;
            }
        }
    };
}
