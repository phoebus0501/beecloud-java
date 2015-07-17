# beecloud-java-sdk
BeeCloud Java SDK (Open Source)

## <a name="payment">支付</a>

调用以下接口发起支付并将得到BCPayResult对象，BCPayResult对象包含两种状态，正确状态和错误状态，正确状态的BCPayResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErr_detail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。
#### <a name="wx_native">微信扫码调用</a>
正确状态调用getCode_url()方法返回二维码字符串，返回code_url的格式为：weixin://wxpay/bizpayurl?sr=XXXXX。
请商户调用第三方库将返回的code_url生成二维码图片。
该模式链接较短，生成的二维码打印到结账小票上的识别率较高。
```java
bcPayResult = BCPay.startBCPay(PAY_CHANNEL.WX_NATIVE, 1, bill_no, "买水", null, null, null , null, null);
if (bcPayResult.getType().ordinal() == 0) {
	System.out.println(bcPayResult.getCode_url());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErr_detail());
}
```
#### <a name="wx_jsapi">微信公众号调用</a>
正确状态调用getWxJSAPIMap()方法返回jsapi map对象。
```java
bcPayResult = BCPay.startBCPay(PAY_CHANNEL.WX_JSAPI, 1, bill_no, "买水", null, null, "openid000000001", null, null);
if (bcPayResult.getType().ordinal() == 0) {
	System.out.println(bcPayResult.getWxJSAPIMap());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErr_detail());
}
```

#### <a name="un_web">银联网页调用</a>
正确状态调用getHtml()方法，如将html输出至页面，即可开始银联网页支付。
```java
bcPayResult = BCPay.startBCPay(PAY_CHANNEL.UN_WEB, 1, bill_no, "买水", null, return_url, null, null, null);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErr_detail());
}
```

#### <a name="ali_web">阿里网页调用</a>
正确状态调用getHtml()方法或者getUrl()方法，getHtml()方法返回html,如将html输出至页面，即可开始支付宝网页支付。getUrl()方法返回支付宝跳转url,推荐使用html。
```java
bcPayResult = BCPay.startBCPay(PAY_CHANNEL.ALI_WEB, 1, bill_no, "农夫山泉", null, "http://beecloud.cn", null, null, null);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
	out.println(bcPayResult.getUrl());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErr_detail());
}
```

#### <a name="ali_qrcode">阿里扫码调用</a>
正确状态调用getHtml()方法或者getUrl()方法，getHtml()方法返回html,如将html输出至页面，即可开始支付。getUrl()方法返回支付宝内嵌二维码地址。
```java
bcPayResult = BCPay.startBCPay(PAY_CHANNEL.ALI_QRCODE, 1, bill_no, "农夫山泉", null, "http://beecloud.cn", null, null, null);
if (bcPayResult.getType().ordinal() == 0) {
	out.println(bcPayResult.getHtml());
	out.println(bcPayResult.getUrl());
}
else {
	//handle the error message as you wish！
	out.println(bcPayResult.getErrMsg());
	out.println(bcPayResult.getErr_detail());
}
```

代码中的各个参数含义如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：WX_NATIVE 微信公众号二维码支付<br/>WX_JSAPI 微信公众号支付<br/>ALI_WEB 支付宝网页支付<br/>ALI_QRCODE 支付宝内嵌二维码支付<br/>UN_WEB 银联网页支付， （必填）
total_fee | 订单总金额， 只能为整数，单位为分，例如 1，（必填）
bill_no | 商户订单号, 32个字符内，数字和/或字母组合，确保在商户系统中唯一, 例如(201506101035040000001),（必填）
title | 订单标题， 32个字节内，最长支持16个汉字，（必填）
optional | 附加数据， 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据，（选填）
return_url | 同步返回页面	， 支付渠道处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径。当 channel 参数为 ALI_WEB 或 ALI_QRCODE 或 UN_WEB时为必填，（选填）
openid | 微信公众号支付(WX_JSAPI)必填，（选填）
show_url | 商品展示地址，需以http://开头的完整路径，例如：http://www.商户网址.com/myorder，（选填）
qr_pay_mode | 二维码类型，二维码类型含义MODE_BRIEF_FRONT： 订单码-简约前置模式, 对应 iframe 宽度不能小于 600px, 高度不能小于 300px<br>MODE_FRONT： 订单码-前置模式, 对应 iframe 宽度不能小于 300px, 高度不能小于 600px<br>MODE_MINI_FRONT： 订单码-迷你前置模式, 对应 iframe 宽度不能小于 75px, 高度不能小于 75px ，（选填）
return   |  BCPayResult对象， 根据type决定返回内容


## <a name="refund">退款</a>
调用以下接口发起退款并将得到BCPayResult对象，BCPayResult对象包含两种状态，正确状态和错误状态，正确状态的BCPayResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErr_detail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。。

#### <a name="wx_refund">微信调用</a>
正确状态调用getSucessMsg()方法获知微信退款调用成功。

```java
BCPayResult result = BCPay.startBCRefund(PAY_CHANNEL.WX, "201507170000", bill_no, 2, null);；
if (bcPayResult.getType().ordinal() == 0) {
    //返回"退款成功！" 
	out.println(bcPayResult.getSucessMsg());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getResult());
	out.println(bcPayResult.getErr_detail());
}
```

#### <a name="ali_refund">阿里调用</a>
正确状态调用getUrl()方法返回url并跳转输入邮箱地址和密码完成退款。

```java
BCPayResult result = BCPay.startBCRefund(PAY_CHANNEL.ALI, "201507170000", bill_no, 1, null);；
if (bcPayResult.getType().ordinal() == 0) {
	response.redirect(bcPayResult.getUrl());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getResult());
	out.println(bcPayResult.getErr_detail());
}
```

