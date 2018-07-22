package com.ed.test;

import java.util.*;

public class MainTest {
    private static volatile List<Integer> list = new LinkedList<>();
    public static void main(String args[]){
        for (int i = 0; i < 10; i++){
            if (i == 0)
                new Thread(new Write(list, i)).start();
            new Thread(new Read(list, i)).start();
        }
    }

    public static void print(String val) {
        System.out.println(val);
    }
}

class Read implements Runnable{
    List<Integer> list; int id;
    public Read(List<Integer> list, int id){
        this.list = list;
        this.id = id;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Read"+id);
        int times = 0;
        while (times < 10){
            times++;
            MainTest.print(Arrays.toString(list.toArray()));
        }
    }
}

class Write implements Runnable{
    List<Integer> list; int id;
    public Write(List<Integer> list, int id){
        this.list = list;
        this.id = id;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Write"+id);
        int times = 0;
        while (times < 10){
            times++;
            Integer i = new Random().nextInt(10);
            list.add(i);
        }
    }
}
