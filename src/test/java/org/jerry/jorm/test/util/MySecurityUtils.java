package org.jerry.jorm.test.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by mryong on 13-11-2.
 */
public final class MySecurityUtils {
    /**
     * 功能：MD5 加密
     *
     * @param source byte[]
     */

    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 获得MD5消息摘要
     *
     * @param source
     * @param charSet
     * @return
     * @throws Exception
     */
    public static String getMessageDigest(String source, String charSet) throws Exception {

        byte[] bsi = source.getBytes(charSet);
        byte[] bso = MessageDigest.getInstance("MD5").digest(bsi);
        return encodeHex(bso);

    }

    /**
     * 获得MD5消息摘要,UTF-8编码
     *
     * @param source
     * @return
     */
    public static String getMessageDigest(String source) {
        try {
            return getMessageDigest(source, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


    /**
     * 获得MD5消息摘要,UTF-8编码
     *
     * @param source
     * @return
     */
    public static String getMessageDigest(String source, int count) {
        try {
            for (int i = 0; i < count; i++) {
                source = getMessageDigest(source, "utf-8");
            }
            return source;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


    /**
     * 以base64加密
     *
     * @param source
     * @param charSet
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public static String base64Code(String source, String charSet) throws UnsupportedEncodingException {

        BASE64Encoder bASE64Encoder = new BASE64Encoder();
        return bASE64Encoder.encode(source.getBytes(charSet));

    }

    /**
     * 以base64加密，以UTF-8编码
     *
     * @param source
     * @return
     */
    public static String base64Code(String source) {
        try {
            return base64Code(source, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    /**
     * 以base64解密
     *
     * @param source
     * @param charSet
     * @return
     * @throws java.io.IOException
     */
    public static String base64Decode(String source, String charSet) throws IOException {
        BASE64Decoder bASE64Decoder = new BASE64Decoder();
        byte[] bs = bASE64Decoder.decodeBuffer(source);
        return new String(bs, charSet);
    }

    /**
     * 以base64解密，以UTF-8编码
     *
     * @param source
     * @return
     */
    public static String base64Decode(String source) {
        try {
            return base64Decode(source, "utf-8");
        } catch (IOException ex) {
            return null;
        }
    }


    /**
     * 十六进制编码
     *
     * @param bytes
     * @return
     */
    public static String encodeHex(byte[] bytes) {
        StringBuffer buffer = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buffer.append("0");
            }
            buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buffer.toString();
    }


    public static String getUUID() {

        String id = UUID.randomUUID().toString();

        // 去掉“-”符号

        return id.replaceAll("-", "");

    }


}
