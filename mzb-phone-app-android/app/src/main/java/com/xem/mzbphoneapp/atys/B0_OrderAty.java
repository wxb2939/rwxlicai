package com.xem.mzbphoneapp.atys;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.BranchData;
import com.xem.mzbphoneapp.entity.ItemqueryData;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.xem.mzbphoneapp.R.layout.ground_nonet_back;

/**
 * Created by xuebing on 15/7/1.
 */
public class B0_OrderAty extends MzbActivity {

    @InjectView(R.id.all_order)
    View allOrder;
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.titlebar_tv_right)
    TextView more;
    @InjectView(R.id.showdate)
    TextView showDate;
    @InjectView(R.id.showtime)
    TextView showTime;
    @InjectView(R.id.order_num)
    TextView orderNum;
    @InjectView(R.id.order_service)
    TextView orderService;
    @InjectView(R.id.llstore)
    View llStore;
    @InjectView(R.id.b0_order_img)
    ImageView orderImg;
    @InjectView(R.id.b0_order_name)
    TextView orderName;
    @InjectView(R.id.b0_order_adrress)
    TextView orderAddress;
    @InjectView(R.id.b0_order_phone)
    TextView orderPhone;
    @InjectView(R.id.b0_order_sure)
    Button orderSure;
    @InjectView(R.id.time_min)
    ImageView timeMim;
    @InjectView(R.id.time_sum)
    ImageView timeSum;
    @InjectView(R.id.time_value)
    TextView etTimeValue;
    @InjectView(R.id.person_min)
    ImageView personMin;
    @InjectView(R.id.person_sum)
    ImageView personSum;
    @InjectView(R.id.order_mls)
    View orderMls;
    @InjectView(R.id.order_item)
    View orderItem;
    @InjectView(R.id.order_extra)
    View orderExtra;
    @InjectView(R.id.yyxm)
    TextView yyXm;
    @InjectView(R.id.tvextra)
    TextView tvExtra;
    @InjectView(R.id.order_ground)
    RelativeLayout ground;


    private static final int SHOW_DATAPICK = 0;
    private static final int DATE_DIALOG_ID = 1;
    private static final int SHOW_TIMEPICK = 2;
    private static final int TIME_DIALOG_ID = 3;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private BranchData branchData;
    private Double timeValue = 0.5;
    private Integer persNum = 1;
    private String mlsName;
    private Integer mlsId;

    private Integer num = -1;
    private List<ItemqueryData> listItem;
    private ArrayList<String> itemArr;
    private String strExtra;
    private String sTime,etime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b0_order_aty);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("预约").setRightText("预约纪录").setLeftImage(R.mipmap.top_view_back);
        Intent intent = this.getIntent();
        branchData = (BranchData) intent.getSerializableExtra("store");


        llStore.setLayoutParams(Utility.getInstance().getLayoutThreeParamsAty(llStore, B0_OrderAty.this));
        initializeStore();
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        setDateTime();
        setTimeOfDay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkNetAddToast()){
            View grounds = View.inflate(this, ground_nonet_back,null);
            ground.addView(grounds);
        }
    }

    private void initializeStore() {
        if (branchData != null) {
            imageLoader.displayImage(branchData.getLogo(), orderImg);
            orderName.setText(branchData.getName());
            orderAddress.setText(branchData.getAddress());
            orderPhone.setText(branchData.getTel());
        }
    }

    @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case -100:
                Bundle b=data.getExtras();
                mlsName = b.getString("info");
                mlsId = b.getInt("waitid");
                num = b.getInt("num");

                orderService.setText(mlsName);
                break;
            case -101:
                listItem = (List<ItemqueryData>) data.getSerializableExtra("info");

                itemArr = new ArrayList();
                for(int i = 0;i < listItem.size(); i ++){
                    itemArr.add(listItem.get(i).getProjectid().toString());
                }
                if(itemArr.isEmpty()){
                    yyXm.setText("已选定");
                }else {
                    yyXm.setText("待定");
                }
                break;
            case -102:
                strExtra = data.getStringExtra("extra");
                tvExtra.setText(strExtra);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.titlebar_iv_left, R.id.showtime, R.id.showdate,
            R.id.b0_order_sure, R.id.titlebar_tv_right, R.id.all_order,
            R.id.time_min,R.id.time_sum,
            R.id.person_min,R.id.person_sum,
            R.id.order_mls,R.id.order_item,
            R.id.order_extra,R.id.tvextra})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.order_extra:
                Intent orderExtra = new Intent(B0_OrderAty.this,CommWriteAty.class);
                startActivityForResult(orderExtra,0);
                break;
            case R.id.all_order:
                InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.titlebar_tv_right:
                Intent orderrecord = new Intent(B0_OrderAty.this, B1_OrderRecordAty.class);
                orderrecord.putExtra("branid", branchData.getBranid());
                orderrecord.putExtra("custid", branchData.getCustid());
                startActivity(orderrecord);
                break;
            case R.id.showdate:
                Message msgt = new Message();
                msgt.what = B0_OrderAty.SHOW_DATAPICK;
                B0_OrderAty.this.dateandtimeHandler.sendMessage(msgt);
                break;
            case R.id.showtime:
                Message msg = new Message();
                msg.what = B0_OrderAty.SHOW_TIMEPICK;
                B0_OrderAty.this.dateandtimeHandler.sendMessage(msg);
                break;
            case R.id.b0_order_sure:
                sTime = showDate.getText().toString() + " " + showTime.getText().toString();
                DoLogin(sTime,orderNum.getText().toString());
                break;
            case R.id.time_min:
                if (timeValue > 0.5){
                    timeValue -= 0.5;
                    etTimeValue.setText(timeValue.toString());
                }else {
                    timeMim.setImageResource(R.mipmap.time_mins);
//                    timeMim.setClickable(false);
                }
                break;
            case R.id.time_sum:
                timeValue += 0.5;
                timeMim.setImageResource(R.mipmap.icon_min);
                if (timeValue > 10){
                    showToast("请预约明天的时间,谢谢。");
                    return;
                }
                etTimeValue.setText(timeValue.toString());
                break;
            case R.id.person_min:
                if (persNum > 1){
                    persNum -= 1;
                    orderNum.setText(persNum.toString());
                }else {
                    personMin.setImageResource(R.mipmap.time_mins);
//                    personMin.setClickable(false);
                }
                break;
            case R.id.person_sum:
                persNum += 1;
                personMin.setImageResource(R.mipmap.icon_min);
                orderNum.setText(persNum.toString());

                break;
            case R.id.order_mls:
                Intent intent = new Intent(B0_OrderAty.this,B2_SelectMlsAty.class);
                intent.putExtra("num",num);
                startActivityForResult(intent, 0);
                break;
            case R.id.order_item:
                Intent orderItem = new Intent(B0_OrderAty.this,B2_SelectItemAty.class);
                startActivityForResult(orderItem,0);
                break;
            default:
                break;
        }
    }

    private void changTime(){
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        c.add(Calendar.MINUTE, Integer.parseInt(timeValue.toString()));
//        etime = sim.format(c.getTime());


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        int min = (int)(60 * timeValue);
        c.add(Calendar.MINUTE, min);
        etime = sim.format(c.getTime());

       /* SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date d=sim.parse(sTime);
            int min = (int)(60 * timeValue);

            d.setMinutes(d.getMinutes()+min);
            etime = sim.format(d).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

    public void DoLogin(final String ordertime,String ordernum) {
        changTime();
        RequestParams params1 = new RequestParams();
        params1.put("time", ordertime);
        params1.put("person", ordernum);
        params1.put("branid", branchData.getBranid().toString());
        params1.put("custid", branchData.getCustid().toString());
        if(itemArr != null){
            params1.put("project", itemArr);
        }
        if (mlsId != null){
            params1.put("waitid", mlsId.toString());
        }
        if(etime != null){
            params1.put("etime",etime);
        }

        params1.put("memo",strExtra);

        RequestUtils.ClientTokenPost(B0_OrderAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_SEND, params1, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        showToast("预约发送成功。");
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
        showDate.setText(new StringBuilder().append(mYear).append("-")
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
        showTime.setText(new StringBuilder().append(mHour).append(":")
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
                return new DatePickerDialog(B0_OrderAty.this, AlertDialog.THEME_HOLO_LIGHT, mDateSetListener, mYear, mMonth,
                        mDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(B0_OrderAty.this, AlertDialog.THEME_HOLO_LIGHT, mTimeSetListener, mHour, mMinute,
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
                case B0_OrderAty.SHOW_DATAPICK:
                    showDialog(DATE_DIALOG_ID);
                    break;
                case B0_OrderAty.SHOW_TIMEPICK:
                    showDialog(TIME_DIALOG_ID);
                    break;
            }
        }
    };
}
