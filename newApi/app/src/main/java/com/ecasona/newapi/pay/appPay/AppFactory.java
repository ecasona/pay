package com.ecasona.newapi.pay.appPay;

import android.content.Context;

import com.ecasona.newapi.pay.appPay.presenter.AppPay;
import com.ecasona.newapi.pay.appPay.presenter.impl.AppAliPay;
import com.ecasona.newapi.pay.appPay.presenter.impl.AppWeixinPay;
import com.ecasona.newapi.pay.model.PayType;

/**
 * Created by aiy on 2016/10/12.
 * <p>
 * des:支付实例创建
 */

public class AppFactory {

    /**
     * 支付实例创建
     *
     * @param context context
     * @param payType 调用支付枚举
     * @return
     */
    public static AppPay getInstance(Context context, PayType payType) {
        AppPay pay = null;
        switch (payType) {
            case AliPay:
                pay = AppAliPay.getInstance();
                break;
            case WeixinPay:
                pay = AppWeixinPay.getInstance(context);
            default:
                break;
        }
        return pay;
    }
}
