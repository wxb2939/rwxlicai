package com.xem.mzbphoneapp.atys;

import android.app.Activity;
import android.os.Bundle;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.view.CircularImage;

/**
 * Created by xuebing on 15/6/9.
 */
public class Circular extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circular);

        CircularImage cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);
        cover_user_photo.setImageResource(R.drawable.profile_img);
    }
}
