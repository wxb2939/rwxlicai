package com.xem.mzbphoneapp.utils;

import android.content.Context;

import com.xem.mzbphoneapp.R;
import com.xem.sharesdk.onekeyshare.OnekeyShare;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by xuebing on 15/7/25.
 */
public class ShowShare {
    Context context;

    public ShowShare(Context context) {
        this.context = context;
    }

    public void showShare(){
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
        // oks.setNotification(R.drawable.ic_launcher,
        // getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("美之伴");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.xirmei.com/Coupon/6.html?device=1");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("智能美业，创享互联");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
        oks.setImageUrl("http://a.hiphotos.baidu.com/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=6e78d2303f87e950561afb3e71513826/f9dcd100baa1cd11aaf3afd3bc12c8fcc2ce2df8.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.xirmei.com/Coupon/6.html?device=1");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("智能美业，创享互联");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.xirmei.com/");
        // 启动分享GUI
        oks.show(context);
    }

    public void StoreShowshare(String title,String content,String logo,String url){
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


    public void ConponShowshare(String title,String content,String logo,String url){
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
