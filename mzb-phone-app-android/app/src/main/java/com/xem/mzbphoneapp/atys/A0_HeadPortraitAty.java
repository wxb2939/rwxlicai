package com.xem.mzbphoneapp.atys;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xuebing on 15/5/27.
 */

public class A0_HeadPortraitAty extends MzbActivity {

    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;
    private static final int RESULT_CODE = 101;
    private Bitmap bm;
    private Uri bmUri;
    private File tempFile;
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private ImageView cover_user_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_portrait_cell);
        new TitleBuilder(this).setTitleText("设置头像").setLeftImage(R.mipmap.top_view_back).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cover_user_photo = (ImageView) findViewById(R.id.cover_user_photo);
        imageLoader.displayImage(Config.getCachedPortrait(A0_HeadPortraitAty.this), cover_user_photo);


        findViewById(R.id.rlBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        findViewById(R.id.commit_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bmUri != null) {
                    Config.cachedPortrait(A0_HeadPortraitAty.this, bmUri.toString());
                    sendImage();
                } else {
                    showToast("请先更换图片哦！谢谢");
                }
            }
        });

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
            cover_user_photo.setImageBitmap(bm);
            bmUri = saveBitmap(bm);
//            sendImage(bmUri);
        }
    }

    private void sendImage() {
        RequestParams params1 = new RequestParams();
        File file = new File(bmUri.getPath());
        String name = file.getName();
        params1.put("uid", Config.getCachedUid(A0_HeadPortraitAty.this));
        try {
            params1.put("data", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params1.put("name", name);

        RequestUtils.ClientTokenPost(A0_HeadPortraitAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_PORTRAIT, params1, new NetCallBack(this) {

            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        Config.cachedPortrait(A0_HeadPortraitAty.this, obj.getString("data"));
                        showToast("修改已完成");
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
                pd.dismiss();
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
