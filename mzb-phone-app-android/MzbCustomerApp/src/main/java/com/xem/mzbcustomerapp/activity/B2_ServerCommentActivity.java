package com.xem.mzbcustomerapp.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
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
public class B2_ServerCommentActivity extends BaseActivity {

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
    @InjectView(R.id.LinearLayout)
    View ll_view;


    private ScoreInfoData detailData;

    @Override
    protected void initView() {
        setContentView(R.layout.assess_fragment);
        new TitleBuilder(this).setTitleText("我的评价").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        String svrid = getIntent().getStringExtra("svrid");
        getPublishDetail(svrid);
        ll_view.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left})
    public void Todo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }

    public void getPublishDetail(String svrid) {
        RequestParams params = new RequestParams();
        params.put("svrid", svrid);
        MzbHttpClient.ClientTokenPost(B2_ServerCommentActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.scoreinfo, params, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        detailData = gson.fromJson(obj.getJSONObject("data").toString(), ScoreInfoData.class);
                        consultant.setText(detailData.getConsultant());
                        conscore.setRating(Integer.parseInt(detailData.getConscore()));
//                        conscore.setEnabled(false);
//                        conscore.setIsIndicator(false);
                        beautician.setText(detailData.getBeautician());
                        beascore.setRating(Integer.parseInt(detailData.getBeascore()));
//                        beascore.setEnabled(false);
//                        beascore.setIsIndicator(false);
                        serscore.setRating(Integer.parseInt(detailData.getSerscore()));
//                        serscore.setEnabled(false);
//                        serscore.setIsIndicator(false);
                        rescore.setRating(Integer.parseInt(detailData.getRescore()));
//                        rescore.setEnabled(false);
//                        rescore.setIsIndicator(false);
                        comment.setText(detailData.getComment());
//                        commit_btn.setVisibility(View.INVISIBLE);
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
