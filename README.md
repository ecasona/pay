本项目是微信支付和支付宝支付的封装
    使用前须在com.ecasona.newapi.pay.model.KeyLibs中配置微信和支付宝的相关信息。
    然后调用com.ecasona.newapi.pay.appPay.AppPayManager 中的pay(...)方法即可。


详解:
  具体调用支付宝和微信接口是在pays包中，以IPayable为规范通过AliPay和WeixinPay两个类实现，具体调用，可查看相关文档。
