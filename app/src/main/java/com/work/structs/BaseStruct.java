package com.work.structs;

import java.io.Serializable;

/**
 * Base Struct Class
 * Created by hmju on 2019-02-24.
 */
public class BaseStruct implements Serializable {
    protected String type;
    protected String msg;

    public String getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
