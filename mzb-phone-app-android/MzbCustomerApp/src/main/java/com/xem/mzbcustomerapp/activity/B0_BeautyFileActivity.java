package com.xem.mzbcustomerapp.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.BeautiFileContentData;
import com.xem.mzbcustomerapp.entity.BeautiFilePictureData;
import com.xem.mzbcustomerapp.entity.BeautyFiledetailData;
import com.xem.mzbcustomerapp.entity.RecordsData;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.utils.LoadImgUtil;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/24.
 */
public class B0_BeautyFileActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView titlebar_iv_left;
    @InjectView(R.id.titlebar_iv_right)
    ImageView titlebar_iv_right;
    @InjectView(R.id.listView_list)
    ListView listView;
    @InjectView(R.id.order_btn)
    Button order_btn;

    JSONArray contentArray;
    JSONArray pictureArray;
    private RecordsData recordsData;

    private List<String> list = null;
    List<String> aList = new ArrayList<>();
    List<String> bList = new ArrayList<>();
    List<String> cList = new ArrayList<String>();
    private List<String> groupkey = new ArrayList<String>();
    private List<BeautiFileContentData> contentList = new ArrayList<>();
    private List<BeautiFilePictureData> pictureList = new ArrayList<>();


    private ImageView plogo;//品牌Logo
    private TextView pname;//品牌名称
    private TextView ptime;//诊断时间


    @Override
    protected void initView() {
        setContentView(R.layout.b0_beautyfile_activity_new);
        new TitleBuilder(this).setTitleText("美丽档案").setLeftImage(R.mipmap.top_view_back).setRightText("所有档案");
        ButterKnife.inject(this);
        list = new ArrayList<>();
        View headview = LayoutInflater.from(B0_BeautyFileActivity.this).inflate(R.layout.include_circle_imgtext, null);
        plogo = (ImageView) headview.findViewById(R.id.circle_img);
        pname = (TextView) headview.findViewById(R.id.cirlce_title);
        ptime = (TextView) headview.findViewById(R.id.circle_detail);
        listView.addHeaderView(headview);
        getBeautyFilelast();
    }

    @Override
    protected void initData() {

    }


    private void initlayoutData() {
        groupkey.add("面部诊断");
        groupkey.add("身体诊断");
        groupkey.add("护理建议");

        List<String> aItemList = Arrays.asList("眼部问题", "T区问题", "两颊问题", "三角区问题", "颈部问题", "皮肤弹性");
        List<String> bItemList = Arrays.asList("头部症状", "面部症状", "肩颈脖子", "肺大肠", "心小肠", "胸部", "脾部", "肾膀胱", "肝胆");
        List<String> cItemList = Arrays.asList("面部描述", "身体描述", "本月护理建议", "家居建议", "备注", "本次护理重点");

        list.add("面部诊断");
        for (int i = 0; i < aItemList.size(); i++) {
            for (int j = 0; j < contentList.size(); j++) {
                if (aItemList.get(i).equals(contentList.get(j).getKey())) {
                    aList.add(contentList.get(j).getValue());
//                    list.add(contentList.get(j).getKey() + ":   " + contentList.get(j).getValue());
                }
            }
            if (aList.size() != 0){
                list.add(aItemList.get(i) + ":   " + format(aList));
            }
            aList.clear();

        }


        list.add("身体诊断");
        for (int i = 0; i < bItemList.size(); i++) {

            String bitem = bItemList.get(i);

            for (int j = 0; j < contentList.size(); j++) {
                if (bitem.equals(contentList.get(j).getKey())) {
//                    bList.add(contentList.get(j).getKey());
                    bList.add(contentList.get(j).getValue());
//                    list.add(contentList.get(j).getKey() + ":   " + contentList.get(j).getValue());
                }
            }
            if (bList.size() != 0){
                list.add(bitem + ":   " + format(bList));
            }
            bList.clear();
        }

        list.add("护理建议");
        for (int i = 0; i < cItemList.size(); i++) {

            String cItem = cItemList.get(i);
            for (int j = 0; j < contentList.size(); j++) {
                if (cItem.equals(contentList.get(j).getKey())) {
                    cList.add(contentList.get(j).getValue());
//                    list.add(contentList.get(j).getKey() + ":  " + contentList.get(j).getValue());
                }
            }
            if (cList.size() != 0){
                list.add(cItem + ":   " + format(cList));
            }
            cList.clear();
        }

        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private String format(List<String> alist) {

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < alist.size(); i++) {
            buffer.append(" ");
            buffer.append(alist.get(i));
        }
        return buffer.toString();
    }


    public void getBeautyFileDetail(String diagid) {

        RequestParams params = new RequestParams();
        params.put("diagid", diagid);

        MzbHttpClient.ClientTokenPost(B0_BeautyFileActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.detail, params, new NetCallBack(this) {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.get("code").equals(0)) {

                        JSONObject data = obj.getJSONObject("data");
                        BeautyFiledetailData beautyFiledetailData = gson.fromJson(data.toString(), BeautyFiledetailData.class);
                        imageLoader.displayImage(LoadImgUtil.loadbigImg(beautyFiledetailData.getLogo()), plogo);
                        pname.setText(beautyFiledetailData.getName());
                        ptime.setText("诊断日期：" + beautyFiledetailData.getTime());
                        contentArray = obj.getJSONObject("data").getJSONArray("contents");
                        if (contentArray != null) {
                            for (int i = 0; i < contentArray.length(); i++) {
                                JSONObject object = contentArray.getJSONObject(i);
                                BeautiFileContentData beautiFileContentData = gson.fromJson(object.toString(), BeautiFileContentData.class);
                                contentList.add(beautiFileContentData);
                            }
                        }

                        pictureArray = obj.getJSONObject("data").getJSONArray("pictures");
                        if (pictureArray != null) {
                            for (int i = 0; i < pictureArray.length(); i++) {
                                JSONObject object = pictureArray.getJSONObject(i);
//                                Log.v("tag",object.toString());
                                BeautiFilePictureData beautiFilePictureData = gson.fromJson(object.toString(), BeautiFilePictureData.class);
                                pictureList.add(beautiFilePictureData);
                            }
                        }
                        initlayoutData();

                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("请求失败");
            }
        });
    }


    @OnClick({R.id.titlebar_iv_left, R.id.titlebar_tv_right, R.id.order_btn})
    public void clickAction(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            //所有档案
            case R.id.titlebar_tv_right:
                startActivityForResult(new Intent(B0_BeautyFileActivity.this, B1_AllBeautyFileActivity.class), 0);
                break;
            //查看本次照片对比
            case R.id.order_btn:
                if (pictureList.size() != 0) {
                    Intent mIntent = new Intent(B0_BeautyFileActivity.this, B1_BeautyImgActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("picture", (Serializable) pictureList);
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                } else {
                    showToast("暂无上传图片");
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case -102:
                recordsData = (RecordsData) data.getSerializableExtra("recordsData");
                contentList.clear();
                pictureList.clear();
                list.clear();
                getBeautyFileDetail(recordsData.getDiagid());
        }

    }

    public void getBeautyFilelast() {

        RequestParams params = new RequestParams();
        params.put("uid", Config.getCachedUid(B0_BeautyFileActivity.this));

        MzbHttpClient.ClientTokenPost(B0_BeautyFileActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.diagnosis_last, params, new NetCallBack(this) {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.get("code").equals(0)) {
                        JSONObject data = obj.getJSONObject("data");
                        BeautyFiledetailData beautyFiledetailData = gson.fromJson(data.toString(), BeautyFiledetailData.class);


                        imageLoader.displayImage(LoadImgUtil.loadbigImg(beautyFiledetailData.getLogo()), plogo);
                        pname.setText(beautyFiledetailData.getName());
                        ptime.setText("诊断日期：" + beautyFiledetailData.getTime());
                        contentArray = obj.getJSONObject("data").getJSONArray("contents");
                        if (contentArray != null) {
                            for (int i = 0; i < contentArray.length(); i++) {
                                JSONObject object = contentArray.getJSONObject(i);
                                BeautiFileContentData beautiFileContentData = gson.fromJson(object.toString(), BeautiFileContentData.class);
                                contentList.add(beautiFileContentData);
                            }
                        }

                        pictureArray = obj.getJSONObject("data").getJSONArray("pictures");
                        for (int i = 0; i < pictureArray.length(); i++) {
                            JSONObject object = pictureArray.getJSONObject(i);
                            BeautiFilePictureData beautiFilePictureData = gson.fromJson(object.toString(), BeautiFilePictureData.class);
                            pictureList.add(beautiFilePictureData);
                        }
                        initlayoutData();

                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("请求失败");
            }
        });
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = convertView;
            if (groupkey.contains(getItem(position))) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.beautyfile_tag, null);
                TextView tagtext = (TextView) view.findViewById(R.id.tag_text);
                tagtext.setText((CharSequence) getItem(position));
            } else {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.beautyfile_item, null);
                TextView text = (TextView) view.findViewById(R.id.addexam_list_item_text);
                text.setText((CharSequence) getItem(position));
                String str=((CharSequence)getItem(position)).toString();
                //判断是否有:,即是否需要变色)
                int index=str.indexOf(":");
                if (index != (-1)){
                    //找到:后面部分的文字
                    String temp_str=str.substring(index+1);
                    SpannableString ss=new SpannableString(str);
                    ss.setSpan(new CharacterStyle() {
                        @Override
                        public void updateDrawState(TextPaint tp) {
                            //更改字体颜色
                            tp.setColor(Color.parseColor("#808080"));
                        }
                    }, str.indexOf(temp_str), str.indexOf(temp_str)+temp_str.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setText(ss);
                }
            }
            return view;
        }

        @Override
        public boolean isEnabled(int position) {
            // TODO Auto-generated method stub
            if (groupkey.contains(getItem(position))) {
                return false;
            }
            return super.isEnabled(position);
        }
    }
}
