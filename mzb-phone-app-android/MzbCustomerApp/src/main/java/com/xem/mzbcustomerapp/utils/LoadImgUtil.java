package com.xem.mzbcustomerapp.utils;

/**
 * Created by xuebing on 15/10/28.
 */
public class LoadImgUtil {


//    图片尺寸样式
//    1.	l1: 50px_50px
//    2.	l2: 160px_160px
//    3.	l3: 300px_300px
//    4.	l4: 800px_800px

//    正式版
//    原图:http://imgcdn.meizhiban.com/201510/5d27.jpg
//    l1:  http://imgcdn.meizhiban.com/201510/5d27.jpg@!l1
//    l2:  http://imgcdn.meizhiban.com/201510/5d27.jpg@!l2
//    l3:  http://imgcdn.meizhiban.com/201510/5d27.jpg@!l3
//    l4:  http://imgcdn.meizhiban.com/201510/5d27.jpg@!l4


    public static String loadsmallImg(String str){
        return str+"@!l1";
    }

    public static String loadmiddleImg(String str){
        return str+"@!l2";
    }
    public static String loadbigImg(String str){
        return str+"@!l3";
    }

    public static String loadbiggerImg(String str){
        return str+"@!l4";
    }

}