#### <a name="un_refund">银联调用</a>
正确状态调用getSucessMsg()方法获知银联退款的返回结果。

```java
BCPayResult result = BCPay.startBCRefund(PAY_CHANNEL.UN, "201507170000", bill_no, 1, null);；
if (bcPayResult.getType().ordinal() == 0) {
	response.redirect(bcPayResult.getSucessMsg());
} else {
	//handle the error message as you wish！
	out.println(bcPayResult.getResult());
	out.println(bcPayResult.getErr_detail());
}
```

代码中的各个参数含义如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX  微信<br>ALI 支付宝<br>UN 银联，（必填）
refund_no | 商户退款单号	， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”，例如：201506101035040000001	（必填）
bill_no | 商户订单号， 32个字符内，数字和/或字母组合，确保在商户系统中唯一，（必填）  
refund_fee | 退款金额，只能为整数，单位为分，例如1，（必填）  
optional   |  附加数据 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据，例如{"key1":"value1","key2":"value2",...}, （选填）
return | BCPayResult, 根据type决定返回内容

## <a name="payment">订单查询</a>

调用以下接口发起订单查询并将得到BCQueryResult对象，BCQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCPayResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErr_detail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。调用参数中，channel参数包含以下取值：
WX、WX_NATIVE、WX_JSAPI、ALI、ALI_APP、ALI_WEB、ALI_QRCODE、UN、UN_APP、UN_WEB。其中WX、ALI、UN是其他子渠道的父渠道，返回的是各种子渠道返回结果的并集。

正确状态调用bcQueryResult.getBcOrders()方法返回订单(BCOrderBean)的list集合。调用者可任意遍历，显示这个订单的list对象。

```java
bcQueryResult = BCPay.startQueryBill(channel, null, null, null, null, null);
if (bcQueryResult.getType().ordinal() == 0) {
    //handle the order list as you wish.
	pageContext.setAttribute("bills", bcQueryResult.getBcOrders());
} else {
	out.println(bcQueryResult.getErrMsg());
	out.println(bcQueryResult.getErr_detail());
}
```
## <a name="payment">退款查询</a>
调用以下接口发起退款查询并将得到BCQueryResult对象，BCQueryResult对象包含两种状态，正确状态和错误状态，正确状态的BCPayResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErr_detail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。调用参数中，channel参数包含以下取值：
WX、WX_NATIVE、WX_JSAPI、ALI、ALI_APP、ALI_WEB、ALI_QRCODE、UN、UN_APP、UN_WEB。其中WX、ALI、UN是其他子渠道的父渠道，返回的是各种子渠道返回结果的并集。

正确状态调用bcQueryResult.getBcRefundList()方法返回退款记录(BCRefundBean)的list集合。调用者可任意遍历，显示这个退款记录的list对象。

```java
bcQueryResult = BCPay.startQueryRefund(PAY_CHANNEL.UN, null, null, null, null, null, null);
if (bcQueryResult.getType().ordinal() == 0) {
	pageContext.setAttribute("refundList", bcQueryResult.getBcRefundList());
}else {
	out.println(bcQueryResult.getErrMsg());
	out.println(bcQueryResult.getErr_detail());
}
```

代码中的各个参数含义如下：

key | 说明
---- | -----
channel | 渠道类型， 根据不同场景选择不同的支付方式，包含：<br>WX<br>WX_APP 微信手机APP支付<br>WX_NATIVE 微信公众号二维码支付<br>WX_JSAPI 微信公众号支付<br>ALI<br>ALI_APP 支付宝APP支付<br>ALI_WEB 支付宝网页支付<br>ALI_QRCODE 支付宝内嵌二维码支付<br>UN<br>UN_APP 银联APP支付<br>UN_WEB 银联网页支付，（必填）
bill_no | 商户订单号， 32个字符内，数字和/或字母组合，确保在商户系统中唯一，	（选填）
start_time | 起始时间， 毫秒时间戳, 13位，（选填）  
end_time | 结束时间， 毫秒时间戳, 13位，（选填）  
skip   |  查询起始位置	 默认为0。设置为10，表示忽略满足条件的前10条数据	, （选填）
limit |  查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据	
return | BCQueryResult, 根据type决定返回内容



## <a name="payment">微信退款状态查询</a>
调用以下接口发起微信退款状态查询并将得到BCQueryStatusResult对象，BCQueryStatusResult对象包含两种状态，正确状态和错误状态，正确状态的BCQueryStatusResult的type类型字符串为OK, 对应值为0。错误状态调用getErrMsg()方法返回错误信息。调用getErr_detail()方法返回具体错误信息，开发者可任意显示，打印，或者进行日志。调用参数中，channel参数包含以下取值：
WX、WX_NATIVE、WX_JSAPI、ALI、ALI_APP、ALI_WEB、ALI_QRCODE、UN、UN_APP、UN_WEB。其中WX、ALI、UN是其他子渠道的父渠道，返回的是各种子渠道返回结果的并集。

正确状态调用getRefundStatus()方法返回微信退款状态(Success, Processing, Fail ...)。调用者可任意处理这个值。

```java
BCQueryStatusResult result = BCPay.startWeChatRefundStatusQuery(refund_no);
if (result.getType().ordinal() == 0 ) {
	out.println(result.getRefundStatus());
} else {
	out.println(result.getErrMsg());
	out.println(result.getErr_detail());
}
```
代码中的各个参数含义如下：

key | 说明
---- | -----
refund_no | 商户退款单号， 格式为:退款日期(8位) + 流水号(3~24 位)。不可重复，且退款日期必须是退款发起当日日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。，（必填）
