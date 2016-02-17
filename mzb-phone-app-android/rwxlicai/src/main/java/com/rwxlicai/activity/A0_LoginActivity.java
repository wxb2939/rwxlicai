package com.rwxlicai.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.rwxlicai.R;
import com.rwxlicai.base.RwxActivity;
import com.rwxlicai.model.ALL_BANK;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.utils.Config;
import com.rwxlicai.utils.DesEncrypt;
import com.rwxlicai.view.TitleBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/13.
 */
public class A0_LoginActivity extends RwxActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.phonenum)
    EditText phonenum;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.loginin)
    Button loginin;
    @InjectView(R.id.forget_pwd)
    TextView forget_pwd;
    @InjectView(R.id.regist)
    TextView regist;


    @Override
    protected void initView() {
        setContentView(R.layout.a0_loginactivity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("登录").setLeftImage(R.mipmap.top_view_back).setRightText("注册");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left, R.id.regist, R.id.forget_pwd, R.id.loginin, R.id.titlebar_tv_right})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.regist:
            case R.id.titlebar_tv_right:
                intent2Aty(A0_RegisterActivity.class);
                finish();
                break;
            case R.id.forget_pwd:
                intent2Aty(A0_FindPwdActivity.class);
                finish();
                break;
            case R.id.loginin:
                if (TextUtils.isEmpty(phonenum.getText().toString())) {
                    showToast("请输入电话号码！");
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    showToast("请输入密码！");
                    return;
                }
                hideKeyboard(view);
                Login(phonenum.getText().toString(), password.getText().toString());
                break;
            default:
                break;
        }
    }


    public void Login(final String mobile, final String password) {
        RequestParams params = new RequestParams();
        params.put("userAccount", mobile);
        try {
            params.put("userPassword", DesEncrypt.DesUtil(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        RwxHttpClient.ClientPost(A0_LoginActivity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.login, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
//                        showToast(obj.getString("message"));
                        JSONObject data = obj.getJSONObject("result");
                        Config.cachedJSESSIONID(A0_LoginActivity.this, data.getString("JSESSIONID"));
                        Config.cachedToken(A0_LoginActivity.this, data.getString("TOKEN"));
                        Config.cachedLogin(A0_LoginActivity.this, true);
                        Config.cachedPhone(A0_LoginActivity.this,phonenum.getText().toString());
                        getSystemConfig();
                        userCenter();
                        finish();
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(context, "请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void userCenter(){
        RwxHttpClient.ClientTokenPost(A0_LoginActivity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.userCenter, null, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        JSONObject data = obj.getJSONObject("result").getJSONObject("user");
                        Config.cachedRealNameState(A0_LoginActivity.this,data.getString("realnameStatus"));
                        Config.cachedBankState(A0_LoginActivity.this,data.getString("bankStatus"));
                    } else {
                        Toast.makeText(A0_LoginActivity.this, obj.getString("message"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(A0_LoginActivity.this, arg0.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }





    private void getSystemConfig(){
        RwxHttpClient.ClientTokenPost(A0_LoginActivity.this, RwxUrlFactory.BASE_URL + RwxUrlFactory.getSystemConfig, null, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
//                        showToast(obj.getString("message"));
                        JSONObject data = obj.getJSONObject("result");
                        init(data);
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(context, "请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void init(JSONObject data) throws Exception{

//        USER_CARD_TYPE
//        JSONObject data1 = data.getJSONObject("USER_CARD_TYPE");
//        USER_CARD_TYPE user_card_type = new USER_CARD_TYPE();
//        Iterator it = data1.keys();
//        while (it.hasNext()) {
//            String key = (String) it.next();
//            String value = data1.getString(key);
//            new Delete().from(USER_CARD_TYPE.class).where("key = ?", key).execute();
//            user_card_type.key = key;
//            user_card_type.value = value;
//            user_card_type.save();
//           int i = new Select().from(USER_CARD_TYPE.class).where("key = ?",key).execute().size();
//            showToast(user_card_type.value + "==="+i);
//        }


        //TRADE_CODE
//        JSONObject data2 = data.getJSONObject("TRADE_CODE");
//        TRADE_CODE trade_code = new TRADE_CODE();
//        Iterator it2 = data2.keys();
//        while (it2.hasNext()) {
//            String key2 = (String) it2.next();
//            String value2 = data2.getString(key2);
//            new Delete().from(TRADE_CODE.class).where("key = ?",key2).execute();
//            trade_code.key = key2;
//            trade_code.value = value2;
//            trade_code.save();
//            int i = new Select().from(TRADE_CODE.class).where("key = ?",trade_code.key).execute().size();
//            showToast(trade_code.key+ "=== "+i);
//        }


//        JSONObject data3 = data.getJSONObject("USER_EMAIL_STATUS");
//        USER_EMAIL_STATUS user_email_status = new USER_EMAIL_STATUS();
//        Iterator it3 = data3.keys();
//        while (it3.hasNext()) {
//            String key3 = (String) it3.next();
//            String value3 = data3.getString(key3);
//            new Delete().from(USER_EMAIL_STATUS.class).where("key = ?",key3).execute();
//            user_email_status.key = key3;
//            user_email_status.value = value3;
//            user_email_status.save();
//            int i = new Select().from(USER_EMAIL_STATUS.class).where("key = ?",user_email_status.key).execute().size();
//            showToast(user_email_status.key+ "=== "+i);
//        }



//        JSONArray data4 = data.getJSONArray("INDEX_IMG");
//        INDEX_IMG index_img = new INDEX_IMG();
//        for (int i = 0; i < data4.length(); i++) {
//            JSONObject object = data4.getJSONObject(i);
//            index_img.content_txt = object.getString("cid");
//            index_img.attach_path = object.getString("attach_path");
//            index_img.external_link_title = object.getString("external_link_title");
//            index_img.content_txt = object.getString("content_txt");
//            index_img.channel_ids = object.getString("channel_ids");
//            index_img.save();
//
//            int m = new Select().from(INDEX_IMG.class).where("cid = ?",index_img.cid).execute().size();
//            showToast(index_img.cid + "=== " + m);
//        }



        JSONArray data4 = data.getJSONArray("ALL_BANK");

        for (int i = 0; i < data4.length(); i++) {
            JSONObject object = data4.getJSONObject(i);
            ALL_BANK all_bank = new ALL_BANK();
            if (object.getString("bid")!= null){
                String bid = object.getString("bid");
                new Delete().from(ALL_BANK.class).where("bid = ?",bid).execute();
            }
            all_bank.bid = object.getString("bid");
            all_bank.bankName = object.getString("bankName");
            all_bank.bankCode = object.getString("bankCode");
            all_bank.save();
//            int m = new Select().from(ALL_BANK.class).where("bankName = ?",object.getString("bankName")).execute().size();
//            showToast(all_bank.bankName + "=== " + m);
        }
    }


}
