package com.ecasona.newapi.pay.pays;

import com.ecasona.newapi.pay.model.PayType;
import com.ecasona.newapi.pay.pays.ali.AliPay;
import com.ecasona.newapi.pay.pays.weixin.WeixinPay;


public class PaysFactory {
	
	public static IPayable GetInstance(PayType payType){
		IPayable pay=null;
		switch (payType) {
		case AliPay:
			pay=new AliPay();
			break;
		case WeixinPay:
			pay = new WeixinPay();
		default:
			break;
		}
		return pay;
	}
}
