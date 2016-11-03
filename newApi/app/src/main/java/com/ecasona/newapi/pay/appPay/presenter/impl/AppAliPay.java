package com.ecasona.newapi.pay.appPay.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.ecasona.newapi.pay.appPay.presenter.AppPay;
import com.ecasona.newapi.pay.listener.PayListener;
import com.ecasona.newapi.pay.model.OrderInfo;
import com.ecasona.newapi.pay.model.PayType;
import com.ecasona.newapi.pay.model.ali.PayResult;
import com.ecasona.newapi.pay.pays.IPayable;
import com.ecasona.newapi.pay.pays.PaysFactory;


/**
 * Created by aiy on 2016/10/12.
 * <p>
 * des:支付宝调用
 */

public class AppAliPay implements AppPay {

    private AppAliPay() {
    }

    private static AppAliPay instance;
    private static IPayable iPayable;

    public synchronized static AppAliPay getInstance() {
        if (null == instance) {
            instance = new AppAliPay();
            iPayable = PaysFactory.GetInstance(PayType.AliPay);
        }
        return instance;
    }

    public void pay(Activity activity,
                    String body,
                    String invalidTime,
                    String notifyUrl,
                    String tradeNo,
                    String subject,
                    String totalFee,
                    String spbillCreateIp) {
        PayListener payListener = null;
        if (activity instanceof PayListener) {
            payListener = (PayListener) activity;
        }
        payToAli(activity, iPayable.buildOrderInfo(body, invalidTime, notifyUrl, tradeNo, subject, totalFee, spbillCreateIp), payListener);
    }

    private void payToAli(final Activity activity, final OrderInfo orderInfo, final PayListener payListener) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String result = iPayable.pay(activity, orderInfo, null);
                if (null == result) {
                    returnResult(payListener, "支付失败");
                    return;
                }
                String resultStatus = new PayResult(result).getResultStatus();

                if (TextUtils.equals(resultStatus, "9000")) {
                    returnResult(payListener, "支付成功");

                } else {
                    // 判断resultStatus 为非“9000”则代表可能支付失败
                    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        returnResult(payListener, "支付结果确认中");

                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        returnResult(payListener, "支付失败");
                    }
                }
            }

        };
        new Thread(runnable).start();

    }

    private void returnResult(PayListener payListener, String msg) {
        if (null != payListener) {
            payListener.success(msg);
        }
    }
}
