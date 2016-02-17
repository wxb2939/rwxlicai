package com.xem.mzbphoneapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.atys.MainTabAty;
import com.xem.mzbphoneapp.view.MzbDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xuebing on 15/7/18.
 */
public class UpDateManager {

    private Context mContext;
    private String mVersion_code;
    private String mVersion_name;
    private String mVersion_path;
    private String mVersion_desc;
    private MzbDialog mDialog;

    public UpDateManager(Context context) {
        mContext = context;
    }


    private Handler mGetVersionHandler = new Handler() {
        public void handleMessage(Message msg) {
            JSONObject jsonObject = (JSONObject) msg.obj;
            try {
                if (jsonObject.getInt("code") == 0) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    mVersion_code = data.getString("state");
                    mVersion_path = data.getString("url");
                    mVersion_name = "美之伴";
                    mVersion_desc = "增加更多的应用功能....";
                    if (isUpdate()) {
                        showNoticeDialog();
                    } else {
//                        Toast.makeText(mContext, "已是最新版本", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 检测软件是否有更新
     */
    public void checkUpdate() {

        String localVersion = "1.0";

        try {
            localVersion = mContext.getPackageManager().getPackageInfo("com.xem.mzbphoneapp", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        RequestParams params = new RequestParams();
        params.put("type", "1");
        params.put("no", localVersion);


        RequestUtils.ClientTokenPost(mContext, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_CHECK, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj != null) {
                        Message msg = Message.obtain();
                        msg.obj = obj;
                        mGetVersionHandler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
//                Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 与本地版本比较判断是否需要更新
     */
    protected boolean isUpdate() {

       /* int serverVersion = Integer.parseInt(mVersion_code);
        int localVersion = 1;

        try {
            localVersion = mContext.getPackageManager().getPackageInfo("com.xem.mzbphoneapp", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (serverVersion > localVersion) {
            return true;
        } else {
            return false;
        }*/

        if (mVersion_code.equals("4")) {
            return true;
        }

        return false;
    }

    /**
     * 有更新时提示对话框
     */
    protected void showNoticeDialog() {

        String title = "提示";
        String message ="软件新增功能，需下载更新！";

        mDialog = new MzbDialog(mContext, title, message);
        mDialog.show();
        mDialog.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mVersion_path));
                mContext.startActivity(intent);
                Activity activity = (Activity) mContext;
                activity.finish();

            }
        });

        mDialog.negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Activity activity = (Activity) mContext;
                activity.finish();
            }
        });





        /*AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        String message = "软件有更新，需下载更新！\n" + mVersion_desc;
        builder.setMessage(message);

        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //显示下载对话框
//                showDownloadDialog();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mVersion_path));
                mContext.startActivity(intent);
                activity.finish();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                activity.finish();
            }
        });
        builder.setCancelable(false);
        builder.create().show();*/

    }

    /**
     * 显示正在下载对话框
     *//*
    protected void showDownloadDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("下载中");
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.id_progress);
        builder.setView(view);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mIsCancel = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();

        //下载文件
        downloadAPK();
    }

    *//**
     * 开启新线程下载文件
     *//*
    private void downloadAPK() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
                        mSavePath = sdPath + "mzb";

                        File dir = new File(mSavePath);
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        //下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(mVersion_path).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();

                        File apkFile = new File(mSavePath, mVersion_name);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];
                        while (!mIsCancel) {
                            int numread = is.read(buffer);
                            count += numread;
                            //计算进度条的当前位置
                            mProgress = (int) ((float) (count / length) * 100);
                            //更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(DOWNLOADING);
                            if (numread < 0) {
                                mUpdateProgressHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    *//**
     * 下载到本地后执行安装
     *//*
    protected void installAPK() {

        File apkFile = new File(mSavePath, mVersion_name);
        if (!apkFile.exists())
            return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + apkFile.toString());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

*/
}
