package com.ecasona.newapi.pay.appPay.presenter.impl;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;


import com.ecasona.newapi.pay.appPay.presenter.AppPay;
import com.ecasona.newapi.pay.listener.PayListener;
import com.ecasona.newapi.pay.model.KeyLibs;
import com.ecasona.newapi.pay.model.OrderInfo;
import com.ecasona.newapi.pay.model.PayType;
import com.ecasona.newapi.pay.pays.IPayable;
import com.ecasona.newapi.pay.pays.PaysFactory;
import com.ecasona.newapi.pay.utils.CommonUtils;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/7/15.
 */

public class AppWeixinPay implements AppPay {

    private static final String TAG = "YokaWeixinPay";

    private IPayable weixinPay;

    private AppWeixinPay(Context context) {
        weixinPay = PaysFactory.GetInstance(PayType.WeixinPay);
        weixinPay.registerApp(context, KeyLibs.weixin_appId);
    }

    private static AppWeixinPay instance = null;

    public synchronized static AppWeixinPay getInstance(Context context) {

        if (null == instance) {
            instance = new AppWeixinPay(context);
        }
        return instance;
    }

    /**
     * 生成订单参数
     *
     * @param body           商品详情。对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
     * @param invalidTime    未付款交易的超时时间。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。参数不接受小数点，如1.5h，可转换为90m。
     * @param notifyUrl      服务器异步通知页面路径
     * @param tradeNo        商户唯一订单号
     * @param subject        商品的标题/交易标题/订单标题/订单关键字等。该参数最长为128个汉字。
     * @param totalFee       该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。(微信则是以分为单位的整形)
     * @param spbillCreateIp 终端ip。APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     * @return
     */
    private OrderInfo BuilderOrderInfo(String body, String invalidTime,
                                       String notifyUrl, String tradeNo,
                                       String subject, String totalFee, String spbillCreateIp) {
        OrderInfo orderInfo = weixinPay.buildOrderInfo(body, invalidTime, notifyUrl, tradeNo, subject, totalFee, spbillCreateIp);
        return orderInfo;
    }

    private void getPrepayId(OrderInfo orderInfo, final PayListener payListener) {

        weixinPay.getPrepayId(orderInfo, new PayListener() {
            @Override
            public void success(Object object) {
                String result = weixinPay.pay(null, null, (String) object);
                payListener.success(result);

            }

            @Override
            public void failed(Object object) {

            }
        });
    }

    public void pay(String body, String invalidTime,
                    String notifyUrl, String tradeNo,
                    String subject, String totalFee, String spbillCreateIp, PayListener payListener) {

        getPrepayId(BuilderOrderInfo(body, invalidTime, notifyUrl, tradeNo, subject, totalFee, spbillCreateIp), payListener);
    }

    @Override
    public void pay(Activity activity, String body, String invalidTime, String notifyUrl, String tradeNo, String subject, String totalFee, String spbillCreateIp) {
        if (activity instanceof PayListener) {
            pay(body, invalidTime, notifyUrl, tradeNo, subject, totalFee, spbillCreateIp, (PayListener) activity);
        }
    }


    public static class PackageAsyncTask extends AsyncTask<Context, Void, Boolean> {

        private WeakReference<Handler> handler;

        public PackageAsyncTask(Handler handler) {
            this.handler = new WeakReference<>(handler);

        }

        @Override
        protected Boolean doInBackground(Context... param) {
            return CommonUtils.isWeixinAvilible(param[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            Message message = new Message();
            message.obj = aBoolean;
            message.what = 10000;
            Handler handler = this.handler.get();
            if (null != handler)
                handler.sendMessage(message);

        }
    }

}
