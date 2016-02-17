package com.xem.mzbemployeeapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xuebing on 15/9/16.
 */
public class CompareTime {


//    String s1="2008-01-25 09:12:09";
//    String s2="2008-01-29 09:12:11";


    public static boolean compare(String edate){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = sdf.format(d);

        java.text.DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        java.util.Calendar c1=java.util.Calendar.getInstance();
        java.util.Calendar c2=java.util.Calendar.getInstance();

        try
        {
            c1.setTime(df.parse(edate));
            c2.setTime(df.parse(dateNow));

        }catch(java.text.ParseException e){
            System.err.println("格式不正确");
        }
        int result=c1.compareTo(c2);
        if(result==0)
//            System.out.println("c1相等c2");
            return false;
        else if(result<0)
            return true;
//            System.out.println("c1小于c2");
        else
            return false;
//            System.out.println("c1大于c2");
    }
}
