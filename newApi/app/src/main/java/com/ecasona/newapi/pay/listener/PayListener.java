package com.ecasona.newapi.pay.listener;

/**
 * Created by aiy on 2016/7/15.
 * <p>
 * 支付结果监听
 */

public interface PayListener {

    void success(Object object);

    void failed(Object object);
}
