package com.work.response;

import java.io.Serializable;
import java.util.ArrayList;

import com.work.structs.DynamicData;

/**
 * Resize Response Data
 * Created by hmju on 2019-04-04.
 */
public class BaseResponseData implements Serializable {

    private ArrayList<DynamicData> list;

    public ArrayList<DynamicData> getList() {
        return list;
    }
}
