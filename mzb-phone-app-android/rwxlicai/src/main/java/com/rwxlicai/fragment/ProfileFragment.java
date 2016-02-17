package com.rwxlicai.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rwxlicai.R;
import com.rwxlicai.activity.Borrow_getTenderRecord_Activity;
import com.rwxlicai.activity.Crash_regionActivity;
import com.rwxlicai.activity.Profile_InfoManager_Activity;
import com.rwxlicai.activity.Profile_ManagerActivity;
import com.rwxlicai.activity.Profile_RedPacketsManager_Activity;
import com.rwxlicai.activity.User_bankCardIdentityActivity;
import com.rwxlicai.activity.User_realIdentity;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.rwxlicai.utils.Config;
import com.rwxlicai.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xuebing on 15/12/29.
 */
public class ProfileFragment extends Fragment {

    @InjectView(R.id.profile_img)
    CircleImageView profile_img;
    @InjectView(R.id.profile_manager)
    View profile_manager;
    @InjectView(R.id.invest)
    View invest;
    @InjectView(R.id.crash)
    View crash;
    @InjectView(R.id.redPackge)
    View redPackge;
    @InjectView(R.id.info)
    View info;
    @InjectView(R.id.profile_name)
    TextView profile_name;
    @InjectView(R.id.profile_total)
    TextView profile_total;
    @InjectView(R.id.profile_gain)
    TextView profile_gain;
    @InjectView(R.id.profile_balance)
    TextView profile_balance;


    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    protected ImageLoader imageLoader;

    private Bitmap bm;
    private Uri bmUri;
    private File tempFile;
    private String uri;
    private String availableMoney;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, null);
        ButterKnife.inject(this, view);
        imageLoader = ImageLoader.getInstance();
        userCenter();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        imageLoader.displayImage(Config.getCachedPortrait(getActivity()), profile_img);
        profile_name.setText(Config.getCachedPhone(getActivity()));
    }

    @OnClick({R.id.profile_img, R.id.profile_manager,R.id.invest,R.id.crash,R.id.redPackge,R.id.info})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.info:
                startActivity(new Intent(getActivity(), Profile_InfoManager_Activity.class));
                break;
            case R.id.crash:
                if (Config.getAuth(getActivity())){
                    Intent crash = new Intent(getActivity(), Crash_regionActivity.class);
                    startActivity(crash);
                }else {
                    if (Config.getRealNameState(getActivity()) == "2"){
                        startActivity(new Intent(getActivity(), User_bankCardIdentityActivity.class));
                    }else {
                        startActivity(new Intent(getActivity(), User_realIdentity.class));
                    }
                }

                break;
            case R.id.redPackge: //红包管理
                startActivity(new Intent(getActivity(), Profile_RedPacketsManager_Activity.class));
//                startActivity(new Intent(getActivity(), User_bankCardIdentityActivity.class));
                break;
            case R.id.invest: //投资管理
//                startActivity(new Intent(getActivity(), User_realIdentity.class));
                Intent intent = new Intent(getActivity(), Borrow_getTenderRecord_Activity.class);
                startActivity(intent);
                break;
            case R.id.profile_manager:
                startActivity(new Intent(getActivity(), Profile_ManagerActivity.class));
                break;
            case R.id.profile_img:
                final String[] arrayFruit = new String[]{"拍照", "从相册选"};
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setItems(arrayFruit, new DialogClickListener()
                        ).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
                Window window = alertDialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                window.setWindowAnimations(R.style.dialogstyle);
                alertDialog.show();
                break;
            default:
                break;
        }
    }

    private void userCenter(){
        RwxHttpClient.ClientTokenPost(getActivity(), RwxUrlFactory.BASE_URL + RwxUrlFactory.userCenter, null, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
                        JSONObject data = obj.getJSONObject("result");
                        availableMoney = data.getString("availableMoney");

                        Config.cachedAvailableMoney(getActivity(),availableMoney);
                        profile_total.setText(data.getString("allMoney"));
                        profile_gain.setText(data.getString("getInterest"));
                        profile_balance.setText(availableMoney);
                    } else {
                        Toast.makeText(getActivity(),obj.getString("message"),Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(getActivity(), arg0.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    class DialogClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    //从相机获取
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    // 判断存储卡是否可以用，可用进行存储
                    if (hasSdcard()) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
                    }
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    break;
                case 1:
                    // 激活系统图库，选择一张图片
                    Intent mintent = new Intent(Intent.ACTION_PICK);
                    mintent.setType("image/*");
                    startActivityForResult(mintent, GALLERY_REQUEST_CODE);
                default:
                    break;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                ToastUtils.showToast(getActivity(), "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG);
            }
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            Uri uri;
            uri = data.getData();
            Uri fileUri = convertUri(uri);
            crop(fileUri);
        } else if (requestCode == CROP_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            if (extras == null) {
                return;
            }
            bm = extras.getParcelable("data");
            profile_img.setImageBitmap(bm);
            bmUri = saveBitmap(bm);
            Config.cachedPortrait(getActivity(), bmUri.toString());
            updateAvatar();
        }
    }

    /**
     * 剪切图片
     *
     * @param uri
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }


    private Uri convertUri(Uri uri) {
        InputStream is = null;
        try {
            is = getActivity().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Uri saveBitmap(Bitmap bm) {

        File img = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);

        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    private void updateAvatar() {
        RequestParams params1 = new RequestParams();
        File file = new File(bmUri.getPath());
        try {
            params1.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RwxHttpClient.ClientTokenPost(getActivity(), RwxUrlFactory.BASE_URL + RwxUrlFactory.updateAvatar, params1, new NetCallBack() {

            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        Toast.makeText(getActivity(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(getActivity(),"查看网络连接!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
