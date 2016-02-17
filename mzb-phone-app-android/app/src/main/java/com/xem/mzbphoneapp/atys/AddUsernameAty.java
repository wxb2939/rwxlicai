package com.xem.mzbphoneapp.atys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.utils.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/9/7.
 */
public class AddUsernameAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.addextra)
    EditText addExtra;

    private int resultCode = -105;
    private String strExtra = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_write_aty);
        new TitleBuilder(this).setTitleText("添加用户名").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        strExtra = getIntent().getStringExtra("info");
        addExtra.setText(strExtra);
    }

    @OnClick({R.id.titlebar_iv_left})
    public void onDo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                Intent intent = new Intent();
                intent.putExtra("extra", addExtra.getText().toString().trim());
                setResult(resultCode, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
