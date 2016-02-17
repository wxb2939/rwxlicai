package com.xem.mzbphoneapp.utils;

import java.util.Map;

/**
 * Created by xuebing on 15/7/20.
 */
public class TestDecodeUrl {
    /**用于测试CRequest类
     * @param args
     */
    public static void main(String[] args) {
        // 请求url
        String str = "index.jsp?Action=del&id=123&sort=";
        //url页面路径
        System.out.println(DecodeUrl.UrlPage(str));
        //url参数键值对
        String strRequestKeyAndValues="";
        Map mapRequest = DecodeUrl.URLRequest(str);
        for(Object strRequestKey: mapRequest.keySet()) {
            String strRequestValue= (String) mapRequest.get(strRequestKey);
            strRequestKeyAndValues+="key:"+strRequestKey+",Value:"+strRequestValue+";";
        }
        System.out.println(strRequestKeyAndValues);
        //获取无效键时，输出null
        System.out.println(mapRequest.get("page"));
    }
}
