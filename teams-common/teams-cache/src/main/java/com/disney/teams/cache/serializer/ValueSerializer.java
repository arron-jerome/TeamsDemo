/*
 * Copyright 2016-2020 1HAITAO.COM. All rights reserved.
 */
package com.disney.teams.cache.serializer;

/**
 * Created by htoo on 9/30/15.
 * 对象序列化接口
 */
public interface ValueSerializer<S> {

    /**
     * 反序列化S为对象T
     */
    <T> T unSerialize(S value);

    <T> T unSerialize(String value, Class<T> clz);

    /**
     * 序列化对象T为S
     */
    <T> S serialize(T value);
}
