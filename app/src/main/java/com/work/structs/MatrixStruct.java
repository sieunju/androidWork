package com.work.structs;

/**
 * Columns And Row Info Data Model.
 * Created by hmju on 2019-04-20.
 */
public class MatrixStruct extends BaseStruct {
    String tag = "";
    int pos = 0;
    int column = 0; // <->
    int row = 0;    // |

    public MatrixStruct(int pos) {
        this(pos, -1, -1);
    }

    public MatrixStruct(int column, int row) {
        this(-1, column, row);
    }

    public MatrixStruct(int pos, int column, int row) {
        this.pos = pos;
        this.column = column;
        this.row = row;
    }

    public MatrixStruct setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public int getPos() {
        return pos;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "Pos\t" + pos + "\tColumn\t" + column + "\tRow\t" + row + "\tTag\t" + tag;
    }
}
