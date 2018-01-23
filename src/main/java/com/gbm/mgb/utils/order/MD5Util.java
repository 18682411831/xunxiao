package com.gbm.mgb.utils.order;


import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/*
 * MD5加密工具类
 * 
 * @author : 刘泽
 * @crtDate: 2013-06-03
 */
public class MD5Util {

	/**
	 * 给指定的字符串进行MD5加密
	 * 
	 * @param plainText
	 *            需要加密的串
	 * @return 返回加密后的串
	 * @throws UnsupportedEncodingException
	 */
	public static String getMd5String(String plainText)
			throws UnsupportedEncodingException {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes("UTF-8"));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	 public static String getSign(Map<String, Object> params, String secretKey){
        //将参数以参数名的字典升序排序
        Map<String, Object> sortParams = new TreeMap<String, Object>(params);
        Set<Map.Entry<String, Object>> entrys = sortParams.entrySet();

        //遍历排序的字典,并拼接"key=value"格式
        StringBuilder baseString = new StringBuilder();
        for(Map.Entry<String, Object> entry:entrys){
            baseString.append(entry.getKey()).append("=").append(entry.getValue());
        }
        baseString.append(secretKey);

        //使用MD5对待签名串求签
        byte[] bytes = null;
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            try {
                bytes = md5.digest(baseString.toString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            String hex = Integer.toHexString(bytes[i]&0xFF);
            if(hex.length() == 1){
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

    /**
     * 根据传入的jsonObject 和appkey进行MD5加密
     * @param jsonObject
     * @param appKey
     * @return
     */
    public static String getSignForJson(JSONObject jsonObject,String appKey){
        StringBuilder baseString = new StringBuilder();
        Iterator<String> sIterator = jsonObject.keys();
        while (sIterator.hasNext()){
            // 获得key
            String key = sIterator.next();
            baseString.append(key).append("=").append(jsonObject.getString(key));
        }
        baseString.append(appKey);

        //使用MD5对待签名串求签
        byte[] bytes = null;
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            try {
                bytes = md5.digest(baseString.toString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            String hex = Integer.toHexString(bytes[i]&0xFF);
            if(hex.length() == 1){
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }


	public static void main(String[] args) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("best", "morning");
        params.put("appName", "tencent");
        params.put("appValue", "1024");
        String secretKey = "1024";
        String sign = getSign(params, secretKey);
        System.out.println(sign);

    }

}
