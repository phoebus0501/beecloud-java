package cn.beecloud;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.RESULT_TYPE;

/**
 * This class is used to unify the validation for all the
 * incoming parameters of payment.
 * @author Ray
 * @version 0.1
 * @since 2015/6/11 
 */
public class ValidationUtil 
{
	private final static String BILL_NO_FORMAT_INVALID =
			"bill_no 是一个长度不超过32字符的数字字母字符串！";
	
	private final static String BILL_NO_EMPTY =
			"bill_no 必填！";
	
	private final static String TITLE_EMPTY =
			"title 必填！";
	
	private final static String RETURN_URL_EMPTY = 
			"return_url 必填！";
	
	private final static String REFUND_NO_EMPTY =
			"refund_no 必填！";
	
	private final static String CHANNEL_EMPTY =
			"channel 必填！";
	
	private final static String REFUND_NO_FORMAT_INVALID =
			"refund_no 是格式为当前日期加3-24位数字字母（不能为000）流水号的字符串！ ";
	
	private final static String TITLE_FORMAT_INVALID =
			"title 是一个长度不超过32字节的字符串！";
	
	private final static String LIMIT_FORMAT_INVALID =
			"limit 的最大长度为50！";
	
	private final static String OPENID_EMPTY =
			"openid 必填！";
	
	private final static String CHANNEL_INVALID_FOR_REFUND =
			"退款只支持WX, UN, ALI !";
	
	final static String PRE_REFUND_SUCCEED = "预退款成功！ ";
	
	final static String REFUND_REJECT = "退款被拒绝！ ";
	
	final static String REFUND_ACCEPT = "退款被同意！ ";
	
	public static BCPayResult validateResultFromBackend(Map<String, Object> ret) {
		
		BCPayResult bcPayResult = new BCPayResult();
		bcPayResult.setErrMsg(ret.get("errMsg").toString());
		bcPayResult.setErrDetail(ret.get("err_detail").toString());
		bcPayResult.setType(RESULT_TYPE.RUNTIME_ERROR);

		return bcPayResult;
	}

	public static BCPayResult validateBCPay(PAY_CHANNEL channel, String billNo, String title, String returnUrl, String openId) {
		if (channel == null) {
			return new BCPayResult(CHANNEL_EMPTY, RESULT_TYPE.VALIDATION_ERROR);
		} else if (StrUtil.empty(billNo)) {
			return new BCPayResult(BILL_NO_EMPTY, RESULT_TYPE.VALIDATION_ERROR);
		} else if (!billNo.matches("[0-9A-Za-z]{1,32}")) {
			return new BCPayResult(BILL_NO_FORMAT_INVALID, RESULT_TYPE.VALIDATION_ERROR);
		} else if (StrUtil.empty(title)) {
			return new BCPayResult(TITLE_EMPTY, RESULT_TYPE.VALIDATION_ERROR);
		} else if (StrUtil.empty(returnUrl) && 
				(channel.equals(PAY_CHANNEL.ALI_WEB) || 
						channel.equals(PAY_CHANNEL.ALI_QRCODE) || 
							channel.equals(PAY_CHANNEL.UN_WEB))) {
			return new BCPayResult(RETURN_URL_EMPTY, RESULT_TYPE.VALIDATION_ERROR);
		} else if (channel.equals(PAY_CHANNEL.WX_JSAPI) && StrUtil.empty(openId)){
			return new BCPayResult(OPENID_EMPTY, RESULT_TYPE.VALIDATION_ERROR);
		} else
			try {
				if (title.getBytes("GBK").length > 32) {
					return new BCPayResult(TITLE_FORMAT_INVALID, RESULT_TYPE.VALIDATION_ERROR);
				}
			} catch (UnsupportedEncodingException e) {
				if (title.length() > 16) {
					return new BCPayResult(TITLE_FORMAT_INVALID, RESULT_TYPE.VALIDATION_ERROR);
				}
			}
		
		return new BCPayResult(RESULT_TYPE.OK);	
	}

