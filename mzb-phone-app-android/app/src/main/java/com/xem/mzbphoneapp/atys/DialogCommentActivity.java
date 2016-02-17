package com.xem.mzbphoneapp.atys;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.ShowShare;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/8/5.
 */
public class DialogCommentActivity extends MzbActivity{

    @InjectView(R.id.comm_name)
    TextView name;
    @InjectView(R.id.comm_detail)
    TextView detail;
    @InjectView(R.id.comm_share)
    TextView share;
    @InjectView(R.id.comm_finish)
    TextView finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comment_aty);
        ButterKnife.inject(this);
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.dimAmount=0.5f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        name.setText(application.getBranchData().getName());
    }


    @OnClick({R.id.comm_finish,R.id.comm_share})
    public void click(View v){
        switch (v.getId()) {
            case R.id.comm_finish:
                finish();
                break;
            case R.id.comm_share:
                GetSharebrandata(application.getBranchData().getBranid()+"");
                break;
        }
    }


    public void GetSharebrandata(String branid) {
        RequestParams params = new RequestParams();
        params.put("type", 1+"");
        params.put("branid", branid);
        RequestUtils.ClientTokenPost(DialogCommentActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_BRANDATA, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        JSONObject data = obj.getJSONObject("data");
                        new ShowShare(DialogCommentActivity.this).StoreShowshare(data.getString("title"), data.getString("content"), data.getString("logo"), data.getString("url"));
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(DialogCommentActivity.this, "请求失败,请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
