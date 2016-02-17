package com.xem.mzbcustomerapp.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.xem.mzbcustomerapp.R;
import com.xem.mzbcustomerapp.base.BaseActivity;
import com.xem.mzbcustomerapp.entity.CardDateilData;
import com.xem.mzbcustomerapp.entity.GroupDetailData;
import com.xem.mzbcustomerapp.entity.ProcardetailData;
import com.xem.mzbcustomerapp.entity.ProdtdetailData;
import com.xem.mzbcustomerapp.entity.ProductDetailData;
import com.xem.mzbcustomerapp.entity.ProgroupdetailData;
import com.xem.mzbcustomerapp.entity.ProjectDetailData;
import com.xem.mzbcustomerapp.entity.ProprjdetailData;
import com.xem.mzbcustomerapp.enums.GoodsType;
import com.xem.mzbcustomerapp.net.MzbHttpClient;
import com.xem.mzbcustomerapp.net.MzbUrlFactory;
import com.xem.mzbcustomerapp.net.NetCallBack;
import com.xem.mzbcustomerapp.utils.LoadImgUtil;
import com.xem.mzbcustomerapp.view.TitleBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by xuebing on 15/11/4.
 */
public class B1_GoodsDetailActivity extends BaseActivity {

    @InjectView(R.id.titlebar_iv_left)
    ImageView back;
    @InjectView(R.id.detail_img)
    ImageView detail_img;
    @InjectView(R.id.detail_name)
    TextView detail_name;
    @InjectView(R.id.detail_zeng)
    ImageView detail_zeng;
    @InjectView(R.id.detail_cu)
    ImageView detail_cu;
    @InjectView(R.id.detail_price)
    TextView detail_price;
    @InjectView(R.id.detail_cprice)
    TextView detail_cprice;
    @InjectView(R.id.detail_tprice)
    TextView detail_tprice;
    @InjectView(R.id.detail_cinfo)
    TextView detail_cinfo;
    @InjectView(R.id.detail_desc)
    TextView detail_desc;
    @InjectView(R.id.detail_effect)
    TextView detail_effect;
    @InjectView(R.id.detail_tips)
    TextView detail_tips;
    @InjectView(R.id.ll_cinfo)
    View ll_cinfo;
    @InjectView(R.id.head)
    View head;
    @InjectView(R.id.project_desc)
    TextView project_desc;
    @InjectView(R.id.project_effect)
    TextView project_effect;
    @InjectView(R.id.desc)
    View desc;
    @InjectView(R.id.order_btn)
    TextView oder;

    private GoodsType targeType;
    private String targetId;
    private String name;
    private Object data;
    private int wid;
    boolean hasfree;
    @Override
    protected void initView() {
        setContentView(R.layout.b1_goosdetail_activity);
        ButterKnife.inject(this);
        new TitleBuilder(this).setTitleText("商品详情").setLeftImage(R.mipmap.top_view_back);
        targetId = getIntent().getStringExtra("id");
        hasfree=getIntent().getBooleanExtra("hasfree", false);
        name = getIntent().getStringExtra("name");
//        String type = getIntent().getStringExtra("type");
        targeType = GoodsType.valueOf(getIntent().getStringExtra("type"));
        if (targeType.equals(GoodsType.Project)){
            oder.setVisibility(View.VISIBLE);
        }else {
            oder.setVisibility(View.INVISIBLE);
        }
        loadData();
    }

    @Override
    protected void initData() {
        ViewGroup.LayoutParams params = detail_img.getLayoutParams();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wid = (int) wm.getDefaultDisplay().getWidth();
        params.width = wid;
        params.height = wid;
        detail_img.setLayoutParams(params);
    }



    @OnClick({R.id.titlebar_iv_left,R.id.order_btn})
    public void clickActon(View view){
        switch (view.getId()){
            case R.id.titlebar_iv_left:
                finish();
                break;
            case R.id.order_btn:
                Intent mIntent = new Intent(B1_GoodsDetailActivity.this,B0_OrderActivity.class);
                mIntent.putExtra("id",targetId);
                mIntent.putExtra("name",name);
                startActivity(mIntent);
                break;
            default:
                break;
        }
    }

