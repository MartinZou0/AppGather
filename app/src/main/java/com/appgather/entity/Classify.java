package com.appgather.entity;

import java.util.List;

/**

 * 应用分类标签实体
 */

//已完成
    //应用实体
public class Classify {

    private String className;
    private int type;
    private boolean isSelect=false;
    public Classify()
    {

    }

    public Classify(String className, int type) {
        this.className = className;
        this.type = type;
    }

    public Classify(String className, int type, boolean isSelect) {
        this.className = className;
        this.type = type;
        this.isSelect = isSelect;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "Classify{" +
                "className='" + className + '\'' +
                ", type=" + type +
                '}';
    }
}
