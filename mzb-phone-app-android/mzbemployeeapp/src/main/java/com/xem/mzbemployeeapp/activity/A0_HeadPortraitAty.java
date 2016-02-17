package com.xem.mzbemployeeapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbemployeeapp.R;
import com.xem.mzbemployeeapp.net.MzbHttpClient;
import com.xem.mzbemployeeapp.net.NetCallBack;
import com.xem.mzbemployeeapp.utils.Config;
import com.xem.mzbemployeeapp.utils.MzbUrlFactory;
import com.xem.mzbemployeeapp.utils.TitleBuilder;

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
 * Created by xuebing on 15/11/8.
 */
public class A0_HeadPortraitAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.person_info)
    View person_info;
    @InjectView(R.id.circle_img)
    CircleImageView circle_img;
    @InjectView(R.id.commit_img)
    Button commit_img;

    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;

    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private ImageView cover_user_photo;


    private Bitmap bm;
    private Uri bmUri;
    private File tempFile;
    private String uri;

    public static boolean isChanged=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        setContentView(R.layout.head_portrait_cell);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("修改头像").setLeftImage(R.mipmap.top_view_back);
        uri = getIntent().getStringExtra("URI");
        imageLoader.displayImage(uri, circle_img);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                showToast("未找到存储卡，无法存储照片！");
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
            circle_img.setImageBitmap(bm);
            bmUri = saveBitmap(bm);
//            sendImage(bmUri);
        }
    }


    @OnClick({R.id.titlebar_iv_left, R.id.person_info, R.id.commit_img})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.person_info:
                AlertDialog.Builder builder = new AlertDialog.Builder(A0_HeadPortraitAty.this);
                builder.setTitle("请选择");
                final String[] cities = {"拍照", "从相册选"};
                builder.setItems(cities, new DialogInterface.OnClickListener() {
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
                });
                builder.show();
                break;

            case R.id.commit_img:
                if (bmUri != null) {
                    Config.cachedPortrait(A0_HeadPortraitAty.this, bmUri.toString());
                    sendImage();
                } else {
                    showToast("请先更换图片哦！谢谢");
                }
                break;
            default:
                break;
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


    private Uri saveBitmap(Bitmap bm) {

        File img= new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);

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

    private Uri convertUri(Uri uri) {
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
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


    private void sendImage() {
        RequestParams params1 = new RequestParams();
        File file = new File(bmUri.getPath());
        String name = file.getName();
//        params1.put("uid", Config.getCachedUid(A0_HeadPortraitAty.this));
        try {
            params1.put("data", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (Config.getCachedIsexp(this)!= null){
            params1.put("empid", ""+A0_ExpAty.employ.getEmpid());
        }else {
            params1.put("empid", "" + Config.getCachedBrandEmpid(A0_HeadPortraitAty.this));
        }
        MzbHttpClient.ClientTokenPost(A0_HeadPortraitAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.portrait, params1, new NetCallBack() {

            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        Config.cachedPortrait(A0_HeadPortraitAty.this, obj.getString("data"));
                        showToast("头像上传成功");
                        isChanged=true;
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


    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


}
