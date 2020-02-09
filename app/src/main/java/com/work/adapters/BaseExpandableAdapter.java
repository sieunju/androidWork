package com.work.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.work.holders.BaseExpandableViewHolder;
import com.work.holders.ExpandableHeaderViewHolder;
import com.work.response.BaseResponseData;
import com.work.structs.DynamicData;
import com.work.structs.GradeStruct;
import com.work.structs.TitleOnlyStruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 확장 가능한 RecyclerView Adapter
 * Created by hmju on 2019-04-02.
 */
public abstract class BaseExpandableAdapter extends RecyclerView.Adapter<BaseExpandableViewHolder> {

    protected Context mContext;
    protected List<ExpandableItemStruct<?>> mItems = new ArrayList<>();
    protected int mSize;

    // [s] row type define
    protected final String ROW_TITLE_ONLY = "title_only";
    protected final String ROW_HEADER = "header";
    // [e] row type define

    // View Type Define [Start]
    protected final int TYPE_TITLE_ONLY = 1;
    protected final int TYPE_HEADER = 2;
    // View Type Define [End]

    // 확장 가능한 헤더의 리얼 포지션 값을 Map 에 저장.
    private HashMap<Integer, Integer> mExpandsRealPositionMap = new LinkedHashMap<>();

    public class ExpandableItemStruct<ITEM> {
        ITEM data;
        int viewType;

        public ExpandableItemStruct(ITEM tmpData, int tmpType) {
            this.data = tmpData;
            this.viewType = tmpType;
        }

        public ITEM getData() {
            return data;
        }

        public int getViewType() {
            return viewType;
        }
    }

    public BaseExpandableAdapter(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseExpandableViewHolder holder, int pos) {
        Object data = mItems.get(pos).getData();
        // 더보기 버튼이 있는 헤더인경우 포지션 값 따로 셋팅.
        if (holder instanceof ExpandableHeaderViewHolder) {
            if (mExpandsRealPositionMap.containsKey(pos)) {
                holder.onBindView(mExpandsRealPositionMap.get(pos), data);
            }
        }
        // 나머지 경우
        else {
            holder.onBindView(pos, data);
        }
    }

    @Override
    public int getItemCount() {
        // Null 값 처리
        if (mItems == null) {
            return 0;
        }
        // Null 값이 아닌경우 Int 절로 size 값을 리턴
        else {
            return mSize;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getViewType();
    }

    /**
     * setData <BaseStruct>
     *
     * @param responseData ResponseResizeData
     * @author hmju
     */
    public void setData(BaseResponseData responseData) {
        if (responseData == null) {
            return;
        }

        int headerCnt = 0;
        // 타입에 맞는 데이터만 mItems 에 추가.
        for (DynamicData data : responseData.getList()) {
            switch (data.getType()) {
                case ROW_TITLE_ONLY:
                    mItems.add(new ExpandableItemStruct<TitleOnlyStruct>(data.getTitleOnly(), TYPE_TITLE_ONLY));
                    break;
                case ROW_HEADER:
                    mExpandsRealPositionMap.put(mItems.size(), headerCnt++);
                    mItems.add(new ExpandableItemStruct<GradeStruct>(data.getGrade(), TYPE_HEADER));
                    break;
            }
        }

        // Size 값 설정후 갱신 처리
        mSize = mItems.size();
        notifyDataSetChanged();
    }
}
