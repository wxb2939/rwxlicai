package com.xem.mzbphoneapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xem.mzbphoneapp.R;

/**
 * Created by xuebing on 15/6/2.
 */
public class FgImageLoaderDemo extends Fragment {

    private ImageLoader loader;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_loader, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageView2);
        loader = ImageLoader.getInstance();
        loader.displayImage("http://wiki.mob.com/wp-content/uploads/2014/12/SMS_NEW.png", imageView);

        /*loader.displayImage(
                "http://s1.jikexueyuan.com/current/static/images/logo.png",
                iv_img, new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                        Log.i("info", "onLoadingStarted");
                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1,
                                                FailReason arg2) {
                        Log.i("info", "onLoadingFailed");
                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1,
                                                  Bitmap arg2) {
                        Log.i("info", "onLoadingComplete");
                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                        Log.i("info", "onLoadingCancelled");
                    }
                });*/
        return view;
    }
}
