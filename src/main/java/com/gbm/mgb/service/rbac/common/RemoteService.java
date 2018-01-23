package com.gbm.mgb.service.rbac.common;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RemoteService {
	private static final Logger logger = LoggerFactory.getLogger(RemoteService.class);

	public static String sendMessage(String phoneNumber,String type) {
		try {
			HttpClient httpClient = new HttpClient();
			//接口请求地址
			String url = "http://112.74.215.56:7099/xunxiao-sms/channel/sendMssage";
			PostMethod postMethod = new PostMethod(url);
			postMethod.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			// 封裝参数信息
			String vcode = RemoteService.createRandomVcode();
			//logger.info(arg0);
			//System.out.println(vcode);
			//String str = URLEncoder.encode("【迅销科技】尊敬客户您注册的短信验证码为："+vcode+"，2分钟内有效。", "utf-8");
			postMethod.setParameter("phoneNumber", phoneNumber);
			postMethod.setParameter("messageContent", vcode);
			postMethod.setParameter("templateId", type);
			// 请求接口并返回
			httpClient.executeMethod(postMethod);
			String response = postMethod.getResponseBodyAsString();
			//System.out.println(response);
			postMethod.releaseConnection();
			return vcode;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	/**
     * 随机生成6位随机验证码
      * 方法说明
      * @Discription:扩展说明
      * @return
      * @return String
     */
    public static String createRandomVcode(){
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 10);
        }
        return vcode;
    }
	
	public static String httpPost(String url,Map<String, Object> param) {
		String result = "";
		try {
			HttpClient client = new HttpClient();
			PostMethod postMethod = new PostMethod(url);
			postMethod.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			for (Map.Entry<String, Object> set : param.entrySet()) {
				String key = set.getKey();
			    String value = set.getValue() == null ? "" : set.getValue()
			            .toString();
				postMethod.setParameter(key, value);
			}
			// 请求接口并返回
			client.executeMethod(postMethod);
			result = postMethod.getResponseBodyAsString();
			logger.info("返回结果是:" + result);
			postMethod.releaseConnection();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static String sendWarningMessage(String warningMobile, String type, String messageContent) {
		try {
			HttpClient httpClient = new HttpClient();
			//接口请求地址
			String url = "http://112.74.215.56:7099/xunxiao-sms/channel/sendMssage";
			PostMethod postMethod = new PostMethod(url);
			postMethod.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			// 封裝参数信息
			postMethod.setParameter("phoneNumber", warningMobile);
			postMethod.setParameter("messageContent", messageContent);
			postMethod.setParameter("templateId", type);
			// 请求接口并返回
			int executeMethod = httpClient.executeMethod(postMethod);
			String response = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
			return response;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String sendCodeMessage(String phoneNumber,String messageContent,String type) {
		try {
			HttpClient httpClient = new HttpClient();
			//接口请求地址
			String url = "http://112.74.215.56:7099/xunxiao-sms/channel/sendMssage";
			PostMethod postMethod = new PostMethod(url);
			postMethod.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			// 封裝参数信息
			postMethod.setParameter("phoneNumber", phoneNumber);
			postMethod.setParameter("messageContent", messageContent);
			postMethod.setParameter("templateId", type);
			// 请求接口并返回
			httpClient.executeMethod(postMethod);
			String response = postMethod.getResponseBodyAsString();
			//System.out.println(response);
			postMethod.releaseConnection();
			return "ok";
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
}
