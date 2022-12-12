package com.disney.teams.common.utils;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.*;

public class CollectionUtils {

    private static final Map<Class<?>, Constructor<?>> defaultInstance = new HashMap<>();

    static {
        try {
            defaultInstance.put(Collection.class, ArrayList.class.getConstructor());
            defaultInstance.put(List.class, ArrayList.class.getConstructor());
            defaultInstance.put(Set.class, HashSet.class.getConstructor());
            defaultInstance.put(SortedSet.class, TreeSet.class.getConstructor());
            defaultInstance.put(NavigableSet.class, TreeSet.class.getConstructor());
            defaultInstance.put(Queue.class, LinkedList.class.getConstructor());
            defaultInstance.put(BlockingQueue.class, LinkedBlockingQueue.class.getConstructor());
            defaultInstance.put(TransferQueue.class, LinkedTransferQueue.class.getConstructor());
            defaultInstance.put(Deque.class, LinkedList.class.getConstructor());
            defaultInstance.put(BlockingDeque.class, LinkedBlockingDeque.class.getConstructor());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        for (Constructor<?> constructor : defaultInstance.values()) {
//            if (!MemberUtils.isPublic(constructor)) {
//                throw new RuntimeException(constructor + " is not accessible");
//            }
        }
    }

    private static <T extends Collection<?>> T newInstance(Class<T> targetClass) {
        try {
            Constructor<?> contructor = defaultInstance.get(targetClass == null ? Collection.class : targetClass);
            if (contructor == null) {
                contructor = targetClass.getConstructor();
            }
            Object target = contructor.newInstance();
            return (T) target;
        } catch (Exception e) {
//            throw ExceptionUtils.uncheck(e);
            throw new RuntimeException("need to do");
        }
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T extends Collection<Q>, Q> T convert(Collection<Q> src, Class<T> targetClass) {
        //如果实现类是当前的子类，则直接返回
        if (targetClass == null || targetClass.isAssignableFrom(src.getClass())) {
            return (T) src;
        }
        T target = newInstance(targetClass);
        target.addAll(src);
        return target;
    }
}
