package com.xem.mzbcustomerapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.CommLogin;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.view.MzbProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by xuebing on 15/7/25.
 */
public class DialogActivity extends BaseActivity implements OnClickListener {

    View layout01;

    @Override
    protected void initView(){
        setContentView(R.layout.activity_dialog);
        //得到布局组件对象并设置监听事件
        layout01 = findViewById(R.id.llayout01);
        layout01.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llayout01:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("解除绑定");
                builder.setMessage("解除绑定后您将丢失与该品牌有关的所有数据，是否确认?");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ppid=Config.getCachedPpid(DialogActivity.this);
                        String uid=Config.getCachedUid(DialogActivity.this);
                        UnBindEmployee(uid,ppid);
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("tag", "取消");
                        finish();
                        return;
                    }
                });
                builder.setCancelable(false);
                builder.show();
                break;
        }
    }

    public void UnBindEmployee(final String uid,String ppid) {
        RequestParams params1 = new RequestParams();
        params1.put("uid", uid);
        params1.put("ppid", ppid);
        final MzbProgressDialog pd = new MzbProgressDialog(this, "请稍后....");
        pd.show();

        MzbHttpClient.ClientTokenPost(this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_UNBIND, params1, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        pd.dismiss();
                        showToast("解绑成功");
                        Config.cachedBrandid(DialogActivity.this, null);
                        Config.cachedPpid(DialogActivity.this, null);
                        Config.cachedCustid(DialogActivity.this, null);
//                        GetStoreList(uid);
                        new CommLogin(DialogActivity.this).cdefault(application);
                        intent2Aty(HomeActivity.class);
                        finish();
                    } else {
                        pd.dismiss();
                        showToast("解绑失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                pd.dismiss();
                showToast("请求失败");
            }
        });
    }

//    public void GetStoreList(String uid) {
//        RequestParams params1 = new RequestParams();
//        params1.put("uid", uid);
//
//        MzbHttpClient.ClientTokenPost(this, MzbUrlFactory.BASE_URL + MzbUrlFactory.user_branches, params1, new NetCallBack() {
//            @TargetApi(Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onMzbSuccess(String result) {
//
//                try {
//                    JSONObject obj = new JSONObject(result);
//                    JSONArray jsonArray;
//                    if (obj.getInt("code") == 0) {
//                        jsonArray = new JSONArray(obj.getString("data"));
//                        if (jsonArray.length() != 0) {
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject object = jsonArray.getJSONObject(i);
//                                BranchData mbranchData = gson.fromJson(object.toString(), BranchData.class);
//                                Config.cachedBrandid(DialogActivity.this, mbranchData.getBranid());
//                                Config.cachedPpid(DialogActivity.this, mbranchData.getPpid());
//                                Config.cachedCustid(DialogActivity.this, mbranchData.getCustid());
//                            }
//                        }else{
//                            Config.cachedBrandid(DialogActivity.this, null);
//                            Config.cachedPpid(DialogActivity.this,null);
//                        }
//                        intent2Aty(HomeActivity.class);
//                        finish();
//                    } else {
//                        showToast(obj.getString("message"));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onMzbFailues(Throwable arg0) {
//                showToast("请求失败");
//
//            }
//        });
//    }

}
