package com.crazy.portal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by Yang.dd on 2014/11/14.
 */
public class Md5Util {

    private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

    private Md5Util(){}
    /**
     * md5加密
     * @param str 明文
     * @param bit 16或32
     * @param capital   true：转大写，false 小写
     * @return
     */
    public  static String encryption(String str,int bit,boolean capital){
        String result=core(str);
        if(bit==16){
            result = result.substring(8,24);
        }
        if(capital){
            result = result.toUpperCase();
        }
        return  result;
    }

    /**
     * MD5加密算法
     * @param plainText
     * @return
     */
    private static String core(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        }
        return re_md5;
    }

    public static void main(String[] args) {
        System.out.println(Md5Util.encryption(UUID.randomUUID().toString(), 16, true));
    }
}

