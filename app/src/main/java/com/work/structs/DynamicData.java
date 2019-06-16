package com.work.structs;

/**
 * Dynamic Data Model Class
 * Created by hmju on 2019-04-04.
 */
public class DynamicData extends BaseStruct {
    protected AppleStruct struct_apple;
    protected GrapeStruct struct_grape;
    protected OrangeStruct struct_orange;
    protected ResizeStruct struct_resize;
    protected TitleOnlyStruct struct_title_only;
    protected GradeStruct struct_grade;

    public AppleStruct getApple() {
        return struct_apple;
    }

    public GrapeStruct getGrape() {
        return struct_grape;
    }

    public OrangeStruct getOrange() {
        return struct_orange;
    }

    public ResizeStruct getResize() {
        return struct_resize;
    }

    public TitleOnlyStruct getTitleOnly() {
        return struct_title_only;
    }

    public GradeStruct getGrade() {
        return struct_grade;
    }
}
