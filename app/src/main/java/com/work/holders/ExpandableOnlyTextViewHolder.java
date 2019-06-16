package com.work.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import butterknife.BindView;
import com.work.R;
import com.work.structs.TitleOnlyStruct;

/**
 * 확장 가능한 RecyclerView 에서 텍스트만 있는 ViewHolder Class
 * Created by hmju on 2019-04-03.
 */
public class ExpandableOnlyTextViewHolder extends BaseExpandableViewHolder<TitleOnlyStruct> {

    @BindView(R.id.expandable_only_text_view)
    TextView mTitleTextView;

    public ExpandableOnlyTextViewHolder(View itemView) {
        super(itemView);
    }

    public static ExpandableOnlyTextViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_expandable_text_only, parent, false);
        return new ExpandableOnlyTextViewHolder(view);
    }

    @Override
    public void onBindView(int pos, @NonNull TitleOnlyStruct data) {
        setData(data);
    }

    /**
     * setData
     *
     * @param data TitleOnlyStruct
     * @author hmju
     */
    private void setData(TitleOnlyStruct data) {
        if (data == null) {
            return;
        }

        if (mTitleTextView != null) {
            mTitleTextView.setText(data.getTitle());
        }
    }
}
