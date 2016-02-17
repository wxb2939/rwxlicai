package com.xem.mzbcustomerapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.BranchData;
import com.xem.mzbcustomerapp.entity.BrandinfoData;
import com.xem.mzbcustomerapp.entity.GoodsCateData;
import com.xem.mzbcustomerapp.entity.GoodsSubCateData;
import com.xem.mzbcustomerapp.entity.GroupsData;
import com.xem.mzbcustomerapp.entity.PcardsData;
import com.xem.mzbcustomerapp.entity.ProcardData;
import com.xem.mzbcustomerapp.entity.ProductData;
import com.xem.mzbcustomerapp.entity.ProgroupsData;
import com.xem.mzbcustomerapp.entity.ProjectCateData;
import com.xem.mzbcustomerapp.entity.ProjectsData;
import com.xem.mzbcustomerapp.entity.ProproductData;
import com.xem.mzbcustomerapp.entity.ProprojectsData;
import com.xem.mzbcustomerapp.enums.GoodsType;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.utils.LoadImgUtil;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/10/23.
 */
public class B0_MyStoreActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.titlebar_tv_right)
    TextView more;
    @InjectView(R.id.list)
    PullToRefreshListView listview;

    private TextView brand_detail;
    private TextView store_phone;
    private TextView first_title;
    private TextView second_title;
    private View entry_storelist;
    private ImageView cell_img;
    private TextView cell_text;
    private TextView cell_text_two;


    private List<GoodsData> data;
    private String uri;
    private MyAdapter adapter;
    private BranchData branchData;
    private GoodsCateData nodes;
    private GoodsSubCateData goodsSubCateData;
    private View headview;
    private String tel_num;

    private ProjectCateData projectCateData;
    private List<GoodsSubCateData> subNodes = new ArrayList<>();

    @Override
    protected void initView() {
        setContentView(R.layout.b0_mystore_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("我的门店").setLeftImage(R.mipmap.top_view_back).setRightText("切换门店");
        headview =getLayoutInflater().from(B0_MyStoreActivity.this).inflate(R.layout.mystore_headview,null);
        cell_img = (ImageView) headview.findViewById(R.id.cell_img);
        cell_text = (TextView) headview.findViewById(R.id.cell_text);
        cell_text_two = (TextView) headview.findViewById(R.id.cell_text_two);
        brand_detail = (TextView) headview.findViewById(R.id.brand_detail);

        store_phone = (TextView) headview.findViewById(R.id.store_phone);
        first_title = (TextView) headview.findViewById(R.id.first_title);
        second_title = (TextView) headview.findViewById(R.id.second_title);
        entry_storelist = (View) headview.findViewById(R.id.entry_storelist);

        entry_storelist.setOnClickListener(new MyClickListener());
        store_phone.setOnClickListener(new MyClickListener());
        brand_detail.setOnClickListener(new MyClickListener());

        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        headview.setLayoutParams(layoutParams);
        ListView lv = listview.getRefreshableView();
        lv.addHeaderView(headview,null,false);
        data = new ArrayList<>();
        adapter = new MyAdapter(B0_MyStoreActivity.this);
        listview.setAdapter(adapter);
    }


    @Override
    protected void initData() {
        String ppid = Config.getCachedPpid(B0_MyStoreActivity.this);
        String branid = Config.getCachedBrandid(B0_MyStoreActivity.this);
        String cateid = Config.getCachedCateid(B0_MyStoreActivity.this);
        if (ppid != null && branid != null){
            brandInfo(ppid, branid);
        }
        if (ppid != null && cateid != null){
            projectCates(ppid);
        }

        //加载更多
        listview.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                    page+=20;
                if (nodes != null && goodsSubCateData!= null){
                    refreshData(nodes, goodsSubCateData);
                }

            }
        });

