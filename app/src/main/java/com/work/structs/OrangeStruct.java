package com.work.structs;

import java.io.Serializable;

/**
 * Created by hmju on 2019-02-21.
 */
public class OrangeStruct implements Serializable {
    protected String origin;
    protected String price;

    public String getOrigin() {
        return origin;
    }

    public String getPrice() {
        return price;
    }
}
