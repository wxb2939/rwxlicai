package com.rwxlicai.utils;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by xuebing on 16/1/12.
 */
public class DESUtil {

    private static Key key;

//    密钥：6Ta4OaHZdpA=
//    明文：123456
//    密文：im8+1pvUtgs=


    public DESUtil(String str) {
        setKey(str);// 生成密匙
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


    public static String fromBytesToHex(byte[] resultBytes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < resultBytes.length; i++) {
            if (Integer.toHexString(0xFF & resultBytes[i]).length() == 1) {
                builder.append("0").append(
                        Integer.toHexString(0xFF & resultBytes[i]));
            } else {
                builder.append(Integer.toHexString(0xFF & resultBytes[i]));
            }
        }
        return builder.toString();
    }


    /**
     * 生成秘钥
     */

    public static byte[] initkey() throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("DES");
        keygen.init(56);
        SecretKey secretKey = keygen.generateKey();
        return secretKey.getEncoded();
    }


    /**
     * DES 加密
     */

    public static byte[] encrypt(byte[] data) throws Exception {
//        SecretKey secretKey = new SecretKeySpec(key, "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] cipherBytes = cipher.doFinal(data);
        return cipherBytes;
    }


    /**
     * DES 解密
     */

    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] plainBytes = cipher.doFinal(data);
        return plainBytes;
    }

    DesEncrypt des = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
}
