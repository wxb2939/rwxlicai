package com.rwxlicai.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.entity.Fuyou;

import java.io.UnsupportedEncodingException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by xuebing on 16/2/1.
 */
public class WebviewAty extends RwxActivity {
    @InjectView(R.id.webView)
    WebView myWebview;

    private String sMchntcd;
    private String strurl;
    private String strOrderid;
    private String strono;
    private String strbackurl;
    private String strhome;
    private String strreurl;
    private String strname;
    private String strsfz;
    private String strmd5;

    private Fuyou fuyou;

    private String postDate;
    @Override
    protected void initView() {
        setContentView(R.layout.web_activity);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        if (null != intent) {
            fuyou= (Fuyou) intent.getSerializableExtra("fuyou");
//            fuyou=new Gson().fromJson(bookJson, Fuyou.class);
        }
        //启用支持javascript
        WebSettings settings = myWebview.getSettings();
        settings.setJavaScriptEnabled(true);




        strurl = fuyou.getGatewayUrl();
        sMchntcd = "mchntCd="+fuyou.getMchntCd();
        strOrderid = "&orderid="+fuyou.getOrderid();
        strmd5 = "&md5="+fuyou.getMd5();

//        strurl = "http://www-1.fuiou.com:18670/mobile_pay/timbnew/timb01.pay";
//        sMchntcd = "mchntCd=0002900F0096235";
//        strOrderid = "&orderid=16020116335400038975";
        strono = "&ono="+fuyou.getOno();
        strbackurl= "&backurl="+fuyou.getBackurl();
        strreurl = "&reurl="+fuyou.getReurl();
//        strhome = "&homeurl="+fuyou.getHomeurl();
        strhome = "&homeurl=";
        strname = "&name="+fuyou.getName();
        strsfz = "&sfz="+fuyou.getSfz();
//        strmd5 = "&md5=768fea735ae827ea5f382841d2c042db";
        //需要访问的网址
//        String url = "http://www.cqjg.gov.cn/netcar/FindThree.aspx";
        //post访问需要提交的参数
//        String postDate = "txtName=zzz&QueryTypeLst=1&CertificateTxt=dsds";
        //由于webView.postUrl(url, postData)中 postData类型为byte[] ，
        //通过EncodingUtils.getBytes(data, charset)方法进行转换
//        myWebview.postUrl(url, EncodingUtils.getBytes(postDate, "BASE64"));

//        postDate = "txtName=zzz&QueryTypeLst=1&CertificateTxt=dsds";
        postDate = sMchntcd+strOrderid+strono+strbackurl+strreurl+strhome+strname+strsfz+strmd5;
//        myWebview.getSettings().setDefaultTextEncodingName("utf-8") ;
        try {
            byte[] midbytes=postDate.getBytes("UTF-8");
            myWebview.postUrl(strurl, midbytes);
            finish();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        myWebview.postUrl(url, EncodingUtils.getBytes(postDate, "BASE64"));
//        myWebview.postUrl(strurl, midbytes);

    }

    @Override
    protected void initData() {


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            intent2Aty(E0_InfoAty.class);
            finish();
            return true;
        }
        return true;
    }
}
