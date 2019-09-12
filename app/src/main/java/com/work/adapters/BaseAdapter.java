package com.work.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import com.work.holders.BaseViewHolder;
import com.work.holders.ResizeViewHolder;
import com.work.response.BaseResponseData;
import com.work.itemDecoration.HeaderItemDecoration;
import com.work.structs.AppleStruct;
import com.work.structs.DynamicData;
import com.work.structs.GrapeStruct;
import com.work.structs.OrangeStruct;
import com.work.structs.ResizeStruct;

/**
 * BaseAdapter Class
 * Created by hmju on 2019-02-20.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext;
    protected ArrayList<ItemStruct<?>> mItems = new ArrayList<ItemStruct<?>>();
    protected int mSize;

    // [s] row type define
    protected final String ROW_RESIZE = "expands";
    protected final String ROW_APPLE = "apple";
    protected final String ROW_ORANGE = "orange";
    protected final String ROW_GRAPE = "grape";
    // [e] row type define

    // [s] View Type Define
    protected final int TYPE_APPLE = 1;
    protected final int TYPE_GRAPE = 2;
    protected final int TYPE_ORANGE = 3;
    protected final int TYPE_RESIZE = 4;
    protected final int TYPE_PINTEREST = 5;
    // [e] View Type Define

    public static class ItemStruct<ITEM> {
        public ITEM data;
        public int viewType;

        public ItemStruct(ITEM data, int type) {
            this.data = data;
            this.viewType = type;
        }

        ITEM getData() {
            return data;
        }

        int getViewType() {
            return viewType;
        }
    }

    public BaseAdapter(Context context) {
        mContext = context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int pos) {
        holder.onBindView(pos, mItems.get(pos).getData());
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

    @Override
    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
        // ResizeViewHolder
        if (holder instanceof ResizeViewHolder) {
            ((ResizeViewHolder) holder).onEnabled();
        }
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        // ResizeViewHolder
        if (holder instanceof ResizeViewHolder) {
            ((ResizeViewHolder) holder).onDisabled();
        }
        super.onViewDetachedFromWindow(holder);
    }

    /**
     * setData <BaseStruct>
     *
     * @param responseData
     * @author hmju
     */
    public void setData(BaseResponseData responseData, HeaderItemDecoration.StickyHeaderInterface listener) {
        if (responseData == null) {
            return;
        }

        ArrayList<DynamicData> list = responseData.getList();
        HashMap<Integer, String> headerMap = new HashMap<>();
        // 타입에 맞는 데이터만 mItems 에 추가.
        for (DynamicData data : list) {
            switch (data.getType()) {
                case ROW_APPLE:
                    mItems.add(new ItemStruct<AppleStruct>(data.getApple(), TYPE_APPLE));
                    break;
                case ROW_GRAPE:
                    mItems.add(new ItemStruct<GrapeStruct>(data.getGrape(), TYPE_GRAPE));
                    break;
                case ROW_ORANGE:
                    mItems.add(new ItemStruct<OrangeStruct>(data.getOrange(), TYPE_ORANGE));
                    break;
                case ROW_RESIZE:
                    headerMap.put(mItems.size(), data.getResize().getTitle());
                    mItems.add(new ItemStruct<ResizeStruct>(data.getResize(), TYPE_RESIZE));
                    break;
            }
        }

        listener.setHeaderMap(headerMap);
        // Size 값 설정후 갱신 처리
        mSize = mItems.size();
        notifyDataSetChanged();
    }
}
