package com.xem.mzbphoneapp.utils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by xuebing on 15/8/5.
 */
public class SerializableMap implements Serializable {

    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
