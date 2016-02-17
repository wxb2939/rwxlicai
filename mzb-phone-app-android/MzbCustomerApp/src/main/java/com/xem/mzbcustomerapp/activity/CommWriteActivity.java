package com.xem.mzbcustomerapp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/11/3.
 */
public class CommWriteActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.addextra)
    EditText addExtra;

    private int resultCode = -102;

    private String strExtra;
    private String title;

    @Override
    protected void initView() {
        setContentView(R.layout.common_write_aty);
        new TitleBuilder(this).setTitleText("评价内容").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        title = getIntent().getStringExtra("title");
//        new TitleBuilder(this).setTitleText(title).setLeftImage(R.mipmap.top_view_back);
        addExtra.setText(getIntent().getStringExtra("info"));
    }

    @Override
    protected void initData() {

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
