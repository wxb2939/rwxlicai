package com.xem.mzbphoneapp.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by xuebing on 15/7/20.
 */
public class Byte2Base64 {


    /**
     * @param bytes
     * @return
     */
    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    private  void testUTF8()
    {
        String str = "美之伴";
        try {
            byte[] en = android.util.Base64.encode(str.getBytes("UTF-8"), android.util.Base64.DEFAULT);

            System.out.println(en);

            byte[] de = android.util.Base64.decode(en, android.util.Base64.DEFAULT);

            String deStr = new String(de,"UTF-8");
            System.out.println(deStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
