package com.rwxlicai.utils;

import java.security.Key;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by xuebing on 16/1/12.
 */
public class DesEncrypt {

    Key key;
    private static final Map<String,String> replaceStr = new HashMap<String,String>();
    public DesEncrypt(String str) {
        setKey(str);// 生成密匙
    }


    public static String DesUtil(String pwd) throws Exception {
        DesEncrypt des = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
        String str2 = des.encrypt(pwd);
        System.out.println("密文:" + str2);
        return str2;
    }

    /**
     * 根据参数生成KEY
     */
    public void setKey(String strKey) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            this.key = keyFactory.generateSecret(new DESKeySpec(strKey
                    .getBytes("UTF8")));
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        }
    }


    /**
     * 加密String明文输入,String密文输出
     */
    public String encrypt(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        BASE64Encoder base64en = new BASE64Encoder();
        try {
            byteMing = strMing.getBytes(Constants.UTF8);
            byteMi = this.getEncCode(byteMing);

            strMi = base64en.encode(byteMi);
        } catch (Exception e) {

//            log.error("encrypt error:"+strMing,e);
            return null;
        } finally {
            base64en = null;
            byteMing = null;
            byteMi = null;
        }
        return replaceKeyByValue(strMi);
    }

    /**
     * 解密 以String密文输入,String明文输出
     *
     * @param strMi
     * @return
     */
    public String decrypt(String strMi) {
        strMi = replaceValueByKey(strMi);
        BASE64Decoder base64De = new BASE64Decoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64De.decodeBuffer(strMi);
            byteMing = this.getDesCode(byteMi);
            strMing = new String(byteMing, Constants.UTF8);
        } catch (Exception e) {

//            log.error("decrypt error:"+strMi,e);
            return null;
        } finally {
            base64De = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }


    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     *
     * @param byteD
     * @return
     */
    private byte[] getDesCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key,
                    SecureRandom.getInstance("SHA1PRNG"));
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }


    /**
     * 加密以byte[]明文输入,byte[]密文输出
     *
     * @param byteS
     * @return
     */
    private byte[] getEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key,SecureRandom.getInstance("SHA1PRNG"));
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }


    /**
     * 替换密文中的特殊字符
     * @param str
     * @return
     */
    public String replaceKeyByValue(String str){
        Iterator<Map.Entry<String, String>> entrys = replaceStr.entrySet().iterator();
        while(entrys.hasNext()){
            Map.Entry<String, String> entry = entrys.next();
            str = str.replaceAll(entry.getKey(), entry.getValue());
        }
        return str;
    }
    /**
     * 还原密文中的特殊字符
     * @param str
     * @return
     */
    public String replaceValueByKey(String str){
        Iterator<Map.Entry<String, String>> entrys = replaceStr.entrySet().iterator();
        while(entrys.hasNext()){
            Map.Entry<String, String> entry = entrys.next();
            str = str.replaceAll(entry.getValue(), entry.getKey());
        }
        return str;
    }

}
