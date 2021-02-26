package org.jerry.jorm.util;

import java.util.Date;

/**
 * Created by yong_pliang on 14/10/29.
 */
public class ClassUtils {
    public static boolean isBaseType(Class<?> calzz) {

        if (calzz == String.class) {
            return true;
        }
        if (calzz == Long.class) {
            return true;
        }
        if (calzz == Date.class) {
            return true;
        }
        if (calzz == Double.class) {
            return true;
        }
        if (calzz == Boolean.class) {
            return true;
        }

        if (calzz == Integer.class) {
            return true;
        }
        if (calzz == Float.class) {
            return true;
        }
        if (calzz == Short.class) {
            return true;
        }
        if (calzz.isEnum()){
            return true;
        }
        return false;

    }

}
