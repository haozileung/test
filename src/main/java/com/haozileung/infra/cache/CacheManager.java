package com.haozileung.infra.cache;

public interface CacheManager {

    void init(CacheProvider provider);

    void destroy();

    <T> T get(String name, String key);

    <T> T get(Class<T> clazz, String name, String key);

    <T> void set(String name, String key, T value);

    void evict(String name, String key);

}
