package com.ecasona.newapi.pay.pays.ali;

import android.app.Activity;
import android.content.Context;

import com.alipay.sdk.app.PayTask;
import com.ecasona.newapi.pay.listener.PayListener;
import com.ecasona.newapi.pay.model.KeyLibs;
import com.ecasona.newapi.pay.model.OrderInfo;
import com.ecasona.newapi.pay.pays.IPayable;
import com.ecasona.newapi.pay.security.ali.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class AliPay implements IPayable {

    /**
     * 支付
     *
     * @param activity  支付页面activity
     * @param orderInfo 规范的订单信息
     * @param prepayId  预付单号（微信）
     * @return
     */
    public String pay(Activity activity, OrderInfo orderInfo, String prepayId) {
        PayTask payTask = new PayTask(activity);
        String result = payTask.pay(orderInfo.getContent());
        return result;
    }

    /**
     * 生成订单参数
     *
     * @param body           商品详情。对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
     * @param invalidTime    未付款交易的超时时间。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。参数不接受小数点，如1.5h，可转换为90m。
     * @param notifyUrl      服务器异步通知页面路径
     * @param tradeNo        商户唯一订单号
     * @param subject        商品的标题/交易标题/订单标题/订单关键字等。该参数最长为128个汉字。
     * @param totalFee       该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
     * @param spbillCreateIp 终端ip。APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     * @return
     */
    public OrderInfo buildOrderInfo(
            String body,
            String invalidTime,
            String notifyUrl,
            String tradeNo,
            String subject,
            String totalFee,
            String spbillCreateIp
    ) {
        String ordInfo = getOrderInfo(body, invalidTime, notifyUrl, tradeNo, subject, totalFee);

        // 对订单做RSA 签名
        String sign = sign(ordInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        String payInfo = ordInfo + "&sign=" + KeyLibs.mark + sign + KeyLibs.mark + "&"
                + getSignType();

        return new OrderInfo(payInfo);
    }

    public void registerApp(Context context, String appId) {
        return;
    }

    public void getPrepayId(OrderInfo orderInfo, PayListener payListener) {
        return;
    }

    /**
     * @param subject      商品名称
     * @param body         商品详情
     * @param price        商品金额
     * @param recharge_num 订单号
     * @return
     */
    public String getOrderInfo(String body, String invalidTime, String notifyUrl,
                               String recharge_num, String subject, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + KeyLibs.mark + KeyLibs.ali_partner + KeyLibs.mark;

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + KeyLibs.mark + KeyLibs.ali_sellerId + KeyLibs.mark;

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + KeyLibs.mark + recharge_num + KeyLibs.mark;

        // 商品名称
        orderInfo += "&subject=" + KeyLibs.mark + subject + KeyLibs.mark;

        // 商品详情
        orderInfo += "&body=" + KeyLibs.mark + body + KeyLibs.mark;

        // 商品金额
        orderInfo += "&total_fee=" + KeyLibs.mark + price + KeyLibs.mark;

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + KeyLibs.mark
                + notifyUrl// 添加回调地址
                + KeyLibs.mark;
        // 服务接口名称， 固定值
        orderInfo += "&service=" + KeyLibs.mark + "mobile.securitypay.pay" + KeyLibs.mark;

        // 支付类型， 固定值
        orderInfo += "&payment_type=" + KeyLibs.mark + "1" + KeyLibs.mark;

        // 参数编码， 固定值
        orderInfo += "&_input_charset=" + KeyLibs.mark + "utf-8" + KeyLibs.mark;

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=" + KeyLibs.mark + invalidTime + KeyLibs.mark;

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + KeyLibs.mark + extern_token + KeyLibs.mark;

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        // orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, KeyLibs.ali_privateKey);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=" + KeyLibs.mark + "RSA" + KeyLibs.mark;
    }
}
