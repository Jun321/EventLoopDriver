package com.ed.container;

import com.ed.model.EventTaskGroup;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LoopCache {
    private final ConcurrentLinkedQueue<EventTaskGroup> queue = new ConcurrentLinkedQueue<>();


    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void addGroup(EventTaskGroup group) {
        queue.add(group);
    }

    public EventTaskGroup getGroup() {
        return queue.poll();
    }
}
