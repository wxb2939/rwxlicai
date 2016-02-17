package com.xem.mzbphoneapp.atys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.fragment.E0_ProfileFragment;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.GetPushagrs;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/6/25.
 */
public class E1_EmployeeMoreAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.unbind)
    View unBind;
    @InjectView(R.id.changpwd)
    View changPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_more_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("更多").setLeftImage(R.mipmap.top_view_back);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.getInstance().clickEffect(unBind);
        Utility.getInstance().clickEffect(changPwd);
    }

    @OnClick({R.id.titlebar_iv_left, R.id.unbind, R.id.changpwd})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                intent2Aty(E1_EmployeeInfoAty.class);
                finish();
                break;
            case R.id.unbind:
                UnBindEmployee(Config.getCachedUid(E1_EmployeeMoreAty.this));
                break;
            case R.id.changpwd:
                intent2Aty(E1_ModifyPwdEmployeeAty.class);
                break;
            default:
                break;
        }
    }


    public void UnBindEmployee(String uid) {
        RequestParams params1 = new RequestParams();
        params1.put("uid", uid);

        final MzbProgressDialog pd = new MzbProgressDialog(E1_EmployeeMoreAty.this, "请稍后....");
        pd.show();

        RequestUtils.ClientTokenPost(E1_EmployeeMoreAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_UNBIND, params1, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        pd.dismiss();
                        showToast("解绑成功");
                        Config.cachedIsemploye(E1_EmployeeMoreAty.this, false);
                        Config.cachedIsmember(E1_EmployeeMoreAty.this, false);
                        Config.cachedBrandCodes(E1_EmployeeMoreAty.this, null);
                        Config.cachedBrandRights(E1_EmployeeMoreAty.this, null);
                        intent2Aty(E0_EmployeeBindAty.class);
                        new GetPushagrs(E1_EmployeeMoreAty.this).refreshPushagrs();
                        finish();

                    } else {
                        pd.dismiss();
                        showToast("解绑失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                pd.dismiss();
                showToast("请求失败");
            }
        });
    }

}
