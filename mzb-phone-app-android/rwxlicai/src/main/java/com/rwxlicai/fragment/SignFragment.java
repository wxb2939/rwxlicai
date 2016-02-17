package com.rwxlicai.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.rwxlicai.R;
import com.rwxlicai.activity.CircleTest;
import com.rwxlicai.utils.Constants;
import com.rwxlicai.utils.DesEncrypt;
import com.rwxlicai.utils.ShakeUtils;
import java.util.Random;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/12/30.
 */
public class SignFragment extends Fragment {

    @InjectView(R.id.shakeImgId)
    ImageView mShakeImageView;
    @InjectView(R.id.encrty)
    TextView encrty;
    @InjectView(R.id.decrty)
    TextView decrty;
    @InjectView(R.id.button)
    Button button;

    private String DATA = "123456";
    private String key = "6Ta4OaHZdpA=";
//    密钥：6Ta4OaHZdpA=

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_fragment, null);
        ButterKnife.inject(this, view);
        initShakeUtils();
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    private void test() throws Exception {
        DesEncrypt des = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
        String str1 = "123456";

        // DES加密
        String str2 = des.encrypt(str1);

        DesEncrypt des1 = new DesEncrypt(Constants.DES_PUBLIC_ENCRYPT_KEY);
        String deStr = des1.decrypt(str2);

        System.out.println("密文:" + str2);
        encrty.setText("加密"+str2);

        // DES解密
        System.out.println("明文:" + deStr);
        decrty.setText("解密" + deStr);

    }


    private void initShakeUtils(){
        mShakeUtils = new ShakeUtils(getActivity());
        mShakeUtils.setOnShakeListener( new ShakeUtils.OnShakeListener( ) {
            @Override
            public void onShake() {
                setShakeImage( );
            }
        });
    }

    private void setShakeImage( ){
        Random random = new Random( );
        mShakeImageView.setBackgroundResource( mBeautys[ ( Math.abs(random.nextInt( ) ) )%mBeautys.length ] );
    }

    private ShakeUtils mShakeUtils = null;
    private static final int[] mBeautys = new int[]{
            R.drawable.profile_img
            ,R.mipmap.index03
            ,R.mipmap.index02
            ,R.mipmap.index01
            ,R.mipmap.ic_launcher
    };


    @Override
    public void onResume() {
        super.onResume();
        mShakeUtils.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mShakeUtils.onPause();
    }

    @OnClick({R.id.button})
    public void clickAction(View v) {
        switch (v.getId()) {
            case R.id.button:
                startActivity(new Intent(getActivity(), CircleTest.class));
                break;
        }
    }
}
