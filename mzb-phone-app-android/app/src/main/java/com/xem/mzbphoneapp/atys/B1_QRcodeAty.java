package com.xem.mzbphoneapp.atys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.zxing.encoding.EncodingHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/7/3.
 */
public class B1_QRcodeAty extends MzbActivity{

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.qr_code_image)
    ImageView codeImage;
    @InjectView(R.id.qr_img)
    ImageView qr_img;
    @InjectView(R.id.qr_name)
    TextView qr_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("我的二维码").setLeftImage(R.mipmap.top_view_back);
        init();
        JSONObject json = new JSONObject();
        try {
            json.put("UserId", Config.getCachedUid(B1_QRcodeAty.this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String business = String.format("%s/%s/%s","美之伴", 2, json.toString());
        String string = Base64.encodeToString(business.getBytes(),Base64.DEFAULT);
        String contentString = String.format("%s%s", "http://www.xirmei.com/Download/Index?m=",string);

        if (!contentString.equals("")) {
            // 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
            Bitmap qrCodeBitmap = null;
            try {
                qrCodeBitmap = EncodingHandler.createQRCode(
                        contentString, 350);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            codeImage.setImageBitmap(qrCodeBitmap);
        } else {
            Toast.makeText(B1_QRcodeAty.this,
                    "Text can not be empty", Toast.LENGTH_SHORT)
                    .show();
        }

    }


    private void init(){
        imageLoader.displayImage(Config.getCachedPortrait(B1_QRcodeAty.this),qr_img);
        qr_name.setText(Config.getCachedName(B1_QRcodeAty.this));
    }


    @OnClick({R.id.titlebar_iv_left})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }
}