    // 加载数据
    private void loadData()
    {
        switch (targeType)
        {
            case Project:
                loadProjectData();
                break;
            case Product:
                loadProductData();
                break;
            case Group:
                loadGroupData();
                break;
            case Card:
                loadCardData();
                break;
            case ProProject:
                loadprjdetail();
                break;
            case ProProduct:
                loadpdtdetail();
                break;
            case ProGroup:
                loadgroupdetail();
                break;
            case ProCard:
                loadcardetail();
                break;

            default:
                break;
        }
    }
    private void loadProjectData()
    {
        RequestParams params = new RequestParams();
        params.put("projectid", targetId);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.pdetail, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        data = gson.fromJson(obj.getString("data"), ProjectDetailData.class);
                        showUI();
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
    private void loadProductData()
    {

        RequestParams params = new RequestParams();
        params.put("productid", targetId);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.ddetail, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        data = gson.fromJson(obj.getString("data"), ProductDetailData.class);
                        showUI();
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
    private void loadGroupData()
    {

        RequestParams params = new RequestParams();
        params.put("groupid", targetId);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.sdetail, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        data = gson.fromJson(obj.getString("data"), GroupDetailData.class);
                        showUI();
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
    private void loadCardData()
    {

        RequestParams params = new RequestParams();
        params.put("cardid", targetId);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.cdetail, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        data = gson.fromJson(obj.getString("data"), CardDateilData.class);
                        showUI();
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

    private void loadpdtdetail()
    {
        RequestParams params = new RequestParams();
        params.put("pid", targetId);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.pdtdetail, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        data = gson.fromJson(obj.getString("data"), ProdtdetailData.class);
                        showUI();
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


    private void loadprjdetail()
    {

        RequestParams params = new RequestParams();
        params.put("pid", targetId);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.pprjdetail, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        data = gson.fromJson(obj.getString("data"), ProprjdetailData.class);
                        showUI();
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

    private void loadgroupdetail()
    {

        RequestParams params = new RequestParams();
        params.put("pid", targetId);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.pgoupdetail, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        data = gson.fromJson(obj.getString("data"), ProgroupdetailData.class);
                        showUI();
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


    private void loadcardetail()
    {
        RequestParams params = new RequestParams();
        params.put("cardpromid", targetId);

        MzbHttpClient.ClientPost(MzbUrlFactory.BASE_URL + MzbUrlFactory.pcardetail, params, new NetCallBack(this) {
            @Override
            public void onMzbSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getInt("code") == 0) {
                        data = gson.fromJson(obj.getString("data"), ProcardetailData.class);
                        showUI();
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


    // 显示数据
    private void showUI()
    {
        switch (targeType)
        {
            //项目
            case Project:
                showProjectUI((ProjectDetailData)data);
                break;
            //产品
            case Product:
                shouProductUI((ProductDetailData) data);
                break;
            //套餐
            case Group:
                shouGroupUI((GroupDetailData) data);
                break;
            //会员卡
            case Card:
                shouCardUI((CardDateilData) data);
                break;
            //促销项目
            case ProProject:
                shouProprjUI((ProprjdetailData) data);
                break;
            //促销产品
            case ProProduct:
                shouPropdtUI((ProdtdetailData) data);
                break;
            //促销会员卡
            case ProCard:
                shouProcardUI((ProcardetailData) data);
                break;
            //促销套餐
            case ProGroup:
                shouProgrouUI((ProgroupdetailData) data);
                break;

        }
    }




    private void showProjectUI(ProjectDetailData data)
    {
        imageLoader.displayImage(LoadImgUtil.loadbiggerImg(data.getPic()), detail_img);
        detail_name.setText(data.getName());
        detail_price.setText("售价:"+data.getMprice().toString());
//        detail_cprice.setText(data.getCprice().toString());
//        detail_tprice.setText(data.getCprice().toString());
        detail_desc.setText(data.getDesc());
        detail_effect.setText(data.getEffect());
        detail_tips.setText(data.getTips());

        ll_cinfo.setVisibility(View.GONE);
        detail_cinfo.setVisibility(View.GONE);
        detail_cu.setVisibility(View.GONE);
        detail_zeng.setVisibility(View.GONE);

    }

    private void shouProductUI(ProductDetailData data)
    {
        imageLoader.displayImage(LoadImgUtil.loadbigImg(data.getPic()), detail_img);
        project_effect.setText("产品作用");
        project_desc.setText("产品描述");
        detail_name.setText(data.getName());
        detail_price.setText("售价:"+data.getPrice().toString());
//        detail_cprice.setText(data.getCprice().toString());
//        detail_tprice.setText(data.getCprice().toString());
        detail_desc.setText(data.getDesc());
        detail_effect.setText(data.getEffect());
        detail_tips.setText(data.getTips());

        ll_cinfo.setVisibility(View.GONE);
        detail_cinfo.setVisibility(View.GONE);
        detail_cu.setVisibility(View.GONE);
        detail_zeng.setVisibility(View.GONE);

    }

    private void shouCardUI(CardDateilData data)
    {
        imageLoader.displayImage(LoadImgUtil.loadbigImg(data.getPic()), detail_img);
        detail_name.setText(data.getName());
        detail_price.setText("售价:"+data.getMoney());
        project_desc.setText("会员卡描述");
//        project_effect.setVisibility(View.GONE);
        desc.setVisibility(View.GONE);
//        detail_cprice.setText(data.getMpresent());
//        detail_tprice.setText(data.getCprice().toString());
        detail_desc.setText(data.getDesc());
//        detail_effect.setText(data.getEffect());
        detail_tips.setText(data.getTips());
//        detail_zeng
        if (hasfree){
            detail_zeng.setVisibility(View.VISIBLE);
        }else{
            detail_zeng.setVisibility(View.GONE);
        }
        ll_cinfo.setVisibility(View.GONE);
        detail_cinfo.setVisibility(View.GONE);
        detail_cu.setVisibility(View.GONE);
    }


    private void shouGroupUI(GroupDetailData data)
    {
        imageLoader.displayImage(LoadImgUtil.loadbigImg(data.getPic()), detail_img);
        detail_name.setText(data.getName());
//        detail_price.setText(data.getPrice().toString());
 //       detail_cprice.setText(data.getPrice().toString());
        detail_price.setText("售价:"+data.getGprice().toString());
        project_desc.setText("套餐描述");
        project_effect.setText("套餐作用");
        detail_desc.setText(data.getDesc());
        detail_effect.setText(data.getEffect());
        detail_tips.setText(data.getTips());

        ll_cinfo.setVisibility(View.GONE);
        detail_cinfo.setVisibility(View.GONE);
        detail_cu.setVisibility(View.GONE);
        detail_zeng.setVisibility(View.GONE);

    }





    private void shouPropdtUI(ProdtdetailData data)
    {
        imageLoader.displayImage(LoadImgUtil.loadbigImg(data.getPic()), detail_img);
        detail_name.setText(data.getName());
        detail_price.setText(data.getPrice().toString());
        project_desc.setText("产品描述");
        project_effect.setText("产品作用");
//        detail_cprice.setText(data.getPrice().toString());
//        detail_tprice.setText(data.getPstcash());
        detail_desc.setText(data.getDesc());
        detail_effect.setText(data.getEffect());
        detail_tips.setText(data.getTips());
        detail_cu.setVisibility(View.VISIBLE);
        detail_zeng.setVisibility(View.GONE);
        if (hasfree){
            detail_zeng.setVisibility(View.VISIBLE);
        }else{
            detail_zeng.setVisibility(View.GONE);
        }

    }


    private void shouProprjUI(ProprjdetailData data)
    {
        imageLoader.displayImage(LoadImgUtil.loadbigImg(data.getPic()), detail_img);
        detail_name.setText(data.getName());
        detail_price.setText(data.getPrice().toString());
//        detail_cprice.setText(data.getPrice().toString());
//        detail_tprice.setText(data.getPstcash());
        detail_desc.setText(data.getDesc());
        detail_effect.setText(data.getEffect());
        detail_tips.setText(data.getTips());
        detail_cu.setVisibility(View.VISIBLE);
        if (hasfree){
            detail_zeng.setVisibility(View.VISIBLE);
        }else{
            detail_zeng.setVisibility(View.GONE);
        }
    }


    private void shouProgrouUI(ProgroupdetailData data)
    {
        imageLoader.displayImage(LoadImgUtil.loadbigImg(data.getPic()), detail_img);
        detail_name.setText(data.getName());
        detail_price.setText(data.getPrice().toString());
        project_desc.setText("套餐说明");
        project_effect.setText("套餐作用");
//        detail_cprice.setText(data.getPrice().toString());
//        detail_tprice.setText(data.getPstcash());
        detail_desc.setText(data.getDesc());
        detail_effect.setText(data.getEffect());
        detail_tips.setText(data.getTips());
        detail_cu.setVisibility(View.VISIBLE);
        if (hasfree){
            detail_zeng.setVisibility(View.VISIBLE);
        }else{
            detail_zeng.setVisibility(View.GONE);
        }
    }



    private void shouProcardUI(ProcardetailData data)
    {
        imageLoader.displayImage(LoadImgUtil.loadbigImg(data.getPic()), detail_img);
        detail_name.setText(data.getName());
        detail_price.setText(data.getMoney().toString());
//        detail_cprice.setText(data.getPrice().toString());
//        detail_tprice.setText(data.getPstcash());
        project_desc.setText("会员卡描述");
        desc.setVisibility(View.GONE);
        detail_desc.setText(data.getDesc());
        detail_effect.setText(data.getExplain());
        detail_tips.setText(data.getTips());
        detail_cu.setVisibility(View.VISIBLE);
        if (hasfree){
            detail_zeng.setVisibility(View.VISIBLE);
        }else{
            detail_zeng.setVisibility(View.GONE);
        }
    }

}
