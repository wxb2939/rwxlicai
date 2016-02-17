package com.xem.mzbphoneapp.atys;

import android.app.AlertDialog;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xem.mzbphoneapp.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xuebing on 15/6/13.
 */
public class ButterknifeAty extends MzbActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.butterknife);
        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog = new AlertDialog.Builder(ButterknifeAty.this).create();
                ImageView imageView = getView();
                dialog.setView(imageView);
                dialog.show();

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }


    private ImageView getView() {
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new LinearLayout.LayoutParams(50, 20));
        InputStream is = null;
        try {
            is = this.getAssets().open("pico.jpg");

        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable drawable = BitmapDrawable.createFromStream(is, null);

        imgView.setImageDrawable(drawable);

        return imgView;
    }

}
