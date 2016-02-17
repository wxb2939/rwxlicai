package com.xem.mzbcustomerapp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/11/8.
 */
public class A0_CheckSexActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.titlebar_tv_right)
    TextView titlebar_tv_right;
    @InjectView(R.id.boy)
    ImageView boy;
    @InjectView(R.id.girl)
    ImageView girl;
    @InjectView(R.id.ll_boy)
    View ll_boy;
    @InjectView(R.id.ll_girl)
    View ll_girl;


    private String strSex;
    private int flag = -1;
    private int resultCode = -110;

    @Override
    protected void initView() {
        setContentView(R.layout.a0_checksex_activity);
        new TitleBuilder(this).setTitleText("选择性别").setLeftImage(R.mipmap.top_view_back).setRightText("保存");
        ButterKnife.inject(this);
        strSex = getIntent().getStringExtra("info");
        if (strSex == null){
            girl.setVisibility(View.INVISIBLE);
            boy.setVisibility(View.INVISIBLE);
        }
        else if (strSex.equals("F")){
            flag = 0;
            girl.setVisibility(View.VISIBLE);
        }else {
            flag = 1;
            boy.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initData() {

    }

    Intent intents;

    @OnClick({R.id.titlebar_iv_left,R.id.ll_boy,R.id.ll_girl,R.id.titlebar_tv_right})
    public void clickAction(View view){
        intents =new Intent();
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                Intent intent = new Intent();
                intent.putExtra("extra", strSex);
                setResult(resultCode, intent);
                finish();
                break;
            case R.id.ll_boy:
                boy.setVisibility(View.VISIBLE);
                girl.setVisibility(View.INVISIBLE);
                strSex = "M";
                flag = 1;
                break;
            case R.id.ll_girl:
                girl.setVisibility(View.VISIBLE);
                boy.setVisibility(View.INVISIBLE);
                strSex = "F";
                flag = 0;
                break;
            case R.id.titlebar_tv_right:
                if (flag == 0){
                    commitModify("F");
                    setResult(-110,intents.putExtra("extra","女"));
//                    showToast("女");
                }else if (flag == 1){
                    commitModify("M");
                    setResult(-110,intents.putExtra("extra","男"));
//                    showToast("男");
                }
                else{
                    showToast("请选择性别后进行保存");
                }
                break;
            default:
                break;
        }
    }


    private void commitModify(final String sex) {
        showToast("请求调用");
        RequestParams params1 = new RequestParams();
        params1.put("type", "3");
        params1.put("sex", sex);
        params1.put("uid", Config.getCachedUid(A0_CheckSexActivity.this));
        MzbHttpClient.ClientTokenPost(A0_CheckSexActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.minfo, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        showToast("修改成功");
                        setResult(-110, intents.putExtra("extra", sex));
                        finish();
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

}
