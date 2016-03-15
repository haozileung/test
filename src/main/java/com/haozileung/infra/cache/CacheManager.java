package com.haozileung.infra.cache;

import java.io.Serializable;

public interface CacheManager {

    void init(CacheProvider provider);

    void destroy();

    <T extends Serializable> T get(String name, String key);

    <T extends Serializable> T get(Class<T> clazz, String name, String key);

    <T extends Serializable> void set(String name, String key, T value);

    void evict(String name, String key);

}
