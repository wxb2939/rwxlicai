package com.xem.mzbcustomerapp.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.fragment.B1_Servercomment_Fragment01;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.ShowShare;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/11/8.
 */
public class DialogCommentActivity extends BaseActivity {
    @InjectView(R.id.comm_name)
    TextView name;
    @InjectView(R.id.comm_detail)
    TextView detail;
    @InjectView(R.id.comm_share)
    TextView share;
    @InjectView(R.id.comm_finish)
    TextView finish;


    @Override
    protected void initView() {
        setContentView(R.layout.dialog_comment_aty);
        ButterKnife.inject(this);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.5f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        name.setText(Config.getCachedCname(DialogCommentActivity.this));
        name.setText(B1_Servercomment_Fragment01.store_name);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.comm_finish, R.id.comm_share})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.comm_finish:
//                setResult(10,null);
//                startActivity(new Intent(this, B2_ToServerCommentActivity.class));
//                finish();
//                intent2Aty(B0_ServerCommentActivity.class);
                startActivity(new Intent(this, B0_ServerCommentActivity.class));
                finish();
                break;
            case R.id.comm_share:
//                GetSharebrandata(Config.getCachedBrandid(DialogCommentActivity.this));
                GetSharebrandata(String.valueOf(B2_ToServerCommentActivity.brandid));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            intent2Aty(B0_ServerCommentActivity.class);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void GetSharebrandata(String branid) {
        RequestParams params = new RequestParams();
        params.put("type", 1+"");
        params.put("branid", branid);
        MzbHttpClient.ClientTokenPost(DialogCommentActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.share_brandata, params, new NetCallBack() {
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
