package com.ecasona.newapi.pay.appPay;

import android.app.Activity;

import com.ecasona.newapi.pay.model.PayType;

/**
 * Created by aiy on 2016/10/12.
 * <p>
 * des:支付管理
 */

public class AppPayManager {
    /**
     * 支付调用
     *
     * @param body           商品详情。对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
     * @param invalidTime    未付款交易的超时时间。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。参数不接受小数点，如1.5h，可转换为90m。
     * @param notifyUrl      服务器异步通知页面路径
     * @param tradeNo        商户唯一订单号
     * @param subject        商品的标题/交易标题/订单标题/订单关键字等。该参数最长为128个汉字。
     * @param totalFee       该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。(微信则是以分为单位的整形)
     * @param spbillCreateIp 终端ip。APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     * @param type           支付方式
     */
    public static void pay(Activity activity,
                           String body,
                           String invalidTime,
                           String notifyUrl,
                           String tradeNo,
                           String subject,
                           String totalFee,
                           String spbillCreateIp,
                           PayType type) {
        AppFactory.getInstance(activity, type).pay(activity, body, invalidTime, notifyUrl, tradeNo, subject, totalFee, spbillCreateIp);
    }
}