//        //下拉刷新
//        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                data.clear();
//                page=0;
//                if (nodes != null && goodsSubCateData!= null){
//                    refreshData(nodes, goodsSubCateData);
//                }
//            }
//        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(B0_MyStoreActivity.this, B1_GoodsDetailActivity.class);
                GoodsData goodsdata = (GoodsData)view.getTag();
                mIntent.putExtra("id",goodsdata.targetId);
                mIntent.putExtra("type",goodsdata.targeType.toString());
                mIntent.putExtra("hasfree",goodsdata.hasFree);
                mIntent.putExtra("name",goodsdata.targetName);
                startActivity(mIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.entry_storelist:
                    Intent mIntent = new Intent(B0_MyStoreActivity.this,B1_StoreProductActivity.class);
                    startActivityForResult(mIntent, 0);
                    break;
                case R.id.store_phone:

                    AlertDialog.Builder builder=new AlertDialog.Builder(B0_MyStoreActivity.this);
                    builder.setTitle("拨打电话");
                    builder.setMessage("是否呼叫?");
                    builder.setPositiveButton("呼叫",new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel_num));
                            startActivity(tel);
                        }
                    });
                    builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                    break;
                case R.id.brand_detail:
                    intent2Aty(B1_BrandDetailActivity.class);
                    break;
                default:
                    break;
            }
        }
    }


    @OnClick({R.id.titlebar_iv_left,R.id.titlebar_tv_right})
    public void clickActon(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.titlebar_tv_right:
                Intent store = new Intent(B0_MyStoreActivity.this, B1_StoreListActivity.class);
                startActivityForResult(store, 0);
                break;
            default:
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case -100:
//                branchData = (BranchData) data.getSerializableExtra("branchData");
//                if (branchData != null) {
//                    Config.cachedPpid(B0_MyStoreActivity.this,branchData.getPpid());
//                    brandInfo(branchData.getPpid(), branchData.getBranid());
//                    pprojects(branchData.getPpid(),branchData.getCustid());
//                }
                this.data.clear();
                page=0;
                changeStore(data);
                break;
            case -101:
                this.data.clear();
                page=0;
                nodes = (GoodsCateData) data.getSerializableExtra("title");
                goodsSubCateData = (GoodsSubCateData) data.getSerializableExtra("subtitle");
                refreshData(nodes, goodsSubCateData);
                break;
            default:
                break;
        }
    }




    //切换门店
    private void changeStore(Intent data){
        branchData = (BranchData) data.getSerializableExtra("branchData");
        if (branchData != null) {
            Config.cachedPpid(B0_MyStoreActivity.this, branchData.getPpid());
            Config.cachedBrandid(B0_MyStoreActivity.this, branchData.getBranid());
            Config.cachedCustid(B0_MyStoreActivity.this, branchData.getCustid());
            brandInfo(branchData.getPpid(), branchData.getBranid());
            projectCates(branchData.getPpid());
        }
    }



    //刷新数据
    private void refreshData(final GoodsCateData nodes,GoodsSubCateData goodsSubCateData){
        if (nodes.getIsPromotion()){
            switch (goodsSubCateData.getType()){
                case ProProduct:
                    proproducts(nodes.getId());
                    first_title.setText(nodes.getName());
                    second_title.setText(goodsSubCateData.getName());
                    break;

                case ProProject:
                    proprojects(nodes.getId());
                    first_title.setText(nodes.getName());
                    second_title.setText(goodsSubCateData.getName());
                    break;
                case ProCard:
                    procards(nodes.getId());
                    first_title.setText(nodes.getName());
                    second_title.setText(goodsSubCateData.getName());
                    break;
                case ProGroup:
                    progroups(nodes.getId());
                    first_title.setText(nodes.getName());
                    second_title.setText(goodsSubCateData.getName());
                    break;
                default:
                    break;
            }

        }else {
            switch (nodes.getType()){
                case Project:
                    pprojects(Config.getCachedPpid(B0_MyStoreActivity.this),goodsSubCateData.getId());
                    first_title.setText(nodes.getName());
                    second_title.setText(goodsSubCateData.getName());
                    break;
                case Product:
                    Pproducts(Config.getCachedPpid(B0_MyStoreActivity.this), goodsSubCateData.getId());
                    first_title.setText(nodes.getName());
                    second_title.setText(goodsSubCateData.getName());
                    break;
                case Card:
                    Pcards(Config.getCachedPpid(B0_MyStoreActivity.this), goodsSubCateData.getId());
                    first_title.setText(nodes.getName());
                    second_title.setText(goodsSubCateData.getName());
                    break;
                case Group:
                    Salsegroups(Config.getCachedPpid(B0_MyStoreActivity.this), goodsSubCateData.getId());
                    first_title.setText(nodes.getName());
                    second_title.setText(goodsSubCateData.getName());
                    break;

                default:
                    break;
            }
        }

    }


    //  获取会员卡促销商品

    private void procards(String promid){
        RequestParams params = new RequestParams();
        params.put("promid", promid);
        params.put("begin", page+"");
        params.put("count", 20+"");
        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.procards, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
//                        data.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ProcardData procardData = gson.fromJson(object.toString(), ProcardData.class);
                            data.add(new GoodsData(procardData));
                        }
                        adapter.notifyDataSetChanged();
