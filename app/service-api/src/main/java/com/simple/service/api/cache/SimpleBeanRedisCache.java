package com.simple.service.api.cache;

import java.util.List;
import java.util.Set;

public interface SimpleBeanRedisCache {
    void save(final List<String> beanNames);
    Set<String> get();
    void delete();
}
