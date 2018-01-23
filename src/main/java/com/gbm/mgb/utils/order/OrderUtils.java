package com.gbm.mgb.utils.order;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 订单号生成工具类
 * 
 */
public class OrderUtils
{

	private static Random randGen = null;
	private static char[] numbersAndLetters = null;

	/**
	 * 生成指定长度随机字段串
	 * 
	 * @param length
	 *            长度
	 * @return 返回随机字符串
	 */
	public static final String randomString(int length)
	{
		if (length < 1)
		{
			return null;
		}
		if (randGen == null)
		{
			randGen = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++)
		{
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static Map<String, String> payConfigMap = new HashMap<String, String>();

	/**
	 * 生成订单号
	 * 
	 * @param orderCount
	 *            订单总量
	 * @return 返回訂單號
	 */
	public static String getOrderNo(Integer orderCount)
	{
		StringBuffer orderNo = new StringBuffer();
		Integer count = orderCount + 1;
		orderNo.append(DateUtils.strToDate4(new Date()));
		String sequ = new DecimalFormat("00000").format(count);
		orderNo.append(sequ);
		return orderNo.toString();
	}

	/**
	 * @Description: 创建订单编号
	 * @param
	 * @return String
	 * @author angelo
	 * @date 2015-11-4
	 */
	public static String createOrderNumber()
	{
		return DateUtils.getCurrDate("yyyyMMddHHmmssSSS") + RandomCode.getSixRandCode();
	}

	/**
	 * 根据不同的支付类型获取回调URL
	 * 
	 * @param payType
	 *            类型
	 * @return 返回URL
	 */
	public static Map<String, String> getPayConfig(Integer payType)
	{
		Map<String, String> map = new HashMap<String, String>();
		if (payType == 1)
		{
			// 支付宝
			map.put("notifyUrl", payConfigMap.get("alipay_notifyUrl"));
			map.put("parterId", payConfigMap.get("alipay_parterId"));
			map.put("seller_id", payConfigMap.get("alipay_Seller_id"));
			map.put("privateKey", payConfigMap.get("alipay_privateKey"));
			map.put("synUrl", "");
			map.put("desKey", "");
		}
		else if (payType == 2)
		{
			// 财富通
			map.put("notifyUrl", payConfigMap.get("tenpay_notifyUrl"));
			map.put("parterId", payConfigMap.get("tenpay_parterId"));
			map.put("seller_id", "");
			map.put("privateKey", "");
			map.put("synUrl", "");
			map.put("desKey", "");
		}
		else if (payType == 3)
		{
			// 银联
			map.put("notifyUrl", payConfigMap.get("chinaUnionPay_notifyUrl"));
			map.put("parterId", payConfigMap.get("chinaUnionPay_parterId"));
			map.put("seller_id", "");
			map.put("privateKey", "");
			map.put("synUrl", "");
			map.put("desKey", "");
		}
		else if (payType == 4)
		{
			// 神州付
			map.put("notifyUrl", payConfigMap.get("shenzhouPay_notifyUrl"));
			map.put("parterId", payConfigMap.get("shenzhouPay_parterId"));
			map.put("seller_id", payConfigMap.get("merId"));
			map.put("privateKey", payConfigMap.get("privateKey"));
			map.put("synUrl", "");
			map.put("desKey", payConfigMap.get("desKey"));
		}
		else if (payType == 5)
		{
			// 支付宝国际网关
			map.put("notifyUrl", payConfigMap.get("alipay_gj_notifyUrl"));
			map.put("parterId", payConfigMap.get("alipay_parterId_gj"));
			map.put("seller_id", payConfigMap.get("alipay_Seller_id_gj"));
			map.put("privateKey", payConfigMap.get("alipay_privateKey_gj"));
			map.put("synUrl", payConfigMap.get("alipay_gj_synUrl"));
			map.put("desKey", "");
		}
		else if (payType == 6)
		{
			// PALPAY支付
			map.put("notifyUrl", payConfigMap.get("palpay_notifyUrl"));
			map.put("parterId", payConfigMap.get("palpay_parterId"));
			map.put("seller_id", payConfigMap.get("palpay_Seller_id"));
			map.put("privateKey", payConfigMap.get("palpay_privateKey"));
			map.put("synUrl", "");
			map.put("desKey", "");
		}
		return map;
	}

}
