package com.xem.mzbcustomerapp.net;

/**
 * Created by xuebing on 15/10/26.
 */
public class MzbUrlFactory {

//    public static final String BASE_URL = "http://api-dev.meizhiban.com/v2/";
    public static final String BASE_URL = "http://api.meizhiban.com/v2/";

    public static final String PLATFORM_CHECK = "platform/update/check";
    public static final String authcode = "platform/user/authcode"; // 2.1.1 验证码 platform/user/authcode
    public static final String register = "platform/user/register";//2.1.2	注册
    public static final String clogin = "platform/user/clogin";//2.1.3	会员端登录
    public static final String spassword = "platform/user/spassword";//2.1.6.1	通过验证码修改
    public static final String password = "platform/user/password";//2.1.6.1	通过验证码修改
    public static final String minfo = "platform/user/minfo";//2.1.8.2	修改个人信息
    public static final String user_info = "platform/user/info";//2.1.8.2	修改个人信息





    public static final String logout = "platform/user/logout";//2.1.6	退出
    public static final String portrait = "platform/user/portrait";//2.1.6	退出
    public static final String cdefault = "platform/user/cdefault";//2.1.4	会员端默认数据
    public static final String brandinfo = "brand/branch/info";//3.5.1	获取门店信息
    public static final String signin = "platform/appointment/signin";//2.2.4	会员签到
    public static final String bind = "platform/user/bind";//2.1.14	绑定客户档案
    public static final String order_cancel = "platform/appointment/cancel";//2.2.3	取消预约




    public static final String Pprojects = "brand/project/projects";//3.8.2	获取项目
    public static final String Pproducts = "brand/product/products";//3.9.2	获取产品
    public static final String Pcards = "brand/card/cards";//3.10.2	获取会员卡
    public static final String Salsegroups = "brand/salegroup/groups";//	3.11.2	获取套餐


    public static final String proms = "brand/promotion/proms";//3.13.1	获得促销活动
    public static final String proproducts = "brand/promotion/products";//3.13.2	获取产品促销商品
    public static final String proprojects = "brand/promotion/projects";//3.13.3	获取疗程促销商品
    public static final String progroups = "brand/promotion/groups";//3.13.4	获取套餐促销商品
    public static final String procards = "brand/promotion/cards";//3.13.5	获取会员卡促销商品






    public static final String project_cates = "brand/project/cates";//3.8.1	获取项目分类
    public static final String product_cates = "brand/product/cates";//3.9.1	获取产品分类
    public static final String salegroup_cates = "brand/salegroup/cates";//3.9.1	获取产品分类
    public static final String card_cates = "brand/card/cates";//3.10.1	获取会员卡分类

    public static final String comment = "platform/service/scoreinfo";//2.6.1	查询服务记录
    public static final String service_records = "platform/service/records";//2.6.1	查询服务记录
    public static final String user_branches = "platform/user/branches";//2.1.12	用户相关门店
    public static final String curbranch = "platform/user/curbranch";//2.1.13	设置用户当前门店

    public static final String cpushagrs = "platform/user/cpushagrs";//2.1.13	设置用户当前门店
    public static final String order_last = "platform/appointment/last";//2.1.13	设置用户当前门店


    public static final String accounts = "brand/customer/accounts";//3.6.4	客户账户资产
    public static final String coupons = "platform/coupon/coupons";//2.3.2	优惠券
    public static final String records = "platform/diagnosis/records";//2.3.2	优惠券
    public static final String detail = "platform/diagnosis/detail";//2.9.2	诊断记录详情
    public static final String diagnosis_last = "platform/diagnosis/last";//2.9.3	最新诊断记录详情
    public static final String pp_info = "brand/pp/info";//3.4.1	获取品牌信息
    public static final String branch_info = "brand/branch/info";//3.5.1	获取门店信息



    public static final String em_query = "brand/employe/query";//3.1.7.2	通过岗位获取员工
    public static final String em_send = "platform/appointment/send";//2.2.1	预约


    public static final String scoreinfo = "platform/service/scoreinfo";//2.2.1	预约
    public static final String evaluate = "platform/service/evaluate";//2.2.1	预约
    public static final String share_brandata = "platform/share/brandata";//2.2.1	预约
    public static final String prjacc = "platform/appointment/prjacc";//2.2.6	会员疗程帐户





    public static final String pdetail = "brand/project/detail";//3.8.3	项目详细信息
    public static final String ddetail = "brand/product/detail";//3.8.3	项目详细信息
    public static final String sdetail = "brand/salegroup/detail";//3.8.3	项目详细信息
    public static final String cdetail = "brand/card/detail";//3.8.3	项目详细信息

    public static final String pdtdetail = "brand/promotion/pdtdetail";//3.8.3	项目详细信息
    public static final String pprjdetail = "brand/promotion/prjdetail";//3.8.3	项目详细信息
    public static final String pgoupdetail = "brand/promotion/groupdetail";//3.8.3	项目详细信息
    public static final String pcardetail = "brand/promotion/cardetail";//3.8.3	项目详细信息
    public static final String BRAND_UNBIND = "platform/user/unbind"; //解除绑定
}
