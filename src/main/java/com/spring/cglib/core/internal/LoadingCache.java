package com.spring.cglib.core.internal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-22  23:13
 * @Version: 1.0
 */
public class LoadingCache<K, KK, V> {
    protected final ConcurrentMap<KK, Object> map;
    protected final Function<K, V> loader;
    protected final Function<K, KK> keyMapper;

    public LoadingCache(Function<K, KK> keyMapper, Function<K, V> loader) {
        this.keyMapper = keyMapper;
        this.loader = loader;
        this.map = new ConcurrentHashMap();
    }

}
