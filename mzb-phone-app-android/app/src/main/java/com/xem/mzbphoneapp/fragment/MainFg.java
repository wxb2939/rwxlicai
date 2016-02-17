package com.xem.mzbphoneapp.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.atys.A0_HeadPortraitAty;
import com.xem.mzbphoneapp.utils.Config;

/**
 * Created by xuebing on 15/5/30.
 */
public class MainFg extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.main_fragment, container, false);

        final String token = Config.getCachedToken(getActivity());
        mainView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("=======" + token);
                if (token == null) {
                    Intent i = new Intent(getActivity(), A0_HeadPortraitAty.class);
                    i.putExtra(Config.KEY_TOKEN, token);
                    startActivity(i);
                } else {

                    // 动态插入一个Fragment到FrameLayout当中
                    //                Fragment fgLogin = new FgLogin();
                    Bundle args = new Bundle();
//                  fgLogin.setArguments(args);
                    Fragment fgImageLoaderDemo = new FgImageLoaderDemo();


                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().addToBackStack(null).replace(R.id.content_frame, fgImageLoaderDemo).commit();
                    System.out.println("===");
                }
            }
        });
        return mainView;
    }
}
