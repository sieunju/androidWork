package com.work.structs;

import java.io.Serializable;

/**
 * Struct Class
 * Created by hmju on 2019-04-02.
 */
public class PeopleStruct implements Serializable {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }
}
