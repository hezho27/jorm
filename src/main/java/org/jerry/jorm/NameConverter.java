package org.jerry.jorm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yong_pliang on 14/10/29.
 */
public class NameConverter {

    public static String sqlName2javaName(String sqlName) {
        String javaName = sqlName;
        Pattern r = Pattern.compile("_.");
        Matcher m = r.matcher(javaName);
        while (m.find()) {
            String src = m.group();
            String des = src.substring(1).toUpperCase();
            javaName = javaName.replaceFirst(src, des);
        }
        return javaName;
    }

    public static String javaName2sqlName(String javaName) {
        String sqlName = javaName;
        Pattern r = Pattern.compile("[a-z][A-Z]");
        Matcher m = r.matcher(sqlName);
        while (m.find()) {
            String src = m.group();
            String des = src.substring(0, 1) + "_" + src.substring(1).toLowerCase();
            sqlName = sqlName.replaceFirst(src, des);
        }
        return sqlName;
    }


    public static void main(String[] args) {
        System.out.println(javaName2sqlName("myUserNameInSchoolButWorldTip"));
    }
}
