package com.rwxlicai.entity;

import java.io.Serializable;

/**
 * Created by xuebing on 16/1/26.
 */
public class ContentMessage implements Serializable {

//            "title":"xxx",//标题
//            "date":"xxx",//时间
//            "content":"xxx"//内容

    private String title;
    private String date;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
