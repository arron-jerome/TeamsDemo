package com.disney.teams.cache.factory;

import com.disney.teams.cache.ICache;
import org.springframework.lang.Nullable;

/**
 * @author arron.zhou
 * @version 1.0.0
 * @date 2022/12/20
 * Description:
 * Modification  History:
 * Date         Author        Version        Description
 * ------------------------------------------------------
 * 2022/12/20       arron.zhou      1.0.0          create
 */
public interface ICacheFactory {

    @Nullable
    ICache getObject() throws Exception;

    @Nullable
    default Class<?> getObjectType() {
        return ICache.class;
    }

    default boolean isSingleton() {
        return true;
    }
}