	public static BCPayResult validateBCRefund(PAY_CHANNEL channel,
			String refundNo, String billNo) {
		 if (channel == null) {
			return new BCPayResult(CHANNEL_EMPTY, RESULT_TYPE.VALIDATION_ERROR);
		 } else if (!channel.equals(PAY_CHANNEL.WX) && !channel.equals(PAY_CHANNEL.ALI) && !channel.equals(PAY_CHANNEL.UN)) {
			 return new BCPayResult(CHANNEL_INVALID_FOR_REFUND, RESULT_TYPE.VALIDATION_ERROR);
		 } else if (StrUtil.empty(refundNo)) {
			return new BCPayResult(REFUND_NO_EMPTY, RESULT_TYPE.VALIDATION_ERROR);
		 } else if (!refundNo.startsWith(new SimpleDateFormat("yyyyMMdd").format(new Date()))){
			return new BCPayResult(REFUND_NO_FORMAT_INVALID, RESULT_TYPE.VALIDATION_ERROR);
		 } else if (!refundNo.substring(8, refundNo.length()).matches("[0-9A-Za-z]{3,24}") || 
				 refundNo.substring(8, refundNo.length()).matches("000") ){
			return new BCPayResult(REFUND_NO_FORMAT_INVALID, RESULT_TYPE.VALIDATION_ERROR);
		 } else if (StrUtil.empty(billNo)) {
			return new BCPayResult(BILL_NO_EMPTY, RESULT_TYPE.VALIDATION_ERROR);
		 } else if (!billNo.matches("[0-9A-Za-z]{1,32}")) {
			return new BCPayResult(BILL_NO_FORMAT_INVALID, RESULT_TYPE.VALIDATION_ERROR);
		 } 
		 
		 return new BCPayResult(RESULT_TYPE.OK);	
	}

	public static BCQueryResult validateQueryBill(PAY_CHANNEL channel,
			String billNo, Integer limit) {
		 if (channel == null) {
			return new BCQueryResult(CHANNEL_EMPTY, RESULT_TYPE.VALIDATION_ERROR);
		 } else if (!StrUtil.empty(billNo) && !billNo.matches("[0-9A-Za-z]{1,32}")) {
			return new BCQueryResult(BILL_NO_FORMAT_INVALID, RESULT_TYPE.VALIDATION_ERROR);
		 } else if (limit != null && limit > 50) {
			return new BCQueryResult(LIMIT_FORMAT_INVALID, RESULT_TYPE.VALIDATION_ERROR);
		 }
		 
		 return new BCQueryResult(RESULT_TYPE.OK);
	}

	public static BCQueryResult validateQueryRefund(PAY_CHANNEL channel, String billNo,
			String refundNo, Integer limit) {
		if (channel == null) {
			return new BCQueryResult(CHANNEL_EMPTY, RESULT_TYPE.VALIDATION_ERROR);
		} else if (!StrUtil.empty(billNo) && !billNo.matches("[0-9A-Za-z]{1,32}")) {
			return new BCQueryResult(BILL_NO_FORMAT_INVALID, RESULT_TYPE.VALIDATION_ERROR);
		} else if (!StrUtil.empty(refundNo) && (!refundNo.substring(8, refundNo.length()).matches("[0-9A-Za-z]{3,24}") || 
				refundNo.substring(8, refundNo.length()).matches("000")) ) {
			return new BCQueryResult(REFUND_NO_FORMAT_INVALID, RESULT_TYPE.VALIDATION_ERROR);
		} else if (limit != null && limit > 50) {
			return new BCQueryResult(LIMIT_FORMAT_INVALID, RESULT_TYPE.VALIDATION_ERROR);
		}
		
		return new BCQueryResult(RESULT_TYPE.OK);
	}

	public static BCQueryStatusResult validateQueryRefundStatus(
			String refundNo) {
		if (StrUtil.empty(refundNo)) {
			return new BCQueryStatusResult(REFUND_NO_EMPTY, RESULT_TYPE.VALIDATION_ERROR);
		}
		return new BCQueryStatusResult(RESULT_TYPE.OK);
	}
		
}
