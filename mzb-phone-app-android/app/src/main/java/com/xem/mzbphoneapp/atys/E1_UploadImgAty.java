package com.xem.mzbphoneapp.atys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/7/7.
 */
public class E1_UploadImgAty extends MzbActivity {
    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.cover_user_photo)
    ImageView mFace;
    @InjectView(R.id.commit_img)
    Button commit;



    private Bitmap bitmap;
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_portrait_cell);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("头像设置").setLeftImage(R.mipmap.top_view_back);

    }

    @OnClick({R.id.titlebar_iv_left,R.id.cover_user_photo})
    public void onDo(View v) {
        switch (v.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;

            case R.id.cover_user_photo:
                AlertDialog.Builder builder = new AlertDialog.Builder(E1_UploadImgAty.this);
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
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(new File(Environment
                                                    .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
                                }
                                startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
                                break;
                            case 1:
                                // 激活系统图库，选择一张图片
                                Intent mintent = new Intent(Intent.ACTION_PICK);
                                mintent.setType("image/*");
                                startActivityForResult(mintent, PHOTO_REQUEST_GALLERY);
                            default:
                                break;
                        }
                    }
                });
                builder.show();
                break;

            default:
                break;
        }
    }


    /**
     * 上传头像
     *
     */
    private void sendImage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        RequestParams params = new RequestParams();
        try {
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();
            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            String photo = new String(encode);
            params.put("photo",photo);

        } catch (IOException e) {
            e.printStackTrace();
        }
        final MzbProgressDialog pd = new MzbProgressDialog(E1_UploadImgAty.this,getResources().getString(R.string.progross_info));
        pd.show();


        RequestUtils.ClientTokenPost(E1_UploadImgAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.PLATFORM_PORTRAIT, params, new NetCallBack() {

            @Override
            public void onMzbSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);

                    if (obj.getInt("code") == 0) {
                        showToast("修改已完成");
                        pd.dismiss();
                        finish();
                    } else {
                        pd.dismiss();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                showToast("未找到存储卡，无法存储照片！");
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                bitmap = data.getParcelableExtra("data");
                this.mFace.setImageBitmap(bitmap);
                boolean delete = tempFile.delete();
                System.out.println("delete = " + delete);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
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
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
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
