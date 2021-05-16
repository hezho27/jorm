package org.jerry.jorm.annotation;


@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Temporal {
    /**
     * The type used in mapping <code>java.util.Date</code> or <code>java.util.Calendar</code>.
     */
    TemporalType value() default TemporalType.TIMESTAMP;
}
