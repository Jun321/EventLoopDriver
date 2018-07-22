package com.ed.model;

import com.ed.container.LoopCache;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class EventLoop extends Thread{
    private final LoopCache cache = new LoopCache();
    private final List<EventTaskGroup> taskList = new LinkedList<>();
    private volatile boolean switchIn = false;
    private final long sleepUpperBound = 500l;
    private final int maxBatchGet = 500;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    @Override
    public void run() {

        while (true){
            lock.lock();
            try{
                while (switchIn){
                    doWork();
                    TimeUnit.MILLISECONDS.sleep(sleepUpperBound);
                }
                //go to rest
                condition.await();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    private void doWork() {
        getGroups();
        checkAndTriggerGroups();
    }

    private void checkAndTriggerGroups() {
        for(EventTaskGroup group: taskList){

        }
    }

    private void getGroups() {
        int num = 0;
        EventTaskGroup group;
        do{
            num++;
            group = cache.getGroup();
            if (group != null)
                taskList.add(group);
        }while (group != null || num < maxBatchGet);
    }

    public void addTask(EventTaskGroup group){
        cache.addGroup(group);
    }

    public synchronized boolean triggerRest(){
        boolean flag = false;
        if (cache.isEmpty() && taskList.isEmpty() && switchIn){
            flag = true;
            switchIn = false;
        }
        return flag;
    }

    public boolean triggerStart(){
        boolean flag = false;
        if (!switchIn){
            lock.lock();
            try{
                switchIn = true;
                condition.signal();
                flag = true;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        return flag;
    }
}
