package com.disney.teams.core.service.logs;

public class AutoRemove<T> implements AutoCloseable {

    private ThreadLocal<T> threadLocal;

    public AutoRemove(ThreadLocal<T> threadLocal) {
        this.threadLocal = threadLocal;
    }

    public T get() {
        return threadLocal == null ? null : threadLocal.get();
    }

    @Override
    public void close() {
        if(threadLocal != null) {
            threadLocal.remove();
        }
    }

    public T closeAndGet() {
        if(threadLocal == null) {
            return null;
        }
        T t = threadLocal.get();
        threadLocal.remove();
        return t;
    }

}
