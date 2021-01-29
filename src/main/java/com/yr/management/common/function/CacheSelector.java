package com.yr.management.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
