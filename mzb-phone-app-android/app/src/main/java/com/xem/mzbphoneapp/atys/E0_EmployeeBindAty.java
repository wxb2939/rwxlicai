package com.xem.mzbphoneapp.atys;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.EmployeData;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.GetPushagrs;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/6/24.
 */
public class E0_EmployeeBindAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.account_num)
    EditText accounNum;
    @InjectView(R.id.all_bind)
    View allBind;
    @InjectView(R.id.account_pwd)
    EditText accounPwd;
    @InjectView(R.id.brand_num)
    EditText brandNum;
    @InjectView(R.id.btnBind)
    Button btn_sure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_bind_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("我是员工").setLeftImage(R.mipmap.top_view_back);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.titlebar_iv_left, R.id.btnBind,R.id.all_bind})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.all_bind:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.btnBind:
                if (brandNum.getText().toString().trim().equals("")) {
                    showToast("输入品牌编号");
                    return;
                } else if (accounNum.getText().toString().trim().equals("")) {
                    showToast("输入账号");
                    return;
                } else if (accounPwd.getText().toString().trim().equals("")) {
                    showToast("输入密码");
                    return;
                }

                hideKeyboard(v);
                DoLogin(brandNum.getText().toString(), accounNum.getText().toString(), accounPwd.getText().toString());
                break;
        }
    }


    public void DoLogin(String brand, String account, String password) {
        RequestParams params1 = new RequestParams();
        params1.put("type","B");
        params1.put("ppno", brand);
        params1.put("account", account);
        params1.put("password", password);
        params1.put("uid", Config.getCachedUid(E0_EmployeeBindAty.this));


        RequestUtils.ClientTokenPost(E0_EmployeeBindAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_BIND, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {

                        JSONObject data = obj.getJSONObject("data");
                        EmployeData employeData = gson.fromJson(data.toString(),EmployeData.class);
                        Config.cachedBrandAccid(E0_EmployeeBindAty.this, employeData.getAccid());
                        Config.cachedBrandEmpid(E0_EmployeeBindAty.this, employeData.getEmpid());
                        Config.cachedBrandCodes(E0_EmployeeBindAty.this, employeData.getRoles());
                        Config.cachedBrandRights(E0_EmployeeBindAty.this, employeData.getRights());
                        Config.cachedIsemploye(E0_EmployeeBindAty.this, true);
                        intent2Aty(E1_EmployeeInfoAty.class);
                        showToast("绑定成功！");
                        new GetPushagrs(E0_EmployeeBindAty.this).refreshPushagrs();
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
                showToast("请求失败");
            }
        });
    }

}
