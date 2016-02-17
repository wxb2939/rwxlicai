package com.rwxlicai.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuiou.pay.FyPay;
import com.fuiou.pay.activity.RequestOrder;
import com.fuiou.pay.util.AppConfig;
import com.google.gson.Gson;
import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.entity.Fuyou;
import com.rwxlicai.view.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/2/1.
 */
public class Fuyou_RechargeAcitity extends RwxActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.mchntCd)
    TextView mchntCd;
    @InjectView(R.id.money)
    TextView money;
    @InjectView(R.id.ono)
    TextView ono;
    @InjectView(R.id.orderid)
    TextView orderid;
    @InjectView(R.id.sfz)
    TextView sfz;
    @InjectView(R.id.fuyou_name)
    TextView name;
    @InjectView(R.id.loginin)
    Button next;


    private Fuyou fuyou;
    private String crash;
    public RequestOrder requestOrder;
    private String mURL = "www.baidu.com";
    private String mMchnt_Key = "5old71wihg2tqjug9kkpxnhx9hiujoqj";
    private String murl;


    private String sMchntcd;
    private String bookJson;

    @Override
    protected void initView() {
        setContentView(R.layout.fuyou_recharge_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("充值").setLeftImage(R.mipmap.top_view_back);
        FyPay.init(Fuyou_RechargeAcitity.this);

        requestOrder = new RequestOrder(this);
        bookJson=getIntent().getStringExtra("fuyou");
        crash = getIntent().getStringExtra("money");
        fuyou=new Gson().fromJson(bookJson, Fuyou.class);
    }



    @Override
    protected void onStart() {
        Log.i("zls",
                "应答码："
                        + AppConfig.getData(Fuyou_RechargeAcitity.this,
                        AppConfig.RSP_CODE));
        Log.i("zls",
                "描述："
                        + AppConfig.getData(Fuyou_RechargeAcitity.this,
                        AppConfig.RSP_DESC));
        /**
         * 请求发送成功的返回数据 发起支付
         */
        Log.i("zls",
                "发送成功请求的返回数据："
                        + AppConfig.getData(Fuyou_RechargeAcitity.this,
                        AppConfig.RSP_SDK_DATA));
        reset();
        super.onStart();
    }

    /**
     * 清除数据
     */
    private void reset() {
        AppConfig.setData(Fuyou_RechargeAcitity.this, AppConfig.RSP_CODE, "");
        AppConfig.setData(Fuyou_RechargeAcitity.this, AppConfig.RSP_DESC, "");
        AppConfig.setData(Fuyou_RechargeAcitity.this, AppConfig.RSP_SDK_DATA, "");
    }

    @OnClick({R.id.titlebar_iv_left,R.id.loginin})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.loginin:
//                String Sing = MD5UtilString.MD5Encode("02" + "|" + "2.0" + "|"
//                        + mchntCd.getText().toString() + "|"
//                        + orderid.getText().toString() + "|"
//                        + userId.getText().toString() + "|"
//                        + money.getText().toString() + "|"
//                        + ono.getText().toString() + "|" + mURL + "|"
//                        + name.getText().toString() + "|"
//                        + sfz.getText().toString() + "|"
//                        + "0" + "|" + mMchnt_Key);
//
//                AppConfig.setData(Fuyou_RechargeAcitity.this, AppConfig.MCHNT_CD, mchntCd
//                        .getText().toString());
//                AppConfig.setData(Fuyou_RechargeAcitity.this, AppConfig.MCHNT_AMT, money
//                        .getText().toString());
//                AppConfig.setData(Fuyou_RechargeAcitity.this, AppConfig.MCHNT_BACK_URL,
//                        mURL);
//                AppConfig.setData(Fuyou_RechargeAcitity.this,
//                        AppConfig.MCHNT_BANK_NUMBER, ono.getText().toString());
//                AppConfig.setData(Fuyou_RechargeAcitity.this, AppConfig.MCHNT_ORDER_ID,
//                        orderid.getText().toString());
//                AppConfig.setData(Fuyou_RechargeAcitity.this,
//                        AppConfig.MCHNT_USER_IDCARD_TYPE, "0");
//                AppConfig.setData(Fuyou_RechargeAcitity.this, AppConfig.MCHNT_USER_ID,
//                        userId.getText().toString());
//                AppConfig.setData(Fuyou_RechargeAcitity.this, AppConfig.MCHNT_USER_IDNU,
//                        sfz.getText().toString());
//                AppConfig.setData(Fuyou_RechargeAcitity.this, AppConfig.MCHNT_USER_NAME,
//                        name.getText().toString());
//                AppConfig.setData(Fuyou_RechargeAcitity.this, AppConfig.MCHNT_SING_KEY,
//                        Sing);
//                requestOrder.Request();
//                break;

                web();

//                Intent question = new Intent();
//                question.setAction("android.intent.action.VIEW");
//                Uri muri = Uri.parse(murl);
//                question.setData(muri);
//                startActivity(question);

                Intent mIntent = new Intent(Fuyou_RechargeAcitity.this, WebviewAty.class);
                mIntent.putExtra("fuyou",fuyou);
                startActivity(mIntent);
                finish();
//                mIntent.putExtra("money",crash_recharge.getText().toString());

                break;
            default:
                break;
        }
    }


    private void web(){
//        http://www-1.fuiou.com:18670/mobile_pay/timbnew/timb01.pay?mchntCd=0002900F0280210&orderid=E10320160201095339245957&ono=6210812430013169228
// &backurl=http://www.rwxlicai.com/web/recvFromFuYou.do
// &reurl=http://www.rwxlicai.com/web/user/recharge.do
// &homeurl=http://www.rwxlicai.com/web/user/recharge.do
// &name=王荣&sfz=340823199209201565&md5=0302a39295ee4a6deb9ff68d805d3f82

//        {"code":"0","message":"在线充值申请成功",
//                "result":
//                {"ono":"6217001210019000437",
//                "gatewayUrl":"http://116.239.4.194:18670/mobile_pay/timbnew/timb01.pay",
//                        "homeurl":"http://www.rwxlicai.com/web/user/recharge.do",
//                        "name":"王学兵","md5":"3594d164a6950982a9512f7f852da859",
//                        "orderid":"E10220160201093146795860",
//                        "sfz":"622421198811092939",
//                        "backurl":"http://www.rwxlicai.com/web/recvFromFuYou.do",
//                        "reurl":"http://www.rwxlicai.com/web/user/recharge.do",
//                        "mchntCd":"0002900F0280210”}}


//        http://www-1.fuiou.com:18670/mobile_pay/timbnew/timb01.pay?
// mchntCd=***&orderid=***&ono=***&b ackurl=***&reurl=***&homeurl=***&name=***&sfz=***&md5=***

//        murl = "http://116.239.4.194:18670/mobile_pay/timbnew/timb01.pay?" +
//                sMchntcd +
//                "orderid=E10220160201093146795860&" +
//                "ono=6217001210019000437"+
//                "&backurl=http://www.rwxlicai.com/web/recvFromFuYou.do"+
//                "&reurl=http://www.rwxlicai.com/web/user/recharge.do"+
//                "&homeurl=http://www.rwxlicai.com/web/user/recharge.do"+
//                "name=王学兵&sfz=622421198811092939&md5=0302a39295ee4a6deb9ff68d805d3f82";

        murl = strurl+sMchntcd+strOrderid+strono+strbackurl+strreurl+strhome+strname+strsfz+strmd5;


        }

    @Override
    protected void initData() {

        mchntCd.setText(fuyou.getMchntCd());
        money.setText(crash);
        ono.setText(fuyou.getOno());
        orderid.setText(fuyou.getOrderid());
        sfz.setText(fuyou.getSfz());
        name.setText(fuyou.getName());

//        strurl = fuyou.getGatewayUrl()+"?";
        strurl = "http://www-1.fuiou.com:18670/mobile_pay/timbnew/timb01.pay?";

//        sMchntcd = "mchntCd= "+fuyou.getMchntCd();
        sMchntcd = "mchntCd=0002900F0096235";

//        strOrderid = "&orderid="+fuyou.getOrderid();
        strOrderid = "&orderid=16020116335400038975";
        strono = "&ono="+fuyou.getOno();
        strbackurl= "&backurl="+fuyou.getBackurl();
        strreurl = "&reurl="+fuyou.getReurl();
        strhome = "&homeurl="+fuyou.getHomeurl();
        strname = "&name="+fuyou.getName();
        strsfz = "&sfz="+fuyou.getSfz();
//        strmd5 = "&md5="+fuyou.getMd5();
        strmd5 = "&md5=2b256da8727f5a79f99d0600d9415601";


//        strmd5 = mchntCd + "|" + orderid
//                +"|"+name + "|" +
//                ono+"|"+sfz+"|" +mchnt_key;


    }

    private String strurl;
    private String strOrderid;
    private String strono;
    private String strbackurl;
    private String strhome;
    private String strreurl;
    private String strname;
    private String strsfz;
    private String strmd5;
//    private String mchnt_key = "5old71wihg2tqjug9kkpxnhx9hiujoqj";
    private String mchnt_key = "5old71wihg2tqjug9kkpxnhx9hiujoqj";

}
