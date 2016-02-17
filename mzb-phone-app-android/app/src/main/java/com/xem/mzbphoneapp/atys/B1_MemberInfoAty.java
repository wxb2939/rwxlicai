package com.xem.mzbphoneapp.atys;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbphoneapp.R;
import com.xem.mzbphoneapp.entity.AccountsData;
import com.xem.mzbphoneapp.entity.CoupdetialData;
import com.xem.mzbphoneapp.entity.PdtdetailData;
import com.xem.mzbphoneapp.entity.PrjdetailData;
import com.xem.mzbphoneapp.utils.Config;
import com.xem.mzbphoneapp.utils.MzbUrlFactory;
import com.xem.mzbphoneapp.utils.NetCallBack;
import com.xem.mzbphoneapp.utils.RequestUtils;
import com.xem.mzbphoneapp.utils.TitleBuilder;
import com.xem.mzbphoneapp.view.MzbProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.xem.mzbphoneapp.R.layout.ground_nonet_back;

/**
 * Created by xuebing on 15/7/11.
 */


public class B1_MemberInfoAty extends MzbActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.ground)
    RelativeLayout ground;

    private ImageView plogo;
    private TextView pname;
    private TextView deposit;
    private TextView present;
    private TextView debt;
    private TextView level;


    List<GroupItem> data = new ArrayList<GroupItem>();

    private class GroupItem {
        public String name;
        public List<ChildItem> children;
    }

    private class ChildItem {
        public String name;
        public String count;
        public boolean itemPresent;
        public String edate;
        public DataType type;
    }

    private enum DataType {
        PROJECT,
        PRODUCT,
        COUPON
    }

    ExpandableAdapter expandAdapter;
    ExpandableListView expandableList;

    private int the_group_expand_position = -1;
    private int count_expand = 0;

    private List<PrjdetailData> prjdetail;
    private List<PdtdetailData> pdtdetail;
    private List<CoupdetialData> coupdetial;



    private Map<Integer, Integer> ids = new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_info_aty2);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("会员信息").setLeftImage(R.mipmap.top_view_back);

        if (application.getBranchData().equals(null)) {
            showToast("请先绑定客户档案，谢谢");
        } else {
            GetClientProperty(application.getBranchData().getPpid() + "", application.getBranchData().getCustid() + "");
        }
        expandAdapter = new ExpandableAdapter(B1_MemberInfoAty.this);
        expandableList = (ExpandableListView) findViewById(R.id.list);

        View headview = LayoutInflater.from(B1_MemberInfoAty.this).inflate(R.layout.headview, null);
        plogo = (ImageView) headview.findViewById(R.id.plogo);
        pname = (TextView) headview.findViewById(R.id.pname);
        deposit = (TextView) headview.findViewById(R.id.deposit);
        present = (TextView) headview.findViewById(R.id.present);
        debt = (TextView) headview.findViewById(R.id.debt);
        level = (TextView) headview.findViewById(R.id.level);

        expandableList.addHeaderView(headview);
        expandableList.setAdapter(expandAdapter);
        expandableList.setGroupIndicator(null);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkNetAddToast()){
            View grounds = View.inflate(this, ground_nonet_back,null);
            ground.addView(grounds);
        }
    }

    @OnClick({R.id.titlebar_iv_left})
    public void Todo(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }

    public void initView() {
        /**
         * 监听父节点打开的事件
         */
        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                the_group_expand_position = groupPosition;
                ids.put(groupPosition, groupPosition);
                count_expand = ids.size();
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
    }


    private void initData() {

        // 项目余量
        GroupItem groupItem = new GroupItem();
        groupItem.name = "疗程账户明细";

        groupItem.children = new ArrayList<ChildItem>();
        if (prjdetail.size() == 0) {
            ChildItem child = new ChildItem();
            child.name = "暂无";
            child.count = "0";
            child.itemPresent = false;
            child.edate = "";
            child.type = DataType.PROJECT;
            groupItem.children.add(child);
        } else {
            for (int i = 0; i < prjdetail.size(); i++) {

                ChildItem child = new ChildItem();

                child.name = prjdetail.get(i).getName();
                child.count = prjdetail.get(i).getCount().toString();
                child.itemPresent = prjdetail.get(i).getPresent();
                child.edate = "";
                child.type = DataType.PROJECT;
                groupItem.children.add(child);
            }
        }
        data.add(groupItem);


        // 产品余量
        groupItem = new GroupItem();
        groupItem.name = "产品明细";
        groupItem.children = new ArrayList<ChildItem>();
        if (pdtdetail.size() == 0) {
            ChildItem child = new ChildItem();
            child.name = "暂无";
            child.count = "0";
            child.itemPresent = false;
            child.edate = "";
            child.type = DataType.PRODUCT;
            groupItem.children.add(child);
        } else {
            for (int i = 0; i < pdtdetail.size(); i++) {
                ChildItem child = new ChildItem();
                child.name = pdtdetail.get(i).getName();
                child.count = pdtdetail.get(i).getCount().toString();
                child.itemPresent = pdtdetail.get(i).getPresent();
                child.edate = "";
                child.type = DataType.PRODUCT;
                groupItem.children.add(child);
            }
        }
        data.add(groupItem);

        groupItem = new GroupItem();
        groupItem.name = "优惠券明细";
        groupItem.children = new ArrayList<ChildItem>();

        if (coupdetial.size() == 0) {
            ChildItem child = new ChildItem();
            child.name = "暂无";
            child.count = "0";
            child.edate = "";
            groupItem.children.add(child);
            child.type = DataType.COUPON;
//            data.add(groupItem);
        } else {
            for (int i = 0; i < coupdetial.size(); i++) {
                ChildItem child = new ChildItem();
                child.name = coupdetial.get(i).getName();
                child.count = "";
                child.edate = coupdetial.get(i).getEdate();
                child.type = DataType.COUPON;
                groupItem.children.add(child);
            }
        }
        data.add(groupItem);
    }


    public void GetClientProperty(String ppid, String custid) {

        RequestParams params = new RequestParams();
        params.put("ppid", ppid);
        params.put("custid", custid);

        RequestUtils.ClientTokenPost(B1_MemberInfoAty.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.BRAND_ACCOUNTS, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.get("code").equals(0)) {
                        JSONObject data = obj.getJSONObject("data");

                        AccountsData accountsData = gson.fromJson(data.toString(), AccountsData.class);

                        imageLoader.displayImage(accountsData.getPlogo(), plogo);
                        pname.setText(accountsData.getPname());
                        deposit.setText(accountsData.getDeposit().toString());
                        present.setText(accountsData.getPresent().toString());
                        debt.setText(accountsData.getDebt().toString());
                        prjdetail = accountsData.getPrjdetail();
                        pdtdetail = accountsData.getPdtdetail();
                        coupdetial = accountsData.getCoupdetial();
                        initData();
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


    class ExpandableAdapter extends BaseExpandableListAdapter {
        B1_MemberInfoAty exlistview;
        @SuppressWarnings("unused")
        private int mHideGroupPos = -1;

        public ExpandableAdapter(B1_MemberInfoAty elv) {
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
                view = inflater.inflate(R.layout.childitem, null);
            }
            final TextView title = (TextView) view.findViewById(R.id.child_text);
            final TextView tvnum = (TextView) view.findViewById(R.id.child_num);
            final TextView tvlog = (TextView) view.findViewById(R.id.child_log);
            title.setText(child.name);
            tvnum.setText(child.count);
            tvlog.setText(child.edate);
            return view;
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
//            title.setText(getGroup(groupPosition).toString());
            title.setText(group.name);
            ImageView image = (ImageView) view.findViewById(R.id.tubiao);

            System.out.println("isExpanded----->" + isExpanded);
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
            return false;
        }

        public void hideGroup(int groupPos) {
            mHideGroupPos = groupPos;
        }
    }
}
