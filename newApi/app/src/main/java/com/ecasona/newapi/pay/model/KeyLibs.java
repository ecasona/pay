package com.ecasona.newapi.pay.model;

/**
 * 微信支付，支付宝支付的相关信息
 */
public class KeyLibs {

    public static final String mark = "\"";

    // 签约的支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。
    public static final String ali_partner = "";
    // 卖家支付宝账号（邮箱或手机号码格式）或其对应的支付宝唯一用户号（以2088开头的纯16位数字）。
    public static final String ali_sellerId = "";
    //商户rsa私钥，pkcs8格式
    public static final String ali_privateKey = "";
    // appId（在请同时修改  androidmanifest.xml里面，.PayActivityd里的属性
    //       <data android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid）
    public static final String weixin_appId = "";
    // 商户号
    public static final String weixin_mchId = "";
    //API密钥
    public static final String weixin_privateKey = "";

}
