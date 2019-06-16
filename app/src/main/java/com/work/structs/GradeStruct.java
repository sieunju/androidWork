package com.work.structs;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hmju on 2019-04-03.
 */
public class GradeStruct implements Serializable {
    protected String grade;
    protected ArrayList<PeopleStruct> list;

    public String getGrade() {
        return grade;
    }

    public ArrayList<PeopleStruct> getList() {
        return list;
    }
}