//                        adapter = new MyAdapter(B0_MyStoreActivity.this);
//                        listview.setAdapter(adapter);
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



    //  获取疗程促销商品
    private void proprojects(String promid){
        RequestParams params = new RequestParams();
        params.put("promid", promid);
        params.put("begin", page+"");
        params.put("count", 20+"");
        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.proprojects, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
//                        data.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ProprojectsData proprojectsData = gson.fromJson(object.toString(), ProprojectsData.class);
                            data.add(new GoodsData(proprojectsData));
                        }
                        adapter.notifyDataSetChanged();
//                        adapter = new MyAdapter(B0_MyStoreActivity.this);
//                        listview.setAdapter(adapter);
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



    //获取套餐促销商品
    private void progroups(String promid){
        RequestParams params = new RequestParams();
        params.put("promid", promid);
        params.put("begin", page+"");
        params.put("count", 20+"");
        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.progroups, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
//                        data.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ProgroupsData progroutsData = gson.fromJson(object.toString(), ProgroupsData.class);
                            data.add(new GoodsData(progroutsData));
                        }
                        adapter.notifyDataSetChanged();
//                        adapter = new MyAdapter(B0_MyStoreActivity.this);
//                        listview.setAdapter(adapter);
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

    int page=0;
    //获取产品促销商品
    private void proproducts(String promid){
        RequestParams params = new RequestParams();
        params.put("promid", promid);
        params.put("begin", page+"");
        params.put("count", 20+"");
        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.proproducts, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ProproductData proproductData = gson.fromJson(object.toString(), ProproductData.class);
                            data.add(new GoodsData(proproductData));
                        }
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


    //获取套餐
    private void Salsegroups(String ppid,String cateid){
        RequestParams params = new RequestParams();
        params.put("ppid", ppid);
        params.put("cateid", cateid);
        params.put("begin", page+"");
        params.put("count", 20+"");
        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.Salsegroups, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
//                        data.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            GroupsData groupsdata = gson.fromJson(object.toString(), GroupsData.class);
                            data.add(new GoodsData(groupsdata));
                        }
                        adapter.notifyDataSetChanged();
//                        adapter = new MyAdapter(B0_MyStoreActivity.this);
//                        listview.setAdapter(adapter);
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
    private void pprojects(String ppid,String cateid){
        RequestParams params = new RequestParams();
        params.put("ppid", ppid);
        params.put("cateid", cateid);
        params.put("begin", page+"");
        params.put("count", 20+"");
        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.Pprojects, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
//                        data.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ProjectsData projectsData = gson.fromJson(object.toString(), ProjectsData.class);
                            data.add(new GoodsData(projectsData));
                        }
                        adapter.notifyDataSetChanged();
//                        first_title.setText("项目");
//                        second_title.setText();
//                        adapter = new MyAdapter(B0_MyStoreActivity.this);
//                        listview.setAdapter(adapter);
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

    //获取产品
    private void Pproducts(String ppid,String cateid){
        RequestParams params = new RequestParams();
        params.put("ppid", ppid);
        params.put("cateid", cateid);
        params.put("begin", page+"");
        params.put("count", 20+"");

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.Pproducts, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
//                        data.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ProductData productData = gson.fromJson(object.toString(), ProductData.class);
                            data.add(new GoodsData(productData));
                        }
                        adapter.notifyDataSetChanged();
//                        adapter = new MyAdapter(B0_MyStoreActivity.this);
//                        listview.setAdapter(adapter);
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

    //获取会员卡
    private void Pcards(String ppid,String cateid){
        RequestParams params = new RequestParams();
        params.put("ppid", ppid);
        params.put("cateid", cateid);
        params.put("begin", page+"");
        params.put("count", 20+"");

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.Pcards, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
//                        data.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            PcardsData pcardsData = gson.fromJson(object.toString(), PcardsData.class);
                            data.add(new GoodsData(pcardsData));
                        }
                        adapter.notifyDataSetChanged();
