package org.jerry.jorm.exception;

/**
 * Created by yong_pliang on 15/2/10.
 */
public class NoIdAnnotationException  extends RuntimeException  {

    public NoIdAnnotationException() {
    }

    public NoIdAnnotationException(String message) {
        super(message);
    }

    public NoIdAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoIdAnnotationException(Throwable cause) {
        super(cause);
    }

    public NoIdAnnotationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
