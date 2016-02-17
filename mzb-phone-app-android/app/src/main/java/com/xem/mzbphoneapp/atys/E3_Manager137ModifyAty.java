package com.xem.mzbphoneapp.atys;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.CalendarData;
import com.xem.mzbphoneapp.entity.Plan137detailData;
import com.xem.mzbphoneapp.utils.CompareTime;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.view.MzbDialogListview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/9/4.
 */
public class E3_Manager137ModifyAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.manager_time)
    TextView managerTime;
    @InjectView(R.id.manager_plan)
    TextView managerPlan;
    @InjectView(R.id.manager_extra)
    TextView managerExtra;
    @InjectView(R.id.llmanager)
    View llManager;
    @InjectView(R.id.manager_state)
    TextView managerState;
    @InjectView(R.id.llstate)
    View llstate;
    @InjectView(R.id.sure)
    Button sure;
    @InjectView(R.id.titlebar_tv_right)
    TextView tv_right;


    private int mYear;
    private int mMonth;
    private int mDay;
    private static final int SHOW_DATAPICK = 0;
    private static final int DATE_DIALOG_ID = 1;
    private String planid;

    private MzbDialogListview dialogListview;
    private Integer num = 0;
    private Boolean state = false;
    private String strExtra = null;
    private CalendarData calendarData;
    private String def_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e3_add137_manager_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("修改137计划").setLeftImage(R.mipmap.top_view_back).
                setRightText("取消计划").setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCancel();
            }
        });
        calendarData = (CalendarData) getIntent().getSerializableExtra("key");
        planid = calendarData.getPlanid().toString();
        setDateTime();
        get137Detail();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CompareTime.compare(calendarData.getDate())){
            managerTime.setClickable(false);
            managerPlan.setClickable(false);
            managerState.setClickable(false);
            llManager.setClickable(false);
            managerExtra.setClickable(false);
            sure.setVisibility(View.INVISIBLE);
            tv_right.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.titlebar_iv_left, R.id.manager_time, R.id.manager_plan, R.id.sure,
            R.id.manager_state,R.id.llmanager})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.llmanager:
                Intent orderExtra = new Intent(E3_Manager137ModifyAty.this,CommWriteAty.class);
                startActivityForResult(orderExtra, 0);
                break;
            case R.id.manager_time:
                Message msgt = new Message();
                msgt.what = E3_Manager137ModifyAty.SHOW_DATAPICK;
                E3_Manager137ModifyAty.this.dateandtimeHandler.sendMessage(msgt);
                break;
            case R.id.sure:
                doCommit();
                finish();
                break;
            case R.id.manager_state:
                final String[] mItems = {"未完成", "已完成"};
                AlertDialog.Builder builder = new AlertDialog.Builder(E3_Manager137ModifyAty.this);
                builder.setTitle("选择状态");
                builder.setItems(mItems, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            managerState.setText("未完成");
                            state = false;
                        } else {
                            managerState.setText("已完成");
                            state = true;
                        }
                    }
                });
                builder.create().show();
                break;
            case R.id.manager_plan:
                dialogListview = new MzbDialogListview(E3_Manager137ModifyAty.this, num);
                dialogListview.show();
                //1表示预约来店，2表示专业关怀，3表示特殊关怀，4表示会员到店护理日，5表示特殊标记
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
                break;
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

    public void doCancel() {
        RequestParams params1 = new RequestParams();
        params1.put("planid", planid);
        RequestUtils.ClientTokenPost(E3_Manager137ModifyAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_137CANCEL, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        showToast("取消成功");
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
                showToast("请求失败,请确认网络连接!");
            }
        });
    }

    public void get137Detail() {
        RequestParams params1 = new RequestParams();
        params1.put("planid", planid);
        RequestUtils.ClientTokenPost(E3_Manager137ModifyAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_DETAIL, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        Plan137detailData plan137detailData = gson.fromJson(obj.getJSONObject("data").toString(), Plan137detailData.class);
                        def_time = plan137detailData.getDate();
                        num = plan137detailData.getType();
                        managerTime.setText(plan137detailData.getDate());
                        managerExtra.setText(plan137detailData.getMemo());

                        switch (plan137detailData.getType()){
                            case 1:
                                managerPlan.setText("预约来店");
                                break;
                            case 2:
                                managerPlan.setText("专业关怀");
                                break;
                            case 3:
                                managerPlan.setText("特殊关怀");
                                break;
                            case 4:
                                managerPlan.setText("到店护理");
                                break;
                            case 5:
                                managerPlan.setText("特殊标记");
                                break;
                            default:
                                managerPlan.setText("其他");
                                break;
                        }

                        if (plan137detailData.getFinished()){
                            managerState.setText("已完成");
                        }else {
                            managerState.setText("未完成");
                        }
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



    public void doCommit() {
        RequestParams params1 = new RequestParams();
        params1.put("planid", planid);
        params1.put("type", num + "");
        params1.put("date",def_time);
        params1.put("memo", strExtra);
        params1.put("finished", state.toString());


        RequestUtils.ClientTokenPost(E3_Manager137ModifyAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_MODIFY, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        showToast("修改成功");
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
        managerTime.setText(new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                .append("-").append((mDay < 10) ? "0" + mDay : mDay));
    }

    /**
     * 设置日期
     */
    private void setDateTime() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDateDisplay();
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
                return new DatePickerDialog(E3_Manager137ModifyAty.this, AlertDialog.THEME_HOLO_LIGHT, mDateSetListener, mYear, mMonth,
                        mDay);
        }
        return null;
    }

    Handler dateandtimeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case E3_Manager137ModifyAty.SHOW_DATAPICK:
                    showDialog(DATE_DIALOG_ID);
                    break;
            }
        }
    };

}
