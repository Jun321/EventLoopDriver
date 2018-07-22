package com.ed.wrapper;

public class Pair<K, V> {
    K a; V b;
    public Pair(K a, V b){
        this.a = a; this.b = b;
    }

    public K A(){
        return a;
    }

    public V B(){
        return b;
    }
}
