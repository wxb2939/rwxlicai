package com.xem.mzbcustomerapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.ChildItem;
import com.xem.mzbcustomerapp.entity.ProjectCateData;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/11/5.
 */
public class B2_SelectXmActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView titlebar_iv_left;
    @InjectView(R.id.list)
    ExpandableListView expandableList;


    private int the_group_expand_position = -1;
    private int count_expand = 0;
    private int resultCode = -104;
    ExpandableAdapter expandAdapter;

    private Map<Integer, Integer> ids = new HashMap<Integer, Integer>();
    private List<ProjectCateData> father_data = new ArrayList<>();
    private List<GroupItem> data = new ArrayList<>();
    private List<ChildItem> child_data = new ArrayList<>();
    private List<ChildItem> select_data = new ArrayList<>();


    private class GroupItem {
        public String ppid;
        public String cateid;
        public String name;
        public String count;
        public List<ChildItem> children;
    }

    /*private class ChildItem{
        public String name;
        public String projectid;
        public boolean pic;
        public String price;
        public boolean isSelected = false;
    }*/


    @Override
    protected void initView() {

        setContentView(R.layout.b2_selectxm_activity);
        new TitleBuilder(this).setTitleText("选择项目").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        projectCates();
        initViewLayout();
    }

    @Override
    protected void initData() {

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


    private void initDataLayout() {
        for (int i = 0; i < father_data.size(); i++) {
            GroupItem groupItem = new GroupItem();
            groupItem.name = father_data.get(i).getName();
            groupItem.children = child_data;
            groupItem.children = new ArrayList<>();
            if (child_data.size() == 0) {
                ChildItem child = new ChildItem();
                child.name = "暂无";
                groupItem.children.add(child);
            } else {
                for (int j = 0; j < child_data.size(); j++) {
                    ChildItem child = new ChildItem();
                    child.name = child_data.get(j).name;
//                    child.projectid = child_data.get(j).projectid;
                    groupItem.children.add(child);
                }
            }
            data.add(groupItem);
        }

        expandAdapter = new ExpandableAdapter(B2_SelectXmActivity.this);
        expandableList.setAdapter(expandAdapter);
        expandableList.setGroupIndicator(null);
//        initViewLayout();
    }


    public void initViewLayout() {
        /**
         * 监听父节点打开的事件
         */
        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                pprojects(groupPosition, Config.getCachedPpid(B2_SelectXmActivity.this), father_data.get(groupPosition).getCateid());
                expandAdapter.notifyDataSetChanged();
                the_group_expand_position = groupPosition;
                ids.put(groupPosition, groupPosition);
                count_expand = ids.size();
//                System.out.println("isExpanded ==========" + groupPosition);
            }
        });
        /**
         * 监听父节点关闭的事件
         */
        expandableList
                .setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                    @Override
                    public void onGroupCollapse(int groupPosition) {

                        ids.remove(groupPosition);
                        expandableList.setSelectedGroup(groupPosition);
                        count_expand = ids.size();

                    }
                });

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                System.out.println("isExpanded i==" + groupPosition + " i1==" + childPosition);

//                data.get(groupPosition).children.get(childPosition).idselected = false;
                ChildItem item = data.get(groupPosition).children.get(childPosition);
                item.isSelected = !item.isSelected;
                if (item.isSelected)
                {

                    if (select_data.size()<5){
                        select_data.add(item);
                    }else {
                        showToast("预约项目最多不超过5个");
                        item.isSelected = !item.isSelected;
                    }
                }else
                {
                    select_data.remove(item);
                }
                expandAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }



    //获取项目分类
    private void projectCates() {
        RequestParams params = new RequestParams();
        params.put("ppid", Config.getCachedPpid(B2_SelectXmActivity.this));

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.project_cates, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 1; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ProjectCateData projectCateData = gson.fromJson(object.toString(), ProjectCateData.class);
                            father_data.add(projectCateData);
                        }
                        initDataLayout();

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


    private GroupItem getGroupItem(int position)
    {
        GroupItem groupItem = data.get(position);
        if (groupItem == null)
            groupItem = new GroupItem();
        groupItem.name = father_data.get(position).getName();
        return groupItem;
    }
    //获取项目
    private void pprojects(final int position,String ppid, String cateid) {
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
                        child_data.clear();
                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
//                            Log.v("tag",object.toString());
                            ChildItem childItem = gson.fromJson(object.toString(), ChildItem.class);
                            child_data.add(childItem);
                        }

                        GroupItem groupItem = getGroupItem(position);
                        groupItem.children = new ArrayList<>();
                        if (child_data.size() == 0) {
                            ChildItem child = new ChildItem();
                            child.name = "暂无";
                            groupItem.children.add(child);
                        } else {
                            for (int j = 0; j < child_data.size(); j++) {
                                ChildItem child = new ChildItem();
                                child.name = child_data.get(j).name;
                                child.projectid = child_data.get(j).projectid;
                                groupItem.children.add(child);
                            }
                        }
                        expandAdapter.notifyDataSetChanged();
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


    class ExpandableAdapter extends BaseExpandableListAdapter {
        B2_SelectXmActivity exlistview;
        @SuppressWarnings("unused")
        private int mHideGroupPos = -1;

        public ExpandableAdapter(B2_SelectXmActivity elv) {
            super();
            exlistview = elv;
        }

        public Object getChild(int groupPosition, int childPosition) {
            return data.get(groupPosition).children.get(childPosition);
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return data.get(groupPosition).children.size();
        }

        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            ChildItem child = data.get(groupPosition).children.get(childPosition);
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.xm_childitem, null);
            }
            final TextView title = (TextView) view.findViewById(R.id.child_text);
            final ImageView imageView = (ImageView) view.findViewById(R.id.mls_good);
            title.setText(child.name);
            if (select_data.contains(data.get(groupPosition).children.get(childPosition))){
                imageView.setVisibility(View.VISIBLE);
            }else {
                imageView.setVisibility(View.INVISIBLE);
            }
            return view;
        }

        @Override
        public void onGroupCollapsed(int groupPosition) {
            super.onGroupCollapsed(groupPosition);
        }

        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflaterGroup = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterGroup.inflate(R.layout.groupitem, null);
            }

            GroupItem group = data.get(groupPosition);

            TextView title = (TextView) view.findViewById(R.id.content_001);
            title.setText(group.name);
            ImageView image = (ImageView) view.findViewById(R.id.tubiao);
//            System.out.println("isExpanded----->" + isExpanded);
            if (isExpanded) {
                image.setBackgroundResource(R.mipmap.btn_browser2);
            } else {
                image.setBackgroundResource(R.mipmap.btn_browser);
            }
            return view;
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public Object getGroup(int groupPosition) {
            return data.get(groupPosition);
        }

        public int getGroupCount() {
            return data.size();
        }

        public boolean hasStableIds() {
            return true;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public void hideGroup(int groupPos) {
            mHideGroupPos = groupPos;
        }
    }

}
