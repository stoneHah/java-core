package com.zq.learn.java.java8;

public interface Filter<T> {
    boolean match(T value);
}
