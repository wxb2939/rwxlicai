package com.xem.mzbphoneapp.utils;

/**
 * Created by xuebing on 15/6/12.
 */
public class MzbUrlFactory {

    public static final String BASE_URL = "http://api-dev.meizhiban.com/v1/";
//    public static final String BASE_URL = "http://api.meizhiban.com/v1/";
//    public static final String BASE_URL = "http://192.168.1.111:8080/v1/";

    public static final String PLATFORM_CHECK = "platform/update/check";
    public static final String PLATFORM_AUTHCODE = "platform/user/authcode";
    public static final String PLATFORM_REGISTER = "platform/user/register";
    public static final String PLATFORM_LOGIN = "platform/user/login";
    public static final String PLATFORM_PASSWORD = "platform/user/password";
    public static final String PLATFORM_FPASSWORD = "platform/user/spassword";
    public static final String PLATFORM_INFO = "platform/user/info";
    public static final String PLATFORM_PORTRAIT = "platform/user/portrait";
    public static final String PLATFORM_FEEDBACK = "platform/feedback/send";//2.7	意见反馈
    public static final String PLATFORM_MINFO = "platform/user/minfo";
    public static final String PLATFORM_BRANCHES = "platform/user/branches";
    public static final String PLATFORM_SEND = "platform/appointment/send";
    public static final String PLATFORM_RECORDS = "platform/appointment/records";
    public static final String PLATFORM_CANCEL = "platform/appointment/cancel";
    public static final String PLATFORM_SIGNIN = "platform/appointment/signin";
    public static final String PLATFORM_CURBRANCH = "platform/user/curbranch";
    public static final String PLATFORM_PUSHAGRS = "platform/user/pushagrs";
    public static final String PLATFORM_BIND = "platform/user/bind";
    public static final String PLATFORM_COUPONS = "platform/coupon/coupons";
    public static final String PLATFORM_BRANDATA = "platform/share/brandata";
    public static final String PLATFORM_COUPDATA = "platform/share/coupdata";
    public static final String PLATFORM_SER_RECORDS = "platform/service/records";
    public static final String PLATFORM_EVALUATE = "platform/service/evaluate";
    public static final String PLATFORM_DETAIL = "platform/service/detail";
    public static final String PLATFORM_PRJACC = "platform/appointment/prjacc";
    public static final String PLATFORM_DEMOLOGIN = "platform/demo/login";


    public static final String BRAND_BIND = "brand/employe/bind";
    public static final String BRAND_UNBIND = "brand/employe/unbind";
    public static final String BRAND_INFO = "brand/employe/info";
    public static final String BRAND_PASSWORD = "brand/employe/password";
    public static final String BRAND_ASSESTS = "brand/customer/assets";
    public static final String BRAND_ACCOUNTS = "brand/customer/accounts";
    public static final String BRAND_TPP = "brand/service/tpp";
    public static final String BRAND_BRANCH = "brand/appointment/branch";
    public static final String BRAND_SBRANCH = "brand/satisfaction/branch";
    public static final String BRAND_EMPLOYEE = "brand/satisfaction/employee";
    public static final String BRAND_appointmentapp = "brand/appointment/pp";
    public static final String BRAND_EMPLOYE_QUERY = "brand/employe/query";



    //137管理
    public static final String BRAND_CUSTOMERS = "brand/plan137/customers";
    public static final String BRAND_SELECT = "brand/plan137/select";
    public static final String BRAND_FOCUS = "brand/plan137/focus";
    public static final String BRAND_CUSTDETAIL = "brand/plan137/custdetail";
    public static final String BRAND_CALENDAR = "brand/plan137/calendar";
    public static final String BRAND_UNFOCUS = "brand/plan137/unfocus";
    public static final String BRAND_CREATE = "brand/plan137/create";
    public static final String BRAND_137CANCEL = "brand/plan137/cancel";
    public static final String BRAND_MODIFY = "brand/plan137/modify";
    public static final String BRAND_DETAIL = "brand/plan137/detail";

    //业绩
    public static final String BRAND_PERFPP = "brand/performance/pp";
    public static final String BRAND_PERFBRANCH = "brand/performance/branch";
    public static final String BRAND_PERFEMPLOYEE = "brand/performance/employee";

    //当日门店服务
    public static final String BRAND_TBSERVING = "brand/service/tbserving";
    public static final String BRAND_TBAPPOING = "brand/service/tbappoing";
    public static final String BRAND_TBLEAVED = "brand/service/tbleaved";

    //当日门店服务详情
    public static final String BRAND_TBSERCUST = "brand/service/tbsercust";
    public static final String BRAND_TBSERITEMS = "brand/service/tbseritems";

    public static final String BRAND_TBLEACUST = "brand/service/tbleacust";
    public static final String BRAND_TBLEAITEMS = "brand/service/tbleaitems";
    public static final String BRAND_TBAPPOCUST = "brand/service/tbappocust";

}
