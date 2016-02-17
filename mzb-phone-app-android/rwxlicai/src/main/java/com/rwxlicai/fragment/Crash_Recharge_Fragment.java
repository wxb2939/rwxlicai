package com.rwxlicai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.rwxlicai.R;
import com.rwxlicai.activity.Fuyou_RechargeAcitity;
import com.rwxlicai.entity.Fuyou;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 16/1/18.
 */
public class Crash_Recharge_Fragment extends Fragment {
    @InjectView(R.id.next)
    Button next;
    @InjectView(R.id.crash_available)
    TextView crash_available;
    @InjectView(R.id.crash_recharge)
    EditText crash_recharge;

    private Fuyou mfuyou;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.crash_recharge_fragment, null);
        ButterKnife.inject(this, view);
        initData();
        Bundle bundle1 = getArguments();
//        Float money = bundle1.getFloat("money");
        crash_available.setText(Config.getAvailableMoney(getActivity()));

        return view;
    }

    protected void initData() {

    }

    @OnClick({R.id.next})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.next:
                if (TextUtils.isEmpty(crash_recharge.getText().toString())) {
                    Toast.makeText(getActivity(), "填入充值金额", Toast.LENGTH_LONG).show();
                    return;
                }
                onlineRecharge();
                break;
            default:
                break;
        }
    }


    public void onlineRecharge() {

        RequestParams params = new RequestParams();
        params.put("rechargeType", "1");
        params.put("rechargeId", "30");
        params.put("rechargeMoney", crash_recharge.getText().toString());
        RwxHttpClient.ClientTokenPost(getActivity(), RwxUrlFactory.BASE_URL + RwxUrlFactory.onlineRecharge, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    Gson gson = new Gson();
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();
                        JSONObject jsonObject = obj.getJSONObject("result");
                        Intent mIntent = new Intent(getActivity(), Fuyou_RechargeAcitity.class);
                        mIntent.putExtra("fuyou",jsonObject.toString());
                        mIntent.putExtra("money",crash_recharge.getText().toString());
//                        mfuyou = gson.fromJson(jsonObject.toString(),Fuyou.class);
//                        Intent mIntent = new Intent(getActivity(), Fuyou_RechargeAcitity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("fuyou",mfuyou);
//                        mIntent.putExtras(bundle);
                        startActivity(mIntent);
                    } else {
                        Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(getActivity(), User_realIdentity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(getActivity(), "请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }

}
