package com.yimei.finance.utils.creator;

public interface Creator<T> {

    public T create(Object source);
}
