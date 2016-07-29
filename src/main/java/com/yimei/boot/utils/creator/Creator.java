package com.yimei.boot.utils.creator;

public interface Creator<T> {

    public T create(Object source);
}
