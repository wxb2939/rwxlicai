package com.xem.mzbemployeeapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.RequestUtils;
import com.xem.mzbemployeeapp.utils.TitleBuilder;
import com.xem.mzbemployeeapp.views.MzbDialogListview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/9/4.
 */
public class E3_Add137ManagerAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.manager_time)
    TextView managerTime;
    @InjectView(R.id.manager_plan)
    TextView managerPlan;
    @InjectView(R.id.manager_extra)
    TextView managerExtra;
    @InjectView(R.id.manager_state)
    TextView managerState;
    @InjectView(R.id.llstate)
    View llstate;
    @InjectView(R.id.llmanager)
    View llManager;
    @InjectView(R.id.sure)
    Button sure;


    private int mYear;
    private int mMonth;
    private int mDay;
    private static final int SHOW_DATAPICK = 0;
    private static final int DATE_DIALOG_ID = 1;
    private String branid;
    private String custid;
    private MzbDialogListview dialogListview;
    private int num;
    private String strExtra = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e3_add137_manager_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("添加新的137计划").setLeftImage(R.mipmap.top_view_back);
        llstate.setVisibility(View.GONE);
        branid = getIntent().getStringExtra("branid");
        custid = getIntent().getStringExtra("custid");
        setDateTime();
    }

    @OnClick({R.id.titlebar_iv_left,R.id.manager_time,R.id.manager_plan,R.id.sure,R.id.llmanager})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.llmanager:
                Intent orderExtra = new Intent(E3_Add137ManagerAty.this,CommWriteAty.class);
                startActivityForResult(orderExtra, 0);
                break;
            case R.id.manager_time:
                Message msgt = new Message();
                msgt.what = E3_Add137ManagerAty.SHOW_DATAPICK;
                E3_Add137ManagerAty.this.dateandtimeHandler.sendMessage(msgt);
                break;

            case R.id.sure:
                if (managerTime.getText().toString().trim().equals("")) {
                    showToast("请选择时间");
                    return;
                } else if (managerPlan.getText().toString().trim().equals("")) {
                    showToast("输入选择项目");
                    return;
                }
                doCommit();
                finish();
                break;
            case R.id.manager_plan:
                dialogListview = new MzbDialogListview(E3_Add137ManagerAty.this,num);
                dialogListview.show();
                dialogListview.dlPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogListview.imgStore.setVisibility(View.VISIBLE);
                        num = 1;
                        managerPlan.setText("预约来店");
                        dialogListview.dismiss();
                    }
                });
                dialogListview.dlCare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogListview.imgCare.setVisibility(View.VISIBLE);
                        num = 2;
                        managerPlan.setText("专业关怀");
                        dialogListview.dismiss();
                    }
                });
                dialogListview.dlExtracare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogListview.imgExtracare.setVisibility(View.VISIBLE);
                        num = 3;
                        managerPlan.setText("特殊关怀");
                        dialogListview.dismiss();
                    }
                });
                dialogListview.dlStore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogListview.imgStore.setVisibility(View.VISIBLE);
                        num = 4;
                        managerPlan.setText("到店护理");
                        dialogListview.dismiss();
                    }
                });
                dialogListview.dlOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogListview.imgOther.setVisibility(View.VISIBLE);
                        num = 5;
                        managerPlan.setText("特殊标记");
                        dialogListview.dismiss();
                    }
                });

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case -102:
                strExtra = data.getStringExtra("extra");
                managerExtra.setText(strExtra);
                break;
            default:
                break;
        }
    }


    public void doCommit() {
        RequestParams params1 = new RequestParams();
        params1.put("custid", custid);
        params1.put("branid", branid);
        params1.put("type", num+"");
        params1.put("date", managerTime.getText().toString());
        params1.put("memo", managerExtra.getText().toString());
        params1.put("creator", Config.getCachedBrandEmpid(E3_Add137ManagerAty.this).toString());

        RequestUtils.ClientTokenPost(E3_Add137ManagerAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_CREATE, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {


                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        showToast("新建成功");
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("请求失败,请确认网络连接!");
            }
        });
    }



    /**
     * 更新日期显示
     */
    private void updateDateDisplay() {

        final Calendar c = Calendar.getInstance();
        int nYear = c.get(Calendar.YEAR);
        int nMonth = c.get(Calendar.MONTH);
        int nDay = c.get(Calendar.DAY_OF_MONTH);

        if (mYear < nYear || mMonth < nMonth || mDay < nDay){
            showToast("当前时间不可选择");
            return;
        }else {
            managerTime.setText(new StringBuilder().append(mYear).append("-")
                    .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                    .append("-").append((mDay < 10) ? "0" + mDay : mDay));
        }

    }

    /**
     * 设置日期
     */
    private void setDateTime() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

//        updateDateDisplay();
    }

    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            updateDateDisplay();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(E3_Add137ManagerAty.this, AlertDialog.THEME_HOLO_LIGHT, mDateSetListener, mYear, mMonth,
                        mDay);
        }
        return null;
    }

   Handler dateandtimeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case E3_Add137ManagerAty.SHOW_DATAPICK:
                    showDialog(DATE_DIALOG_ID);
                    break;
            }
        }
    };
}
