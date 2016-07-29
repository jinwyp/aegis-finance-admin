package com.yimei.boot.services;

/**
 * Created by hary on 16/3/30.
 */

public class StorageException extends Exception {
    public StorageException(Throwable cause) {
        super(cause);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

