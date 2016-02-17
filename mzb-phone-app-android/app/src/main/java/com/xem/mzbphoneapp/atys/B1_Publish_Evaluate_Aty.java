package com.xem.mzbphoneapp.atys;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.BranchData;
import com.xem.mzbphoneapp.entity.ItemqueryData;
import com.xem.mzbphoneapp.entity.ServiceDetailData;
import com.xem.mzbphoneapp.entity.ServiceRecord;
import com.xem.mzbphoneapp.entity.Store;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/7/11.
 */
public class B1_Publish_Evaluate_Aty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.all_evaluat)
    View allEvaluate;
    @InjectView(R.id.item_mark)
    RatingBar itemMark;
    @InjectView(R.id.master_mark)
    RatingBar masterMark;
    @InjectView(R.id.service_mark)
    RatingBar serviceMark;
    @InjectView(R.id.effect_mark)
    RatingBar effectMark;
    @InjectView(R.id.et_idea)
    TextView idea;
    @InjectView(R.id.commit_feedback)
    TextView commit;
    @InjectView(R.id.evalute_pic)
    ImageView evalutePic;
    @InjectView(R.id.evaluate_name)
    TextView evaluateName;
    @InjectView(R.id.evaluate_mls)
    TextView evaluateMls;

    //svrid表示服务id，consultant表示顾问得分，beautician表示美疗师得分，
    //service表示服务得分，result表示护理效果得分，comment表示备注

    private String svrid = "0";
    private String consultant = "0";
    private String beautician = "0";
    private String service = "0";
    private String result = "0";
    private String comment = "0";
    private String strExtra = "";
    private ServiceRecord serviceRecord;
    private boolean finished;
    private ServiceDetailData detailData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_evaluate_aty);
        new TitleBuilder(this).setTitleText("发表评价").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);

        Intent intent = this.getIntent();
        serviceRecord = (ServiceRecord) intent.getSerializableExtra("serviceRecord");
        svrid = getIntent().getStringExtra("svrid");
        finished = serviceRecord.getFinished();
        svrid = serviceRecord.getSvrid();

        init();

        itemMark.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                consultant = (int) rating + "";
            }
        });
        masterMark.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                beautician = (int) rating + "";
            }
        });
        serviceMark.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                service = (int) rating + "";
            }
        });
        effectMark.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                result = (int) rating + "";
            }
        });
    }

    private void init() {
        if (serviceRecord != null) {
            imageLoader.displayImage(serviceRecord.getPic(), evalutePic);
            evaluateName.setText(serviceRecord.getName());
            evaluateMls.setText(serviceRecord.getBeautician());
        }
        if (finished) {
            getPublishDetail();
            itemMark.setIsIndicator(true);
            masterMark.setIsIndicator(true);
            serviceMark.setIsIndicator(true);
            effectMark.setIsIndicator(true);
            idea.setClickable(false);
            commit.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case -102:
                strExtra = data.getStringExtra("extra");
                idea.setText(strExtra);
                break;
            default:
                break;
        }

    }

    @OnClick({R.id.titlebar_iv_left, R.id.commit_feedback, R.id.all_evaluat,R.id.et_idea})
    public void Todo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.all_evaluat:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.commit_feedback:
//                intent2Aty(DialogCommentActivity.class);
                commitPublish();
                break;
            case R.id.et_idea:
                Intent orderExtra = new Intent(B1_Publish_Evaluate_Aty.this,CommWriteAty.class);
                startActivityForResult(orderExtra, 0);
                break;
            default:
                break;
        }
    }


    public void commitPublish() {
        RequestParams params = new RequestParams();
        comment = idea.getText().toString().trim();
        params.put("svrid", svrid);
        params.put("consultant", consultant);
        params.put("beautician", beautician);
        params.put("service", service);
        params.put("result", result);
        params.put("comment", strExtra);
        RequestUtils.ClientTokenPost(B1_Publish_Evaluate_Aty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_EVALUATE, params, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        Intent intent = new Intent();
                        intent.putExtra("svrid", svrid);
                        setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
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


    public void getPublishDetail() {
        RequestParams params = new RequestParams();
        params.put("svrid", svrid);
        RequestUtils.ClientTokenPost(B1_Publish_Evaluate_Aty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_DETAIL, params, new NetCallBack() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        detailData = gson.fromJson(obj.getJSONObject("data").toString(), ServiceDetailData.class);
                        itemMark.setRating(Integer.parseInt(detailData.getConscore()));
                        masterMark.setRating(Integer.parseInt(detailData.getBeascore()));
                        serviceMark.setRating(Integer.parseInt(detailData.getSerscore()));
                        effectMark.setRating(Integer.parseInt(detailData.getRescore()));
                        idea.setText(detailData.getComment());
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
