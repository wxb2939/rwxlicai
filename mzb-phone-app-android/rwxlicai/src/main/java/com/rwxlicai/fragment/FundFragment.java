package com.rwxlicai.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rwxlicai.R;
import com.rwxlicai.activity.User_GetAccountRecordAty;
import com.rwxlicai.activity.User_GetTenderRecordAty;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/12/30.
 */
public class FundFragment extends Fragment {

    @InjectView(R.id.invest_list)
    View invest_list;
    @InjectView(R.id.crash_detail)
    View crash_detail;
    @InjectView(R.id.profile_total)
    TextView profile_total;
    @InjectView(R.id.profile_gain)
    TextView profile_gain;
    @InjectView(R.id.profile_balance)
    TextView profile_balance;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fund_fragment, null);
        ButterKnife.inject(this, view);
        userCenter();
        return view;
    }

    @OnClick({R.id.invest_list,R.id.crash_detail})
    public void clickAction(View v) {
        switch (v.getId()) {
            case R.id.invest_list: //投资记录
                startActivity(new Intent(getActivity(), User_GetTenderRecordAty.class));
                break;
            case R.id.crash_detail://资金使用记录
                startActivity(new Intent(getActivity(), User_GetAccountRecordAty.class));
                break;
            default:
                break;
        }
    }


    private void userCenter(){
        RwxHttpClient.ClientTokenPost(getActivity(), RwxUrlFactory.BASE_URL + RwxUrlFactory.userCenter, null, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
                        JSONObject data = obj.getJSONObject("result");
                        profile_total.setText(data.getString("allMoney"));
                        profile_gain.setText(data.getString("getInterest"));
                        profile_balance.setText(data.getString("availableMoney"));
                    } else {
                        Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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
