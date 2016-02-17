package com.xem.mzbcustomerapp.activity;

import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.other.zxing.encoding.EncodingHandler;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/26.
 */
public class B1_MyQrActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.qr_code_image)
    ImageView codeImage;
    @InjectView(R.id.qr_img)
    ImageView qr_img;
    @InjectView(R.id.qr_name)
    TextView qr_name;

    @Override
    protected void initView() {
        setContentView(R.layout.b1_myqr_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("我的二维码").setLeftImage(R.mipmap.top_view_back);
    }

    @Override
    protected void initData() {
        String uri = Config.getCachedPortrait(B1_MyQrActivity.this)+"@!l1";
        imageLoader.displayImage(uri,qr_img);
        qr_name.setText(Config.getCachedName(B1_MyQrActivity.this));

        JSONObject json = new JSONObject();
        try {
            json.put("UserId", Config.getCachedUid(B1_MyQrActivity.this));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String business = String.format("%s/%s/%s","美之伴", 2, json.toString());
        String string = Base64.encodeToString(business.getBytes(), Base64.DEFAULT);
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
            showToast("Text can not be empty");
        }
    }

    @OnClick({R.id.titlebar_iv_left,R.id.titlebar_tv_right})
    public void clickAction(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }
}
