package com.xem.mzbcustomerapp.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
public class A0_AddUserNameActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.addextra)
    EditText addExtra;
    @InjectView(R.id.titlebar_tv_right)
    TextView titlebar_tv_right;

    private int resultCode = -107;
    Intent intent;
    @Override
    protected void initView() {
        setContentView(R.layout.common_write_aty);
        new TitleBuilder(this).setTitleText("修改用户名").setLeftImage(R.mipmap.top_view_back).setRightText("保存");
        ButterKnife.inject(this);
        intent=new Intent();
    }

    @Override
    protected void initData() {
//        showToast(getIntent().getStringExtra("info"));
        intent.putExtra("extra",getIntent().getStringExtra("info"));
        addExtra.setText(getIntent().getStringExtra("info"));
        setResult(resultCode, intent);
    }

    @OnClick({R.id.titlebar_iv_left,R.id.titlebar_tv_right})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
//                Intent intent = new Intent();
//                intent.putExtra("extra", addExtra.getText().toString().trim());
//                setResult(resultCode, intent);
                finish();
                break;
            case R.id.titlebar_tv_right:
                commitModify();
                break;
            default:
                break;
        }
    }

    private void commitModify() {
        RequestParams params1 = new RequestParams();
        params1.put("type", "1");
        params1.put("uid", Config.getCachedUid(A0_AddUserNameActivity.this));
        params1.put("name", addExtra.getText().toString());
        Log.v("tag",addExtra.getText().toString());
        MzbHttpClient.ClientTokenPost(A0_AddUserNameActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.minfo, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                Log.v("tag",result);
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
//                        setResult(107,);
                        showToast("修改成功");
                        intent.putExtra("extra", addExtra.getText().toString());
                        setResult(resultCode, intent);
                        Config.cachedName(A0_AddUserNameActivity.this,addExtra.getText().toString());
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
