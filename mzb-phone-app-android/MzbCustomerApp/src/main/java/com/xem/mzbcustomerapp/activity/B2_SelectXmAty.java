package com.xem.mzbcustomerapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.adapter.SelectXmGroupAdapter;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.GroupItem;
import com.xem.mzbcustomerapp.entity.PprojectData;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.view.MyListView;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/11/27.
 */
public class B2_SelectXmAty extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView titlebar_iv_left;
    @InjectView(R.id.listView)
    MyListView listView;
    @InjectView(R.id.subListView)
    MyListView subListView;


    private List<GroupItem> groupItem = new ArrayList<>();
    private List<PprojectData> select_data = new ArrayList<>();

    private int num = 0;

    private GroupItem groupSelected = null;
    private SelectXmGroupAdapter adapter;
    private SelectXmChildAdapter subAdapter;
    private int resultCode = -104;

    @Override
    protected void initView() {
        setContentView(R.layout.b2_selectxm_aty);
        new TitleBuilder(this).setTitleText("选择项目").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        adapter = new SelectXmGroupAdapter(getApplicationContext(), groupItem);
        listView.setAdapter(adapter);
        adapter.setSelectedPosition(0);
        adapter.notifyDataSetInvalidated();
        Pprjacc();

    }

    @Override
    protected void initData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetInvalidated();
                final GroupItem cate = groupItem.get(position);
                subAdapter = new SelectXmChildAdapter(getApplicationContext(), cate.getChildren());
                subListView.setAdapter(subAdapter);
                List<PprojectData> pd = cate.getChildren();

                if (pd.size() == 0 && position != 0) {
                    pprojects(position, cate.getPpid(), cate.getCateid());
                }
                groupSelected = cate;
            }
        });

        subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                PprojectData item = groupSelected.getChildren().get(position);
                item.isSelected = !item.isSelected;
                int i = groupSelected.getSelNum();
                if (item.isSelected) {
                    if (select_data.size() < 5 && i < 5) {
                        select_data.add(item);
                        groupSelected.setSelNum(++i);
                    } else {
                        showToast("预约项目最多不超过5个");
                        item.isSelected = !item.isSelected;
                    }

                } else {
                    select_data.remove(item);
                    groupSelected.setSelNum(--i);
                }
                adapter.notifyDataSetChanged();
                subAdapter.notifyDataSetChanged();
            }
        });
    }


    @OnClick({R.id.titlebar_iv_left})
    public void clickAction(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("xm", (Serializable) select_data);
                intent.putExtras(bundle);
                setResult(resultCode, intent);
                finish();
                break;
            default:
                break;
        }
    }


    //获取项目分类
    private void projectCates() {
        RequestParams params = new RequestParams();
        params.put("ppid", Config.getCachedPpid(B2_SelectXmAty.this));

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.project_cates, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            GroupItem gItem = gson.fromJson(object.toString(), GroupItem.class);
                            groupItem.add(gItem);
                        }
//                        pprojects(0, groupSelected.getPpid(), groupSelected.getCateid());
                        adapter.notifyDataSetChanged();
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("");
            }
        });
    }


    //获取已有项目
    private void Pprjacc() {
        RequestParams params = new RequestParams();
        params.put("ppid", Config.getCachedPpid(B2_SelectXmAty.this));
        params.put("custid", Config.getCachedCustid(B2_SelectXmAty.this));

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.prjacc, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        List<PprojectData> children = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            PprojectData pprojectData = gson.fromJson(object.toString(), PprojectData.class);
                            children.add(pprojectData);
                        }
                        GroupItem group = new GroupItem();
                        group.setName("已有项目");
                        group.setChildren(children);
                        groupItem.add(0, group);
                        groupSelected = groupItem.get(0);
                        projectCates();
                        subAdapter = new SelectXmChildAdapter(getApplicationContext(), children);
                        subListView.setAdapter(subAdapter);
                        subAdapter.notifyDataSetChanged();
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("");
            }
        });
    }


    //获取项目
    private void pprojects(final int position, String ppid, String cateid) {
        RequestParams params = new RequestParams();
        params.put("ppid", ppid);
        params.put("cateid", cateid);
        params.put("begin", 0 + "");
        params.put("count", 20 + "");
        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.Pprojects, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        GroupItem group = groupItem.get(position);
                        List<PprojectData> children = group.getChildren();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            PprojectData pprojectData = gson.fromJson(object.toString(), PprojectData.class);
                            children.add(pprojectData);
                        }
                        subAdapter = new SelectXmChildAdapter(getApplicationContext(), children);
                        subListView.setAdapter(subAdapter);
                        subAdapter.notifyDataSetChanged();
                    } else {
                        showToast(obj.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onMzbFailues(Throwable arg0) {
                showToast("");
            }
        });
    }


    public class SelectXmChildAdapter extends BaseAdapter {
        Context context;
        LayoutInflater layoutInflater;
        List<PprojectData> subCates;

        public SelectXmChildAdapter(Context context, List<PprojectData> subCates) {
            this.context = context;
            this.subCates = subCates;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return subCates.size();
        }

        @Override
        public Object getItem(int position) {
            return getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.sublist_item, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.textview1);
                viewHolder.img_select = (ImageView) convertView.findViewById(R.id.img_select);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(subCates.get(position).getName());
            viewHolder.textView.setTextColor(Color.BLACK);
            if (select_data.contains(groupSelected.getChildren().get(position))) {
                viewHolder.img_select.setVisibility(View.VISIBLE);
            } else {
                viewHolder.img_select.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }

        public class ViewHolder {
            public TextView textView;
            public ImageView img_select;
        }
    }
}
