package com.xem.mzbcustomerapp.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.AccountsData;
import com.xem.mzbcustomerapp.entity.BranchData;
import com.xem.mzbcustomerapp.entity.CoupdetialData;
import com.xem.mzbcustomerapp.entity.PdtdetailData;
import com.xem.mzbcustomerapp.entity.PrjdetailData;
import com.xem.mzbcustomerapp.entity.PstdetailData;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/24.
 */
public class B0_MyAccountActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView titlebar_iv_left;
    @InjectView(R.id.titlebar_iv_right)
    ImageView titlebar_iv_right;
    @InjectView(R.id.list)
    ExpandableListView expandableList;

    ExpandableAdapter expandAdapter;

    private int the_group_expand_position = -1;
    private int count_expand = 0;


    private List<PstdetailData> pstdetail;
    private List<PrjdetailData> prjdetail;
    private List<PdtdetailData> pdtdetail;
    private List<CoupdetialData> coupdetial;

    private ImageView plogo;//品牌Logo
    private TextView pname;//品牌名称
    private TextView name;//会员名称
    private TextView deposit;//储值账户余额
    private TextView debt;//欠款帐户


    private List<GroupItem> data = new ArrayList<GroupItem>();
    private Map<Integer, Integer> ids = new HashMap<Integer, Integer>();
    private BranchData branchData;

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
        PRESENT,
        PROJECT,
        PRODUCT,
        COUPON
    }

    @Override
    protected void initView() {
        setContentView(R.layout.b0_myaccount_activity);
        new TitleBuilder(this).setTitleText("我的账户").setLeftImage(R.mipmap.top_view_back).setRightText("切换门店");
        ButterKnife.inject(this);

        if (Config.getCachedBrandid(B0_MyAccountActivity.this) == null) {
            showToast("请先绑定客户档案，谢谢");
        } else {
            GetClientProperty(Config.getCachedPpid(B0_MyAccountActivity.this), Config.getCachedCustid(B0_MyAccountActivity.this));
        }

        View headview = LayoutInflater.from(B0_MyAccountActivity.this).inflate(R.layout.myaccount_headview, null);
        plogo = (ImageView) headview.findViewById(R.id.cell_img);
        pname = (TextView) headview.findViewById(R.id.cell_text);
        name = (TextView) headview.findViewById(R.id.cell_text_two);
        deposit = (TextView) headview.findViewById(R.id.deposit);
        debt = (TextView) headview.findViewById(R.id.debt);

        expandAdapter = new ExpandableAdapter(B0_MyAccountActivity.this);
        expandableList.addHeaderView(headview);
        expandableList.setAdapter(expandAdapter);
        expandableList.setGroupIndicator(null);
        initViewLayout();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.titlebar_iv_left, R.id.titlebar_tv_right})
    public void clickAction(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.titlebar_tv_right:
                Intent store = new Intent(B0_MyAccountActivity.this, B1_StoreListActivity.class);
                startActivityForResult(store, 0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case -100:
                branchData = (BranchData) data.getSerializableExtra("branchData");
                if (branchData != null) {
//                    imageLoader.displayImage(LoadImgUtil.loadbigImg(branchData.getLogo()), plogo);
//                    pname.setText(branchData.getName());
                    Config.cachedPpid(B0_MyAccountActivity.this, branchData.getPpid());
                    Config.cachedCustid(B0_MyAccountActivity.this, branchData.getCustid());
                    Config.cachedBrandid(B0_MyAccountActivity.this, branchData.getBranid());
                    GetClientProperty(branchData.getPpid(), branchData.getCustid());
//                    name.setText("地址:" + branchData.getAddress());
                }
                break;
        }
    }


    private void initDataLayout() {


        // 赠金
        GroupItem groupItem = new GroupItem();
        groupItem.name = "赠金";

        groupItem.children = new ArrayList<ChildItem>();
        if (pstdetail.size() == 0) {
            ChildItem child = new ChildItem();
            child.name = "暂无";
            child.count = "0";
            child.itemPresent = false;
            child.edate = "";
            child.type = DataType.PROJECT;
            groupItem.children.add(child);
        } else {
            for (int i = 0; i < pstdetail.size(); i++) {

                ChildItem child = new ChildItem();

                child.name = pstdetail.get(i).getAmount();
//                child.count = pstdetail.get(i).getCount().toString();
//                child.itemPresent = pstdetail.get(i).getPresent();
                child.edate = pstdetail.get(i).getEdate();
                groupItem.children.add(child);
            }
        }
        data.add(groupItem);

        // 项目余量
        groupItem = new GroupItem();
        groupItem.name = "疗程账户";

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
                child.edate=prjdetail.get(i).edate;
                child.type = DataType.PROJECT;
                groupItem.children.add(child);
            }
        }
        data.add(groupItem);


        // 产品余量
        groupItem = new GroupItem();
        groupItem.name = "寄存产品";
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
        groupItem.name = "品牌优惠券";
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

        MzbHttpClient.ClientTokenPost(B0_MyAccountActivity.this, MzbUrlFactory.BASE_URL + MzbUrlFactory.accounts, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.get("code").equals(0)) {
                        data.clear();
                        JSONObject data = obj.getJSONObject("data");
                        AccountsData accountsData = gson.fromJson(data.toString(), AccountsData.class);
                        imageLoader.displayImage(accountsData.getPlogo(), plogo);
                        pname.setText(accountsData.getPname());
                        name.setText(accountsData.getName());
                        deposit.setText(accountsData.getDeposit().toString());
//                        present.setText(accountsData.getPresent().toString());
                        debt.setText(accountsData.getDebt().toString());
                        pstdetail = accountsData.getPstdetail();
                        prjdetail = accountsData.getPrjdetail();
                        pdtdetail = accountsData.getPdtdetail();
                        coupdetial = accountsData.getCoupdetial();
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
                showToast("请求失败");
            }
        });
    }


    public void initViewLayout() {
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


    class ExpandableAdapter extends BaseExpandableListAdapter {
        B0_MyAccountActivity exlistview;
        @SuppressWarnings("unused")
        private int mHideGroupPos = -1;

        public ExpandableAdapter(B0_MyAccountActivity elv) {
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
                view = inflater.inflate(R.layout.child_item, null);
            }
            final ImageView title = (ImageView) view.findViewById(R.id.child_text);
            final TextView tvnum = (TextView) view.findViewById(R.id.child_num);
            final TextView tvlog = (TextView) view.findViewById(R.id.child_log);
//            title.setText(child.name);
            if (child.itemPresent){
                title.setVisibility(View.VISIBLE);
            }else{
                title.setVisibility(View.INVISIBLE);
            }
            if (child.count != null && !child.count.equals("0")){
                tvlog.setText(child.name+"*"+child.count);
                if (tvlog.getText().toString().equals(child.name+"*")){
                    tvlog.setText(child.name+"*"+1);
                }
            }else{
                tvlog.setText(child.name);
            }
            if (isValidDate(child.edate)){
                tvnum.setText("有效期至:"+child.edate);
            }else{
                tvnum.setText(child.edate);
            }
            return view;
        }


        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflaterGroup = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflaterGroup.inflate(R.layout.groupitem, null);
            }
            ImageView ImageView01 = (ImageView) view.findViewById(R.id.ImageView01);
            GroupItem group = data.get(groupPosition);
            String str = group.name;
            if (str.equals("赠金")) {
                ImageView01.setImageResource(R.mipmap.zj);
//                ImageView01.setBackgroundResource(R.mipmap.zj);
            } else if (str.equals("疗程账户")) {
                ImageView01.setImageResource(R.mipmap.lc);
//                ImageView01.setImageResource(R.mipmap.lc);
            } else if (str.equals("寄存产品")) {
                ImageView01.setImageResource(R.mipmap.jc);
//                ImageView01.setImageResource(R.mipmap.jc);
            } else if (str.equals("品牌优惠券")) {
                ImageView01.setImageResource(R.mipmap.yhq);
//                ImageView01.setImageResource(R.mipmap.yhq);
            }
            TextView title = (TextView) view.findViewById(R.id.content_001);
            title.setText(group.name);
            ImageView image = (ImageView) view.findViewById(R.id.tubiao);
            System.out.println("isExpanded----->" + isExpanded);
            if (isExpanded) {
                flag = true;
                Animation animation_rotate = new RotateAnimation(0, 90, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                animation_rotate.setRepeatCount(0);
                animation_rotate.setDuration(10);
                animation_rotate.setFillAfter(true);
                image.startAnimation(animation_rotate);
            } else {
                Animation animation_rotate = new RotateAnimation(0, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                animation_rotate.setRepeatCount(0);
                animation_rotate.setDuration(10);
                animation_rotate.setFillAfter(true);
                if (flag) {
                    image.startAnimation(animation_rotate);
                } else {
                    image.setBackgroundResource(R.mipmap.next_arrow);
                }
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

    boolean flag = false;

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

}
