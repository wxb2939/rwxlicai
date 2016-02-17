package com.rwxlicai.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.rwxlicai.R;
import com.rwxlicai.net.NetCallBack;
import com.rwxlicai.net.RwxHttpClient;
import com.rwxlicai.net.RwxUrlFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.zxinglib.zxing.encoding.EncodingHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by xuebing on 15/12/30.
 */
public class InviteFragment extends Fragment {

    @InjectView(R.id.qr_code_image)
    ImageView codeImage;
    @InjectView(R.id.order_btn)
    TextView order_btn;
    @InjectView(R.id.invite_register)
    TextView invite_register;
    @InjectView(R.id.invite_invest)
    TextView invite_invest;

    private UMImage image;
//    UMImage image = new UMImage(getActivity(), "http://www.umeng.com/images/pic/social/integrated_3.png");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invite_fragment2, null);
        ButterKnife.inject(this, view);
        image = new UMImage(getActivity(), BitmapFactory.decodeResource(getResources(), R.mipmap.launcher));
        initData();
        getInviteUser();
        return view;
    }

    private void initData() {
        String business = String.format("%s/%s/%s", "仁我行", 2, "理财");
        String string = Base64.encodeToString(business.getBytes(), Base64.DEFAULT);
        String contentString = String.format("%s", "http://121.40.117.241/web/index.do");
        if (!contentString.equals("")) {
            // 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
            Bitmap qrCodeBitmap = null;
            try {
                qrCodeBitmap = EncodingHandler.createQRCode(
                        contentString, 350);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            codeImage.setImageBitmap(qrCodeBitmap);
        } else {

        }
    }

    @OnClick({R.id.order_btn})
    public void clickAction(View view) {
        switch (view.getId()) {
            case R.id.order_btn:


                new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                new ShareAction(getActivity()).withMedia(image).setPlatform(share_media)
                                        .withTargetUrl("http://121.40.117.241/web/index.do")
                                        .withTitle("仁我行投资")
                                        .withText("仁我行投资—人人都是理财师，推荐好友赚收益")
                                        .setCallback(umShareListener).share();

                            }
                        })
                        .open();


//                new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
//                        .setContentList(new ShareContent(), new ShareContent())
//                        .withMedia(image)
//                        .withTargetUrl("www.rwxlicai.com")
//                        .withTitle("仁我行投资")
//                        .withText("仁我行投资—人人都是理财师，推荐好友赚收益")
//                        .setListenerList(umShareListener,umShareListener)
//                        .open();


                break;

            default:
                break;
        }
    }

    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

        }

    };

    //    onActivityResult()
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }


    private void getInviteUser() {
        RwxHttpClient.ClientTokenPost(getActivity(), RwxUrlFactory.BASE_URL + RwxUrlFactory.getInviteUser, null, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Gson gson = new Gson();
                    if (obj.getInt("code") == 0) {
                        JSONObject data = obj.getJSONObject("result");
                        invite_invest.setText(data.getString("tenderNum"));
                        invite_register.setText(data.getString("inviteNum"));
                    } else {
                        Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                Toast.makeText(getActivity(), "请确认网络连接!", Toast.LENGTH_LONG).show();
            }
        });
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

}
