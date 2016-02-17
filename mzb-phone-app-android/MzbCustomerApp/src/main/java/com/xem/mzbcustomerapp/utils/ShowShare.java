package com.xem.mzbcustomerapp.utils;

import android.content.Context;
import com.xem.mzbcustomerapp.R;

import java.util.HashMap;
import java.util.Queue;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qq.QQWebShareAdapter;

/**
 * Created by xuebing on 15/11/8.
 */
public class ShowShare {
    Context context;

    public ShowShare(Context context) {
        this.context = context;
    }

    public void TestShare() {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("美之伴");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("美之伴");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(context);
    }

    public void StoreShowshare(String title, String content, String logo, String url) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setTitle(title);
        oks.setTitleUrl(url);
        oks.setText(content);
        oks.setImageUrl(logo);
        oks.setUrl(url);
        oks.setComment("智能美业，创享互联");
        oks.setSite(title);
        oks.setSiteUrl("http://www.xirmei.com/");
        oks.show(context);
    }


    public void ConponShowshare(String title, String content, String logo, String url) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setTitle(title);
        oks.setTitleUrl(url);
        oks.setText(content);
        oks.setImageUrl(logo);
        oks.setUrl(url);
        oks.setComment(content);
        oks.setSite(title);
        oks.setSiteUrl("http://www.xirmei.com/");
        oks.show(context);
    }
}