package org.jerry.jorm.exception;

/**
 * Created by yong_pliang on 14/11/23.
 */
public class NotOneResultException extends RuntimeException {
    public NotOneResultException() {
    }

    public NotOneResultException(String message) {
        super(message);
    }

    public NotOneResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotOneResultException(Throwable cause) {
        super(cause);
    }

    public NotOneResultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
