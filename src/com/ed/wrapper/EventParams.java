package com.ed.wrapper;

import java.util.HashMap;

public class EventParams {
    private HashMap<String, Object> fieldMap = new HashMap<>();

    public EventParams set(String name, Object val){
        this.fieldMap.put(name, val);
        return this;
    }
}
