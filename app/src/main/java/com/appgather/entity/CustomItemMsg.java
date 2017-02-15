package com.appgather.entity;

import java.io.Serializable;

/**
 * Created by qinghua on 2017/2/14.
 * 自定义分组的实体类
 */

public class CustomItemMsg implements Serializable {

     private  String item_namae;
     private  int type;

    public CustomItemMsg(String item_namae, int type) {
        this.item_namae = item_namae;
        this.type = type;
    }

    public String getItem_namae() {
        return item_namae;
    }

    public void setItem_namae(String item_namae) {
        this.item_namae = item_namae;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
