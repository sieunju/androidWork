package com.work.structs;

import java.io.Serializable;

/**
 * Created by hmju on 2019-04-03.
 */
public class TitleOnlyStruct implements Serializable {
    protected String title;

    public TitleOnlyStruct(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
