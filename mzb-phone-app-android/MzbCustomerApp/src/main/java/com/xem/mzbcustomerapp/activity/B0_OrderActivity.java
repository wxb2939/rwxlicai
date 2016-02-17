package com.xem.mzbcustomerapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.BranchData;
import com.xem.mzbcustomerapp.entity.BrandinfoData;
import com.xem.mzbcustomerapp.entity.OrderDetailData;
import com.xem.mzbcustomerapp.entity.PprojectData;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.utils.LoadImgUtil;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/24.
 */
public class B0_OrderActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView titlebar_iv_left;
    @InjectView(R.id.titlebar_iv_right)
    ImageView titlebar_iv_right;
    @InjectView(R.id.b0_order_img)
    ImageView order_img;
    @InjectView(R.id.b0_order_name)
    TextView order_name;
    @InjectView(R.id.b0_order_phone)
    TextView order_phone;
    @InjectView(R.id.b0_order_adrress)
    TextView order_adrress;
    private Integer num = -1;


    @InjectView(R.id.showdate)
    TextView show_date;
    @InjectView(R.id.showtime)
    TextView show_time;
    @InjectView(R.id.time_value)
    TextView time_value;
    @InjectView(R.id.time_min)
    ImageView time_min;
    @InjectView(R.id.time_sum)
    ImageView time_sum;


    @InjectView(R.id.order_mls)
    View order_mls;
    @InjectView(R.id.order_service)
    TextView order_service;
    @InjectView(R.id.mls_next)
    ImageView mls_next;


    @InjectView(R.id.order_xm)
    View order_xm;
    @InjectView(R.id.xm_name)
    TextView xm_name;
    @InjectView(R.id.xm_img)
    ImageView xm_img;

    @InjectView(R.id.order_num)
    TextView order_person_num;
    @InjectView(R.id.person_min)
    ImageView person_min;
    @InjectView(R.id.person_sum)
    ImageView person_sum;
    @InjectView(R.id.order_state)
    TextView order_state;


    @InjectView(R.id.order_extra)
    View order_extra;
    @InjectView(R.id.tvextra)
    TextView tv_extra;
    @InjectView(R.id.order_btn)
    Button order_btn;
    @InjectView(R.id.order_cacle)
    View order_cancle;


    private static final int SHOW_DATAPICK = 0;
    private static final int DATE_DIALOG_ID = 1;
    private static final int SHOW_TIMEPICK = 2;
    private static final int TIME_DIALOG_ID = 3;

    private BranchData brandinfoData;
    private Double timeValue = 0.5;
    private Integer persNum = 1;
    private String mlsName;
    private Integer mlsId;
    private String strExtra;
    private String sTime, etime;
    private ArrayList<String> itemArr;
    private List<PprojectData> select_data = new ArrayList<>();
    private OrderDetailData orderDetailData;
    private String curBranid;
    private String curPpid;
    private String curState;
    private String appoid;


    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    private String targetId;
    private String name;
    private String mxName;

    @Override
    protected void initView() {
        setContentView(R.layout.b0_order_activity);
        new TitleBuilder(this).setTitleText("预约").setLeftImage(R.mipmap.top_view_back).setRightText("切换门店");
        ButterKnife.inject(this);
        targetId = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        xm_name.setText(name);

        brandInfo(Config.getCachedPpid(B0_OrderActivity.this), Config.getCachedBrandid(B0_OrderActivity.this));
        getOrderDetail(Config.getCachedBrandid(B0_OrderActivity.this), Config.getCachedCustid(B0_OrderActivity.this));
    }

    public void refreshView() {
        getOrderDetail(Config.getCachedBrandid(B0_OrderActivity.this), Config.getCachedCustid(B0_OrderActivity.this));
        brandInfo(Config.getCachedPpid(B0_OrderActivity.this), Config.getCachedBrandid(B0_OrderActivity.this));
    }

    //获取预约信息的方法
    public void getOrderDetail(String branid, String custid) {
        RequestParams params1 = new RequestParams();
        params1.put("branid", branid);
        params1.put("custid", custid);
        MzbHttpClient.ClientTokenPost(B0_OrderActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.order_last, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        orderDetailData = gson.fromJson(obj.getString("data"), OrderDetailData.class);
                        if (orderDetailData != null) {
                            curState = orderDetailData.getState();
                            appoid = orderDetailData.getAppoid();
                            String year = orderDetailData.stime.substring(0, orderDetailData.stime.indexOf(" ") + 1);
                            show_date.setText(year);
                            xm_name.setText("");
                            if (orderDetailData.getProject() == null || orderDetailData.getProject().size() == 0) {
                                xm_name.setText("未选择");
                            }else  {
                                for (int a = 0; a < orderDetailData.getProject().size(); a++) {
                                    xm_name.append(orderDetailData.getProject().get(a).pname + "\t\t");
                                }
                            }
                            String time = orderDetailData.stime.substring(orderDetailData.stime.indexOf(" ") + 1);
                            show_time.setText(time);
                            if (curState.equals("1")) {
                                order_state.setText("预约安排中");
                                //设置选项不能够被点击
                                if (order_state.getText().equals("预约安排中") || order_state.getText().equals("预约已成功")) {
                                    show_date.setEnabled(false);
                                    show_time.setEnabled(false);
                                    order_service.setEnabled(false);
                                    xm_name.setEnabled(false);
                                    order_mls.setEnabled(false);
                                    order_xm.setEnabled(false);
                                    tv_extra.setEnabled(false);
                                    order_extra.setEnabled(false);
                                    refreshUI(orderDetailData);
                                } else {
                                    show_date.setEnabled(true);
                                    show_time.setEnabled(true);
                                    order_service.setEnabled(true);
                                    xm_name.setEnabled(true);
                                    order_mls.setEnabled(true);
                                    order_xm.setEnabled(true);
                                    tv_extra.setEnabled(true);
                                    order_extra.setEnabled(true);
                                }
                            } else if (curState.equals("2")) {
                                order_state.setText("预约已成功");
                                //设置选项不能够被点击
                                if (order_state.getText().equals("预约已成功")) {
                                    show_date.setEnabled(false);
                                    show_time.setEnabled(false);
                                    order_service.setEnabled(false);
                                    xm_name.setEnabled(false);
                                    order_mls.setEnabled(false);
                                    order_xm.setEnabled(false);
                                    tv_extra.setEnabled(false);
                                    order_extra.setEnabled(false);
                                    refreshUI(orderDetailData);
                                }
                            } else {
                                final Calendar c = Calendar.getInstance();
                                mYear = c.get(Calendar.YEAR);
                                mMonth = c.get(Calendar.MONTH);
                                mDay = c.get(Calendar.DAY_OF_MONTH);
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);
                                setDateTime();
                                setTimeOfDay();
                                freshUI();
                            }
                        } else {
                            final Calendar c = Calendar.getInstance();
                            mYear = c.get(Calendar.YEAR);
                            mMonth = c.get(Calendar.MONTH);
                            mDay = c.get(Calendar.DAY_OF_MONTH);
                            mHour = c.get(Calendar.HOUR_OF_DAY);
                            mMinute = c.get(Calendar.MINUTE);
                            setDateTime();
                            setTimeOfDay();
                            freshUI();
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
                showToast("请求失败");
            }
        });
    }

    private void refreshUI(OrderDetailData orderDetailData) {

        order_btn.setVisibility(View.INVISIBLE);

        order_cancle.setVisibility(View.VISIBLE);
        time_min.setVisibility(View.INVISIBLE);
        time_sum.setVisibility(View.INVISIBLE);
        person_min.setVisibility(View.INVISIBLE);
        person_sum.setVisibility(View.INVISIBLE);
        mls_next.setVisibility(View.INVISIBLE);
        xm_img.setVisibility(View.INVISIBLE);

        String waiter = orderDetailData.getWaiter();
        order_service.setText(waiter == null || waiter.equals("") ? "未选择" : orderDetailData.getWaiter());
        orderDetailData.getProject().toString();
        xm_name.setText("");
        if (orderDetailData.getProject() == null || orderDetailData.getProject().size() == 0) {
            xm_name.setText("未选择");
        } else {
            for (int a = 0; a < orderDetailData.getProject().size(); a++) {
                xm_name.append(orderDetailData.getProject().get(a).pname + "\t\t");
            }
        }
    }

    private void freshUI() {
        order_btn.setVisibility(View.VISIBLE);
        order_cancle.setVisibility(View.INVISIBLE);
        time_min.setVisibility(View.VISIBLE);
        time_sum.setVisibility(View.VISIBLE);
        person_min.setVisibility(View.VISIBLE);
        person_sum.setVisibility(View.VISIBLE);
        mls_next.setVisibility(View.VISIBLE);
        xm_img.setVisibility(View.VISIBLE);

        order_service.setText("未选定");
        xm_name.setText("未选定");
        order_state.setText("新增预约");
    }


    @Override
    protected void initData() {

    }


    @OnClick({R.id.titlebar_iv_left, R.id.titlebar_tv_right, R.id.showdate, R.id.showtime,
            R.id.time_min, R.id.time_sum, R.id.person_min, R.id.person_sum, R.id.order_mls,
            R.id.order_extra, R.id.order_btn, R.id.order_xm, R.id.order_cacle})
    public void clickAction(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.order_cacle:
                if (appoid != null) {
                    orderCancle(appoid);
                    showToast("预约已取消");
                    refresh();
                }
                break;
            case R.id.titlebar_tv_right:
                Intent mIntent = new Intent(B0_OrderActivity.this, B1_StoreListActivity.class);
                startActivityForResult(mIntent, 0);
                break;
            case R.id.showdate:
                Message msgt = new Message();
                msgt.what = B0_OrderActivity.SHOW_DATAPICK;
                B0_OrderActivity.this.dateandtimeHandler.sendMessage(msgt);
                break;
            case R.id.showtime:
                Message msg = new Message();
                msg.what = B0_OrderActivity.SHOW_TIMEPICK;
                B0_OrderActivity.this.dateandtimeHandler.sendMessage(msg);
                break;
            case R.id.time_min:
                if (timeValue > 0.5) {
                    timeValue -= 0.5;
                    time_value.setText(timeValue.toString());
                } else {
                    time_min.setImageResource(R.mipmap.time_mins);
//                    timeMim.setClickable(false);
                }
                break;
            case R.id.time_sum:
                timeValue += 0.5;
                time_min.setImageResource(R.mipmap.icon_min);
                if (timeValue > 10) {
                    showToast("请预约明天的时间,谢谢。");
                    return;
                }
                time_value.setText(timeValue.toString());
                break;

            case R.id.person_min:
                if (persNum > 1) {
                    persNum -= 1;
                    order_person_num.setText(persNum.toString());
                } else {
                    person_min.setImageResource(R.mipmap.time_mins);
//                    personMin.setClickable(false);
                }
                break;
            case R.id.person_sum:
                persNum += 1;
                person_min.setImageResource(R.mipmap.icon_min);
                order_person_num.setText(persNum.toString());
                break;

            case R.id.order_mls:
                Intent intent = new Intent(B0_OrderActivity.this, B2_SelectMlsActivity.class);
                intent.putExtra("num", num);
                startActivityForResult(intent, 0);
                break;
            case R.id.order_extra:
                Intent orderExtra = new Intent(B0_OrderActivity.this, CommWriteActivity.class);
                orderExtra.putExtra("title", "修改备注");
                orderExtra.putExtra("info", tv_extra.getText().toString().trim());
                startActivityForResult(orderExtra, 0);
                break;
            //提交预约
            case R.id.order_btn:
                sTime = show_date.getText().toString() + " " + show_time.getText().toString();
                commitOrder(sTime, order_person_num.getText().toString());
                break;

            case R.id.order_xm:
//                Intent xmIntent = new Intent(B0_OrderActivity.this, B2_SelectXmActivity.class);
                Intent xmIntent = new Intent(B0_OrderActivity.this, B2_SelectXmAty.class);
                startActivityForResult(xmIntent, 0);
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            //切换门店后返回
            case -100:
                brandinfoData = (BranchData) data.getSerializableExtra("branchData");
                if (brandinfoData != null) {
                    if (!order_state.getText().equals("预约安排中") || !order_state.getText().equals("预约已成功")) {
                        show_date.setEnabled(true);
                        show_time.setEnabled(true);
                        order_service.setEnabled(true);
                        xm_name.setEnabled(true);
                        order_mls.setEnabled(true);
                        order_xm.setEnabled(true);
                        tv_extra.setEnabled(true);
                        order_extra.setEnabled(true);
                    }
                    imageLoader.displayImage(LoadImgUtil.loadbigImg(brandinfoData.getLogo()), order_img);
                    order_name.setText(brandinfoData.getName());
                    order_adrress.setText(brandinfoData.getAddress());
                    order_phone.setText(brandinfoData.getTel());
                    curBranid = brandinfoData.getBranid();
                    curPpid = brandinfoData.getPpid();
                    Config.cachedPpid(B0_OrderActivity.this, brandinfoData.getPpid());
                    Config.cachedBrandid(B0_OrderActivity.this, brandinfoData.getBranid());
                    Config.cachedCustid(B0_OrderActivity.this, brandinfoData.getCustid());
                    getOrderDetail(curBranid, brandinfoData.getCustid());
                }
                break;

            case -101:
                Bundle b = data.getExtras();
                mlsName = b.getString("info");
                mlsId = b.getInt("waitid");
                num = b.getInt("num");
                order_service.setText(mlsName);
                mls_next.setVisibility(View.INVISIBLE);
                break;
            case -102:
                strExtra = data.getStringExtra("extra");
                tv_extra.setText(strExtra);
                break;

            case -104:
                select_data = (List<PprojectData>) data.getSerializableExtra("xm");
                StringBuffer buffer = new StringBuffer();
                itemArr = new ArrayList();
                for (int i = 0; i < select_data.size(); i++) {
                    buffer.append(select_data.get(i).getName() + "  ");
                    itemArr.add(select_data.get(i).getProjectid());

                }
                if (select_data  == null) {
                    xm_name.setText("未选择");
                    return;
                }
                xm_name.setText(buffer.toString());
                xm_img.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }


    private String format(List<String> alist) {

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < alist.size(); i++) {
            buffer.append("  " + alist.get(i));
        }
        return buffer.toString();
    }

    //刷新activityd的方法
    private void refresh() {
        finish();
        Intent intent = new Intent(B0_OrderActivity.this, B0_OrderActivity.class);
        startActivity(intent);
    }

    //提交预约的方法
    public void commitOrder(final String ordertime, String ordernum) {
        changTime();
        RequestParams params1 = new RequestParams();
        params1.put("time", ordertime);
        params1.put("person", ordernum);
//        params1.put("branid", );
        params1.put("branid", curBranid == null || curBranid.equals("") ? Config.getCachedBrandid(B0_OrderActivity.this) : curBranid);
        params1.put("custid", Config.getCachedCustid(B0_OrderActivity.this));
        if (ordertime != null) {
            params1.put("time", ordertime);
        } else {
            showToast("请选择预约时间。");
            return;
        }
        if (itemArr != null) {
            params1.put("project", itemArr);
        }else {
            params1.put("project",targetId);
        }
        if (mlsId != null) {
            params1.put("waitid", mlsId.toString());
        }
        if (etime != null) {
            params1.put("etime", etime);
        }

        params1.put("memo", strExtra);

        MzbHttpClient.ClientTokenPost(B0_OrderActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.em_send, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        showToast("预约发送成功");
                        refreshView();
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


    private void changTime() {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Calendar c = Calendar.getInstance();
            Date d = sim.parse(sTime);
            c.setTime(d);
            int min = (int) (60 * timeValue);
            c.add(Calendar.MINUTE, min);
            etime = sim.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //取消预约的方法
    private void orderCancle(String appoid) {
        RequestParams params = new RequestParams();
        params.put("appoid", appoid);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.order_cancel, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        showToast("成功取消预约。");
////                        finish();
//                        refresh();
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("");
            }
        });
    }


    //请求店铺基本信息
    private void brandInfo(String ppid, String branid) {
        RequestParams params = new RequestParams();
        params.put("ppid", ppid);
        params.put("branid", branid);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.brandinfo, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        BrandinfoData brandinfoData = gson.fromJson(obj.getJSONObject("data").toString(), BrandinfoData.class);
                        imageLoader.displayImage(LoadImgUtil.loadbigImg(brandinfoData.getLogo()), order_img);
                        order_name.setText(brandinfoData.getName());
                        order_adrress.setText(brandinfoData.getAddress());
                        order_phone.setText(brandinfoData.getTel());
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("");
            }
        });
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
     * 更新日期显示
     */
    private void updateDateDisplay() {
        show_date.setText(new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                .append("-").append((mDay < 10) ? "0" + mDay : mDay));
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

    /**
     * 设置时间
     */
    private void setTimeOfDay() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        updateTimeDisplay();
    }

    /**
     * 更新时间显示
     */
    private void updateTimeDisplay() {
        show_time.setText(new StringBuilder().append(mHour).append(":")
                .append((mMinute < 10) ? "0" + mMinute : mMinute));
    }

    /**
     * 时间控件事件
     */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;

            updateTimeDisplay();
        }
    };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(B0_OrderActivity.this, AlertDialog.THEME_HOLO_LIGHT, mDateSetListener, mYear, mMonth,
                        mDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(B0_OrderActivity.this, AlertDialog.THEME_HOLO_LIGHT, mTimeSetListener, mHour, mMinute,
                        true);
        }
        return null;
    }


    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
            case TIME_DIALOG_ID:
                ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
                break;
        }
    }

    Handler dateandtimeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case B0_OrderActivity.SHOW_DATAPICK:
                    showDialog(DATE_DIALOG_ID);
                    break;
                case B0_OrderActivity.SHOW_TIMEPICK:
                    showDialog(TIME_DIALOG_ID);
                    break;
            }
        }
    };
}
