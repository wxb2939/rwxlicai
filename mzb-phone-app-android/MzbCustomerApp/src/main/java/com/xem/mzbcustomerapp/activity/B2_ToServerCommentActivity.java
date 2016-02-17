package com.xem.mzbcustomerapp.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.ScoreInfoData;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/11/8.
 */
public class B2_ToServerCommentActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.consultant)
    TextView consultant;
    @InjectView(R.id.conscore)
    RatingBar conscore;
    @InjectView(R.id.beautician)
    TextView beautician;
    @InjectView(R.id.beascore)
    RatingBar beascore;
    @InjectView(R.id.serscore)
    RatingBar serscore;
    @InjectView(R.id.rescore)
    RatingBar rescore;
    @InjectView(R.id.comment)
    TextView comment;
    @InjectView(R.id.server_commit_btn)
    Button commit_btn;


    private String mConscore = "0";
    private String mBeascore = "0";
    private String mSerscore = "0";
    private String mRescore = "0";
    private String strExtra = "";
    private String svrid;

    private int resultCode = -106;
    public static int brandid;
    @Override
    protected void initView() {
        setContentView(R.layout.b2_servercomment_activity);
        new TitleBuilder(this).setTitleText("发表评价").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        //服务id
        svrid = getIntent().getStringExtra("svrid");
        getServerData(svrid);
        conscore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mConscore = (int) rating + "";
            }
        });

        beascore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mBeascore = (int) rating + "";
            }
        });
        serscore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mSerscore = (int) rating + "";
            }
        });
        rescore.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mRescore = (int) rating + "";
            }
        });

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case -102:
                strExtra = data.getStringExtra("extra");
                comment.setText(strExtra);
                break;
            default:
                break;
        }

    }



    @OnClick({R.id.titlebar_iv_left,R.id.comment,R.id.server_commit_btn})
    public void Todo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;

            case R.id.comment:
                Intent orderExtra = new Intent(B2_ToServerCommentActivity.this,CommWriteActivity.class);
                startActivityForResult(orderExtra, 0);
                break;

            case R.id.server_commit_btn:
                commitPublish(svrid);
                break;
            default:
                break;
        }
    }

    public void commitPublish(final String svrid) {
        RequestParams params = new RequestParams();
        params.put("svrid", svrid);
        params.put("consultant", mConscore);
        params.put("beautician", mBeascore);
        params.put("service", mSerscore);
        params.put("result", mRescore);
        params.put("comment", strExtra);
        MzbHttpClient.ClientTokenPost(B2_ToServerCommentActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.evaluate, params, new NetCallBack() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        Intent intent = new Intent();
                        intent.putExtra("svrid", svrid);
                        setResult(resultCode, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
//                        finish();
                        //跳转至对话框界面
                        Intent mIntent = new Intent(B2_ToServerCommentActivity.this, DialogCommentActivity.class);
//                      initData();
                        startActivity(mIntent);
                        finish();
//                        startActivityForResult(mIntent,1);
                        showToast("提交评价成功");
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



    //从服务器获取服务数据
//    scoreinfo
    public void getServerData(final String svrid) {
        RequestParams params = new RequestParams();
        params.put("svrid", svrid);
        MzbHttpClient.ClientTokenPost(B2_ToServerCommentActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.scoreinfo, params, new NetCallBack() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    //获取数据成功
                    if (obj.getInt("code") == 0) {
                        ScoreInfoData sid = gson.fromJson(obj.getJSONObject("data").toString(), ScoreInfoData.class);
                        //获取门店id
                        brandid=sid.branid;
                        //获取服务顾问
                        String comment=sid.getConsultant();
                        consultant.setText(comment == null ? "" : comment);
                        //获取美疗师
                        String beautician_str=sid.getBeautician();
                        beautician.setText(beautician_str == null ? "" : beautician_str);
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