//                        adapter = new MyAdapter(B0_MyStoreActivity.this);
//                        listview.setAdapter(adapter);
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






    private void brandInfo(String ppid,String branid){
        RequestParams params = new RequestParams();
        params.put("ppid", ppid);
        params.put("branid", branid);
        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.brandinfo, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        BrandinfoData brandinfoData = gson.fromJson(obj.getJSONObject("data").toString(),BrandinfoData.class);
                        imageLoader.displayImage(LoadImgUtil.loadbigImg(brandinfoData.getLogo()), cell_img);
                        cell_text.setText(brandinfoData.getName());
                        cell_text_two.setText(brandinfoData.getAddress());
                        tel_num = brandinfoData.getTel();
                        store_phone.setText(tel_num);
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



    private void projectCates(final String ppid) {
        RequestParams params = new RequestParams();
        params.put("ppid", ppid);
        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.project_cates, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        subNodes.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            projectCateData = gson.fromJson(object.toString(), ProjectCateData.class);
                            subNodes.add(new GoodsSubCateData(GoodsType.Project, projectCateData.getCateid(), projectCateData.getName(), projectCateData.getCount()));
                        }
                        first_title.setText("项目");
                        second_title.setText(subNodes.get(0).getName());
                        pprojects(ppid, subNodes.get(0).getId());

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





    // ViewHolder静态类
    static class ViewHolder {
        public ImageView cell_img;
        public TextView name;
        public TextView price;
    }

    class GoodsData
    {
        public Boolean isProms;
        public GoodsType targeType;
        public String targetId;
        public String targetName;
        public String picture;
        public String price;
        public Boolean hasFree;
        public String rule;
        public GoodsData(ProjectsData data)
        {
            this.isProms = false;
            this.targeType = GoodsType.Project;
            this.targetId = data.getProjectid();
            this.targetName = data.getName();
            this.picture = data.getPic();
            this.price = data.getPrice();
            this.hasFree = false;
            iscu=false;
        }

        public GoodsData(ProductData data)
        {
            this.isProms = false;
            this.targeType = GoodsType.Product;
            this.targetId = data.getProductid();
            this.targetName = data.getName();
            this.picture = data.getPic();
            this.price = data.getPrice();
            this.hasFree = false;
            iscu=false;
        }

        public GoodsData(PcardsData data)
        {
            this.isProms = false;
            this.targeType = GoodsType.Card;
            this.targetId = data.getCardid();
            this.targetName = data.getName();
            this.picture = data.getPic();
            this.price = data.getMoney();
//            this.hasFree = false;
            this.hasFree=data.getHasfree();
            iscu=false;
        }

        public GoodsData(GroupsData data)
        {
            this.isProms = false;
            this.targeType = GoodsType.Group;
            this.targetId = data.getGroupid();
            this.targetName = data.getName();
            this.picture = data.getPic();
            this.price = data.getPrice();
            this.hasFree = false;
            iscu=false;
        }

        public GoodsData(ProproductData data)
        {
            this.isProms = false;
            this.targeType = GoodsType.ProProduct;
            this.targetId = data.getPid();
            this.targetName = data.getName();
            this.picture = data.getPic();
            this.price = data.getPrice();
//            this.hasFree = true;
            this.hasFree=data.getHasfree();
            iscu=true;
        }

        public GoodsData(ProprojectsData data)
        {
            this.isProms = false;
            this.targeType = GoodsType.ProProject;
            this.targetId = data.getPid();
            this.targetName = data.getName();
            this.picture = data.getPic();
            this.price = data.getPrice();
            this.hasFree = true;
            iscu=true;
            this.hasFree=data.getHasfree();
        }

        boolean iscu;
        public GoodsData(ProgroupsData data)
        {
            this.isProms = false;
            this.targeType = GoodsType.ProGroup;
            this.targetId = data.getPid();
            this.targetName = data.getName();
            this.picture = data.getPic();
            this.price = data.getPrice();
            this.hasFree = true;
            iscu=true;
            this.hasFree=data.getHasfree();
        }


        public GoodsData(ProcardData data)
        {
            this.isProms = false;
            this.targeType = GoodsType.ProCard;
            this.targetId = data.getPid();
            this.targetName = data.getName();
            this.picture = data.getPic();
            this.price = data.getMoney();
            this.hasFree = true;
            iscu=true;
            this.hasFree=data.getHasfree();
        }
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public GoodsData getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                ViewHolder holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.b0_mystoreactivty_cell, null);
                holder.cell_img = (ImageView) convertView.findViewById(R.id.cell_img);
                holder.name = (TextView) convertView.findViewById(R.id.cell_text);
                holder.price = (TextView) convertView.findViewById(R.id.cell_text_two);
                imageLoader.displayImage(LoadImgUtil.loadbigImg(data.get(position).picture), holder.cell_img);
                holder.name.setText(data.get(position).targetName);
                holder.price.setText("售价：￥" + data.get(position).price);
                convertView.setTag(data.get(position));
                ImageView zeng= (ImageView) convertView.findViewById(R.id.detail_zeng);
                ImageView cu= (ImageView) convertView.findViewById(R.id.detail_cu);
                if (data.get(position).hasFree){
                    zeng.setVisibility(View.VISIBLE);
                }else{
                    zeng.setVisibility(View.GONE);
                }
                if (data.get(position).iscu){
                    cu.setVisibility(View.VISIBLE);
                } else {
                    cu.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }
    }
}
