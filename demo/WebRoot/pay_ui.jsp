<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	/* *
	 功能：商户结算页面
	 版本：3.3
	 日期：2015-03-20
	 说明：
	 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
	 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	 本Demo仅支持非IE浏览器，秒支付SDK无此限制。

	 //***********页面功能说明***********
	 该页面可以在本机电脑测试。
	 UI页面
	 //********************************
	 * */
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>支付页面</title>
<style>
.clear:after {
	content: ".";
	display: block;
	clear: both;
	visibility: hidden;
	line-height: 0;
	height: 0;
}

html {
	width: 100%;
}

body {
	margin: 0;
	padding: 0;
	width: 100%;
	color: #111;
	font-family: "PingHei", STHeitiSC-Light, "Lucida Grande",
		"Lucida Sans Unicode", Helvetica, Arial, Verdana, sans-serif;
	font-size: 1em;
}

ul {
	list-style: none;
	padding: 0;
	margin: 0;
	width: 100%;
}

ul li {
	float: left;
	margin: 0 1em;
}

ul li img {
	cursor: pointer;
	width: 158px;
	border: rgba(0, 0, 0, 0.2) 2px solid;
}

ul li img:hover {
	box-shadow: 0 0 2px #0CA6FC;
	border: #0CA6FC 2px solid;
}

.button {
	cursor: pointer;
	display: block;
	line-height: 45px;
	text-align: center;
	width: 158px;
	height: 45px;
	margin-top: 1.5em;
	border: rgba(123, 170, 247, 1) 1px solid;
	color: #fff;
	font-size: 1.2em;
	border-top-color: #1992da;
	border-left-color: #0c75bb;
	border-right-color: #0c75bb;
	border-bottom-color: #00589c;
	-webkit-box-shadow: inset 0 1px 1px 0 #6fc5f5;
	-moz-box-shadow: inset 0 1px 1px 0 #6fc5f5;
	box-shadow: inset 0 1px 1px 0 #6fc5f5;
	background: #117ed2;
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#37aaea",
		endColorstr="#117ed2");
	background: -webkit-gradient(linear, left top, left bottom, from(#37aaea),
		to(#117ed2));
	background: -moz-linear-gradient(top, #37aaea, #117ed2);
	background-image: -o-linear-gradient(top, #37aaea 0, #117ed2 100%);
	background-image: linear-gradient(to bottom, #37aaea 0, #117ed2 100%);
}

.button:hover {
	background: #1c5bad;
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#2488d4",
		endColorstr="#1c5bad");
	background: -webkit-gradient(linear, left top, left bottom, from(#2488d4),
		to(#1c5bad));
	background: -moz-linear-gradient(top, #2488d4, #1c5bad);
	background-image: -o-linear-gradient(top, #2488d4 0, #1c5bad 100%);
	background-image: linear-gradient(to bottom, #2488d4 0, #1c5bad 100%);
	-webkit-box-shadow: inset 0 1px 1px 0 #64bef1;
	-moz-box-shadow: inset 0 1px 1px 0 #64bef1;
	box-shadow: inset 0 1px 1px 0 #64bef1;
}

li.clicked img {
	box-shadow: 0 0 2px #0CA6FC;
	border: #0CA6FC 2px solid;
}

input {
	display: none;
}
</style>

</head>

<body>
	<div>
		<h2>应付总额： ¥0.01</h2>
		<p>请选择支付方式</p>
	</div>
	<div>
		支付平台<span>(推荐支付宝、微信用户使用)</span>
	</div>
	<form action="redirect.jsp" method="POST" target="_blank">
		<div>
			<ul class="clear" style="margin-top:20px">
				<li class="clicked" onclick="paySwitch(this)">
			   		<input type="radio"  value="alipay" name="paytype" checked="checked">
			    	<img src="http://beeclouddoc.qiniudn.com/ali.png" alt="">
		        </li>
				<li onclick="paySwitch(this)">
					<input type="radio"  value="wechatQr" name="paytype">
					<img src="http://beeclouddoc.qiniudn.com/wechats.png" alt="">
				</li>
				<li onclick="paySwitch(this)">
					<input type="radio"  value="unionpay" name="paytype">
					<img src="http://beeclouddoc.qiniudn.com/unionpay.png" alt="">
				</li>
				<li onclick="paySwitch(this)">
					<input type="radio"  value="alipayQr" name="paytype">
					<img src="http://beeclouddoc.qiniudn.com/alis.png" alt="">
				</li>
				<li onclick="paySwitch(this)">
					<input type="radio"  value="alipayWAP" name="paytype">
					<img src="http://beeclouddoc.qiniudn.com/aliwap.png" alt="">
				</li>
				<!-- i onclick="paySwitch(this)">
					<input type="radio"  value="wechatJSAPI" name="paytype">
					<img src="http://7xavqo.com1.z0.glb.clouddn.com/wechatgzh.png" alt="">
				</li-->
  		    </ul>
		</div>
		<div style="clear: both;">
			<input type="submit" class="button" value="确认付款">
		</div>
	</form>
	
	<hr/>
	<div>
		<h2>订单查询及发起退款，退款查询，微信退款状态查询</h2>
		<p>请选择渠道进行操作</p>
	</div>
	
	<form action="query.jsp" method="POST" target="_blank">
		<div>
			<ul class="clear" style="margin-top:20px">
				<li class="clicked" onclick="querySwitch(this)">
			   		<input type="radio"  value="aliQuery" name="querytype" checked="checked">
			    	<img src="http://beeclouddoc.qiniudn.com/ali.png" alt="">
		        </li>
				<li onclick="querySwitch(this)">
					<input type="radio"  value="wechatQuery" name="querytype">
					<img src="http://beeclouddoc.qiniudn.com/wechat.png" alt="">
				</li>
				<li onclick="querySwitch(this)">
					<input type="radio"  value="unionQuery" name="querytype">
					<img src="http://beeclouddoc.qiniudn.com/unionpay.png" alt="">
				</li>
  		    </ul>
		</div>
		<div style="clear: both;">
			<input name="queryBIll" type="submit" class="button" value="订单查询">
			<input name="queryRefund" type="submit" class="button" value="退款查询">
		</div>
	</form>
	
</body>
<script type="text/javascript">
	function paySwitch(that) {
		var li = that.parentNode.children;
		for(var i =0;i < li.length; i++) {
		 	li[i].className = "";
		 	li[i].childNodes[1].removeAttribute("checked");
		}
		console.log(li);
		that.className = "clicked";
		that.childNodes[1].setAttribute("checked", "checked");
	}
	function querySwitch(that) {
		var li = that.parentNode.children;
		for(var i =0;i < li.length; i++) {
		 	li[i].className = "";
		 	li[i].childNodes[1].removeAttribute("checked");
		}
		console.log(li);
		that.className = "clicked";
		that.childNodes[1].setAttribute("checked", "checked");
	}
</script>
</html>
