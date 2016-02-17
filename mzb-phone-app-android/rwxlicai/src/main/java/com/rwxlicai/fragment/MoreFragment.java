package com.rwxlicai.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rwxlicai.R;
import com.rwxlicai.activity.More_Calculator_Activity;
import com.rwxlicai.activity.More_Opinionfeedback_Activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by xuebing on 15/12/30.
 */
public class MoreFragment extends Fragment {

    @InjectView(R.id.version)
    TextView version;
    @InjectView(R.id.ll_call)
    View ll_call;
    @InjectView(R.id.about_us)
    View about_us;
    @InjectView(R.id.question)
    View question;
    @InjectView(R.id.idea)
    View idea;
    @InjectView(R.id.calculate)
    View calculate;


    private String localVersion = "1.0";
    MaterialDialog mMaterialDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_fragment, null);
        ButterKnife.inject(this, view);
        initData();
        return view;
    }

    protected void initData() {
        try {
            localVersion = getActivity().getPackageManager().getPackageInfo("com.rwxlicai", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version.setText("V" + localVersion);
    }

    @OnClick({R.id.ll_call,R.id.about_us,R.id.question,R.id.idea,R.id.calculate})
    public void clickAction(View view){
        switch (view.getId()){
            case R.id.idea:
                startActivity(new Intent(getActivity(), More_Opinionfeedback_Activity.class));
                break;
            case R.id.calculate:
                startActivity(new Intent(getActivity(), More_Calculator_Activity.class));
                break;
            case R.id.about_us:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse("http://121.40.117.241/web/content/toShowContentListInfo/0/55.do");
                intent.setData(uri);
                startActivity(intent);
                break;
            case R.id.question:
                Intent question = new Intent();
                question.setAction("android.intent.action.VIEW");
                Uri muri = Uri.parse("http://121.40.117.241/web/content/toShowContentListInfo/0/44.do");
                question.setData(muri);
                startActivity(question);
                break;

            case R.id.ll_call:
                mMaterialDialog = new MaterialDialog(getActivity());
                mMaterialDialog.setTitle("拨打电话");
                mMaterialDialog.setMessage("是否呼叫？");
                mMaterialDialog.setPositiveButton("呼叫", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        Uri uri = Uri.parse("tel:" + "400-180-9959");
                        Intent it = new Intent();   //实例化Intent
                        it.setAction(Intent.ACTION_CALL);   //指定Action
                        it.setData(uri);   //设置数据
                        startActivity(it);//启动Acitivity
                    }
                });
                mMaterialDialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
                mMaterialDialog.show();
                break;
            default:
                break;
        }
    }
}
