package com.gbm.mgb.utils.order;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Waylon on 2017/3/24.
 */
public class Config {

    public static String alipayUrl = "http://112.74.215.56:7099/mgb-payment-interface/alipayWebPay?";
    public static String wechatUrl = "http://112.74.215.56:7099/mgb-payment-interface/weChatJSAPI";
    public static String refundAliPayUrl = "http://112.74.215.56:7099/mgb-payment-interface/alipayRefund";
    public static String refundWechatPayUrl = "http://112.74.215.56:7099/mgb-payment-interface/weChatPayRefund";
    public static String weChatOauth2Url = "http://112.74.215.56:7099/mgb-payment-interface/weChatOauth2";


    //积分商城测试
    //public static String mgbUrl = "http://happy.mgb.cn/#/loading2?";
    //积分商城线网
    public static String mgbUrl = "https://mgb.cn/#/loading2?";

    //商户号A1066
    public static String merchantNo = "A1066";


    //测试
    public static String paternerNo = "20162120";
    //现网
    //public static String paternerNo = "20162118";


    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCoPooz8fZWT0FJYlu7SmSOvDvE2jDHVs2FKQbS8QAXZ04wlhJMGFMXY4WhaquJt/g8rdjcL6rQzIVH/G4gyv733N3yC4B4GWfwvbgM+qQoEXOXm+w7v07lLJk5PBAUri/Q4D6wiOFf1iWUNxSxTF+ip9Ex1ncXOiKCsEMdsOHEGwIDAQAB";
	public static String MD5String = "mgb";

    //实物
    public static int PHYSICAL_PRODUCT = 1;
    //票劵
    public static int VIRTUAL_PRODUCT = 2;
//    @Value("${cloud.url}")
//    public static String cloudUrl;

    //发送券码短信的模板id
    public static String CODE_MESSAGE = "9"; 	 	

}
