package com.xem.mzbcustomerapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.adapter.MyAdapter;
import com.xem.mzbcustomerapp.adapter.SubAdapter;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.CardCateData;
import com.xem.mzbcustomerapp.entity.GoodsCateData;
import com.xem.mzbcustomerapp.entity.GoodsSubCateData;
import com.xem.mzbcustomerapp.entity.ProductCateData;
import com.xem.mzbcustomerapp.entity.ProjectCateData;
import com.xem.mzbcustomerapp.entity.PromsData;
import com.xem.mzbcustomerapp.entity.SalegroupCateData;
import com.xem.mzbcustomerapp.enums.GoodsType;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.Config;
import com.xem.mzbcustomerapp.view.MyListView;
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
 * Created by xuebing on 15/10/24.
 */
public class B1_StoreProductActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView titlebar_iv_left;
    @InjectView(R.id.listView)
    MyListView listView;
    @InjectView(R.id.subListView)
    MyListView subListView;


    private MyAdapter myAdapter;
    private SubAdapter subAdapter;

    private GoodsCateData projectCate;
    private GoodsCateData productCate;
    private GoodsCateData groupCate;
    private GoodsCateData cardCate;


    GoodsCateData nodeSelected = null;
    List<GoodsCateData> nodes = new ArrayList<>();
    private int resultCode = -101;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.b1_storeproduct_activity);
        new TitleBuilder(this).setTitleText("选择分类").setLeftImage(R.mipmap.top_view_back);
        ButterKnife.inject(this);
        projectCate = new GoodsCateData(GoodsType.Project, false, "", "项目");
        productCate = new GoodsCateData(GoodsType.Product, false, "", "产品");
        groupCate = new GoodsCateData(GoodsType.Group, false, "", "套餐");
        cardCate = new GoodsCateData(GoodsType.Card, false, "", "会员卡");
        nodes.add(projectCate);
        nodes.add(productCate);
        nodes.add(groupCate);
        nodes.add(cardCate);
        nodeSelected = projectCate;

        myAdapter = new MyAdapter(getApplicationContext(), nodes);
        listView.setAdapter(myAdapter);
        myAdapter.setSelectedPosition(0);
        myAdapter.notifyDataSetInvalidated();

        proms(Config.getCachedPpid(B1_StoreProductActivity.this));
        projectCates();
    }

    @Override
    protected void initData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                final int location = position;
                if (location > 4) {
                    return;
                }
                myAdapter.setSelectedPosition(position);
                myAdapter.notifyDataSetInvalidated();
                final GoodsCateData cate = nodes.get(position);
                nodeSelected = cate;
                subAdapter = new SubAdapter(getApplicationContext(), cate.getChildren());
                subListView.setAdapter(subAdapter);

                if (cate.getChildren().size() == 0) {
                    GoodsType type = cate.getType();
                    if (type == GoodsType.Project) {
                        projectCates();
                    } else if (type == GoodsType.Product) {
                        productCates();
                    } else if (type == GoodsType.Group) {
                        salegroupCates();
                    } else if (type == GoodsType.Card) {
                        cardCates();
                    }
                }
            }
        });


        subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                // TODO Auto-generated method stub
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("title", nodeSelected);
                List<GoodsSubCateData> subNodes = nodeSelected.getChildren();
                bundle.putSerializable("subtitle", subNodes.get(position));
                intent.putExtras(bundle);
                setResult(resultCode, intent);
                finish();
            }
        });
    }

    @OnClick({R.id.titlebar_iv_left, R.id.titlebar_tv_right})
    public void clickAction(View v) {
        switch (v.getId()) {
            case R.id.titlebar_iv_left:
                finish();
                break;
            default:
                break;
        }
    }


    private void projectCates() {
        RequestParams params = new RequestParams();
        params.put("ppid", Config.getCachedPpid(B1_StoreProductActivity.this));

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.project_cates, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        List<GoodsSubCateData> subCates = projectCate.getChildren();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ProjectCateData projectCateData = gson.fromJson(object.toString(), ProjectCateData.class);
                            GoodsSubCateData scd = new GoodsSubCateData(GoodsType.Project, projectCateData.getCateid(), projectCateData.getName(), projectCateData.getCount());
                            subCates.add(scd);
                        }
                        subAdapter = new SubAdapter(getApplicationContext(), subCates);
                        subListView.setAdapter(subAdapter);

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


    private void productCates() {
        RequestParams params = new RequestParams();
        params.put("ppid", Config.getCachedPpid(B1_StoreProductActivity.this));

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.product_cates, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        List<GoodsSubCateData> subCates = productCate.getChildren();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ProductCateData productCateData = gson.fromJson(object.toString(), ProductCateData.class);
                            GoodsSubCateData scd = new GoodsSubCateData(GoodsType.Product, productCateData.getCateid(), productCateData.getName(), productCateData.getCount());
                            subCates.add(scd);
                        }
                        subAdapter = new SubAdapter(getApplicationContext(), subCates);
                        subListView.setAdapter(subAdapter);

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


    private void salegroupCates() {
        RequestParams params = new RequestParams();
        params.put("ppid", Config.getCachedPpid(B1_StoreProductActivity.this));

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.salegroup_cates, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        List<GoodsSubCateData> subCates = groupCate.getChildren();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            GoodsCateData cate = new GoodsCateData();
                            SalegroupCateData salegroupCateData = gson.fromJson(object.toString(), SalegroupCateData.class);
                            subCates.add(new GoodsSubCateData(GoodsType.Group, salegroupCateData.getCateid(),
                                    salegroupCateData.getName(), salegroupCateData.getCount()));
                        }
                        subAdapter = new SubAdapter(getApplicationContext(), subCates);
                        subListView.setAdapter(subAdapter);

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


    private void cardCates() {
        RequestParams params = new RequestParams();
        params.put("ppid", Config.getCachedPpid(B1_StoreProductActivity.this));

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.card_cates, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;
                    if (obj.getInt("code") == 0) {
                        jsonArray = new JSONArray(obj.getString("data"));
                        List<GoodsSubCateData> subCates = cardCate.getChildren();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            GoodsCateData cate = new GoodsCateData();
                            JSONObject object = jsonArray.getJSONObject(i);
                            CardCateData cardCateData = gson.fromJson(object.toString(), CardCateData.class);
                            subCates.add(new GoodsSubCateData(GoodsType.Card, cardCateData.getCateid(), cardCateData.getName(), cardCateData.getCount()));
                        }
                        subAdapter = new SubAdapter(getApplicationContext(), subCates);
                        subListView.setAdapter(subAdapter);

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


    //促销活动
    private void proms(String ppid) {
        RequestParams params = new RequestParams();
        params.put("ppid", ppid);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.proms, params, new NetCallBack() {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray jsonArray;

                    if (obj.getInt("code") == 0) {

                        jsonArray = new JSONArray(obj.getString("data"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            PromsData promsData = gson.fromJson(object.toString(), PromsData.class);

                            GoodsCateData cate = new GoodsCateData();
                            cate.setId(promsData.getPromid());
                            cate.setIsPromotion(true);
                            cate.setName(promsData.getName());

                            List<GoodsSubCateData> subCates = cate.getChildren();
                            subCates.add(new GoodsSubCateData(GoodsType.ProProduct, promsData.getPromid(), "促销产品", promsData.getProduct()));
                            subCates.add(new GoodsSubCateData(GoodsType.ProProject, promsData.getPromid(), "促销项目", promsData.getProject()));
                            subCates.add(new GoodsSubCateData(GoodsType.ProGroup, promsData.getPromid(), "促销套餐", promsData.getGroup()));
                            subCates.add(new GoodsSubCateData(GoodsType.ProCard, promsData.getPromid(), "促销会员卡", promsData.getCard()));
                            nodes.add(cate);
                        }
                        myAdapter.notifyDataSetChanged();

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
}