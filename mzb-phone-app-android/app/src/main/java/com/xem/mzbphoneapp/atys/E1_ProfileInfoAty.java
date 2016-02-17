package com.xem.mzbphoneapp.atys;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.PInfoData;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/6/8.
 */
public class E1_ProfileInfoAty extends MzbActivity {

    @InjectView(R.id.cover_user_photo)
    ImageView cover_user_photo;
    @InjectView(R.id.all_info)
    View allInfo;
    @InjectView(R.id.llBtn)
    View llBtn;
    @InjectView(R.id.user_name)
    TextView etUserName;
    @InjectView(R.id.user_phone)
    TextView etUserPhone;
    @InjectView(R.id.user_info)
    TextView etUserInfo;
    @InjectView(R.id.im_info)
    ImageView imInfo;
    @InjectView(R.id.titlebar_iv_left)
    ImageView ivLeft;
    @InjectView(R.id.titlebar_tv_right)
    TextView tvRight;
    @InjectView(R.id.llpinfo)
    View pInfo;
    @InjectView(R.id.llinfo)
    View llInfo;

    private static int IMG_REQUEST_CODE = 1;
    private String uri;
    private String uid;
    private String token;
    private String strName;
    private String name = "";
    private String strExtra;
    private String desc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_info_aty);
        ButterKnife.inject(this);

        new TitleBuilder(E1_ProfileInfoAty.this).setTitleText("个人信息")
                .setLeftImage(R.mipmap.top_view_back)
                .setRightText("保存");

        uid = getIntent().getStringExtra(Config.KEY_UID);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);
        DoLogin(uid);
    }

    @Override
    protected void onResume() {
        super.onResume();
        uri = Config.getCachedPortrait(E1_ProfileInfoAty.this);
        imageLoader.displayImage(uri, cover_user_photo);
    }

    @OnClick({R.id.llBtn, R.id.titlebar_tv_right, R.id.im_info, R.id.titlebar_iv_left,
            R.id.all_info, R.id.llpinfo, R.id.llinfo})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                commitModify();
                finish();
                break;
            case R.id.llpinfo:
                Intent orderExtra = new Intent(E1_ProfileInfoAty.this, AddUsernameAty.class);
                orderExtra.putExtra("info", name);
                startActivityForResult(orderExtra, 0);
                break;
            case R.id.llinfo:
                Intent linfo = new Intent(E1_ProfileInfoAty.this, AddUserinfpAty.class);
                linfo.putExtra("info", desc);
                startActivityForResult(linfo, 0);

            case R.id.all_info:
                InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.llBtn:
                Intent intent = new Intent(E1_ProfileInfoAty.this, A0_HeadPortraitAty.class);
            intent.putExtra("URI", uri);
            startActivityForResult(intent, IMG_REQUEST_CODE);
            break;

            case R.id.im_info:
                /*final AlertDialog dialog = new AlertDialog.Builder(this).create();
                ImageView imageView = getView();
                dialog.setView(imageView);
                dialog.show();

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });*/
                startActivity(new Intent(E1_ProfileInfoAty.this, B1_QRcodeAty.class));
                break;

            default:
                break;
        }

    }

    private void commitModify() {
        RequestParams params1 = new RequestParams();
        params1.put("uid", Config.getCachedUid(E1_ProfileInfoAty.this));
        params1.put("name", etUserName.getText().toString());
        params1.put("desc", etUserInfo.getText().toString());
        params1.put("type", "0");

        RequestUtils.ClientTokenPost(E1_ProfileInfoAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_MINFO, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        showToast("修改成功");
                        Config.cachedName(E1_ProfileInfoAty.this, etUserName.getText().toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("请求失败");
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        final String uril = "file:///" + Environment.getExternalStorageDirectory() + "/avateravater.png";
        String uril = "file:///" + Environment.getExternalStorageDirectory() + "/temp_photo.jpg";
        imageLoader.displayImage(uril, cover_user_photo);
        switch (resultCode) {
            case -105:
                strName = data.getStringExtra("extra");
                etUserName.setText(strName);
                break;
            case -102:
                strExtra = data.getStringExtra("extra");
                etUserInfo.setText(strExtra);
                break;
            default:
                break;
        }
        return;
    }


    public void DoLogin(String uid) {
        RequestParams params1 = new RequestParams();
        params1.put("uid", uid);


        RequestUtils.ClientTokenPost(E1_ProfileInfoAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_INFO, params1, new NetCallBack(this) {

            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {

                        PInfoData pInfoData = gson.fromJson(obj.getJSONObject("data").toString(), PInfoData.class);
                        name = pInfoData.getName();
                        etUserName.setText(name);
                        etUserPhone.setText(pInfoData.getMobile());
                        desc = pInfoData.getDesc();
                        if (desc != null) {
                            etUserInfo.setText(desc);
                        } else {
                            etUserInfo.setText("");
                        }

                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("请求失败");
            }
        });
    }


    private ImageView getView() {
        ImageView imgView = new ImageView(this);
//        imgView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        InputStream is = null;
        try {
            is = this.getAssets().open("pic.png");

        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable drawable = BitmapDrawable.createFromStream(is, null);

        imgView.setImageDrawable(drawable);

        return imgView;
    }

}
