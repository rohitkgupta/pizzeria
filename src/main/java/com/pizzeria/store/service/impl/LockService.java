package com.pizzeria.store.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockService {
    private static Map<Integer, ReadWriteLock> locks = new HashMap<>();
    private LockService(){}

    public static ReadWriteLock getLock(Integer itemId){
        if (!locks.containsKey(itemId)){
            locks.put(itemId, new ReentrantReadWriteLock());
        }
        return locks.get(itemId);
    }
}
