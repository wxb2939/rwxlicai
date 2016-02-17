package com.xem.mzbphoneapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.atys.WeiClassroomAty;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.utils.Utility;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/6/3.
 */
public class D0_FindFragment extends MzbFragment {

    @InjectView(R.id.d0_find)
    View find;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View footView = inflater.inflate(R.layout.d0_find_fg, container, false);
        new TitleBuilder(footView).setTitleText("发现");
        ButterKnife.inject(this, footView);
        return footView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Utility.getInstance().clickEffect(find);
    }

    @OnClick({R.id.d0_find})
    public void Todo(View v) {
        switch (v.getId()){
            case R.id.d0_find:
                    intent2Activity(WeiClassroomAty.class);
                break;
            default:
                break;
        }
    }


    /*    private ImageLoader loader;
    private CircularImage cover_user_photo;
    private String token;
    private String uid;
    private static int FIMG_REQUEST_CODE = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View footView = inflater.inflate(R.layout.d0_profile_fg, container, false);
        token = Config.getCachedToken(getActivity());
        uid = Config.getCachedUid(getActivity());
        loader = ImageLoader.getInstance();

        TextView title = (TextView) footView.findViewById(R.id.top_view_text);
        title.setText("发现");
        cover_user_photo = (CircularImage) footView.findViewById(R.id.cover_user_photo);
        TextView tv_uid = (TextView) footView.findViewById(R.id.tv_uid);

        if (token != null && uid != null) {


            final String uri = "file:///" + Environment.getExternalStorageDirectory() + "/avateravater.png";
            loader.displayImage(uri, cover_user_photo);
            tv_uid.setText(Config.getCachedName(getActivity()));
        }

        View personLogin = footView.findViewById(R.id.personLogin);
        personLogin.setOnClickListener(new setClicklistener());
        return footView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        uid = Config.getCachedUid(getActivity());
        token = Config.getCachedToken(getActivity());
        if (uid != null && token != null) {
            final String uri = "file:///" + Environment.getExternalStorageDirectory() + "/avateravater.png";
            loader.displayImage(uri, cover_user_photo);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String uri = "file:///" + Environment.getExternalStorageDirectory() + "/avateravater.png";
        loader.displayImage(uri, cover_user_photo);
        return;
    }

    class setClicklistener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.personLogin:
                    if (token != null && uid != null) {
                        Intent profile = new Intent(getActivity(), E1_ProfileInfoAty.class);
                        profile.putExtra(Config.KEY_TOKEN, token);
                        profile.putExtra(Config.KEY_UID, uid);
                        startActivityForResult(profile, FIMG_REQUEST_CODE);
//                        startActivity(profile);
                        break;
                    } else {
                        Intent personLogin = new Intent(getActivity(), A0_LoginAty.class);
                        startActivity(personLogin);
                        break;
                    }
                default:
                    break;
            }
        }

    }*/
}
