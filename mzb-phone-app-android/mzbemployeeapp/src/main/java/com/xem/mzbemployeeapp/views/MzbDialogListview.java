package com.xem.mzbemployeeapp.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xem.mzbemployeeapp.R;


/**
 * Created by xuebing on 15/9/4.
 */
public class MzbDialogListview {

    private Dialog mDialog;
    public TextView dialog_title;
    public View dlPhone;
    public View dlCare;
    public View dlExtracare;
    public View dlStore;
    public View dlOther;
    public ImageView imgPhone;
    public ImageView imgCare;
    public ImageView imgExtracare;
    public ImageView imgStore;
    public ImageView imgOther;



    public MzbDialogListview(Context context, int num) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_listview, null);
        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });

        dialog_title = (TextView) view.findViewById(R.id.dialog_title);
        dlPhone = view.findViewById(R.id.dl_phone);
        dlCare =  view.findViewById(R.id.dl_care);
        dlExtracare = view.findViewById(R.id.extracare);
        dlStore = view.findViewById(R.id.dl_store);
        dlOther = view.findViewById(R.id.dl_other);
        imgPhone = (ImageView) view.findViewById(R.id.im_phone);
        imgCare = (ImageView) view.findViewById(R.id.im_care);
        imgExtracare = (ImageView) view.findViewById(R.id.im_extracare);
        imgStore = (ImageView) view.findViewById(R.id.img_store);
        imgOther = (ImageView) view.findViewById(R.id.img_other);

        switch (num){
            case 1:
                imgPhone.setVisibility(View.VISIBLE);
                break;
            case 2:
                imgCare.setVisibility(View.VISIBLE);
                break;
            case 3:
                imgExtracare.setVisibility(View.VISIBLE);
                break;
            case 4:
                imgExtracare.setVisibility(View.VISIBLE);
                break;
            case 5:
                imgOther.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }
}
