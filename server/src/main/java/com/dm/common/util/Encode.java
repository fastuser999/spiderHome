package com.dm.common.util;



import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public class Encode {


    /**
     * 生成盐值
     * @return
     */
    public String genSalt(){
        return UUID.randomUUID().toString();
    }
    /**
     * 密码加密功能（把盐值和密码合成，输出MD5）
     * @param password
     * @return
     */
    public String encoding(String password,String salt) {

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            String pwd=null;
            pwd=salt+password;
            byte[] pwdBytes = pwd.getBytes("UTF-8");
            md5.update(pwdBytes);
            byte[] resultArray = md5.digest();
            BigInteger bigInt = new BigInteger(1, resultArray);//1是什么意思？
            String resultStr = bigInt.toString(16);
            return resultStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "null";
    }
}
