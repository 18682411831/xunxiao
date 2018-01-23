package com.gbm.mgb.utils.order;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Waylon on 2017/3/15.
 */
public class HttpClientUtils {
    public static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    private static Map<String, String> headers = new HashMap<String, String>();
    static {
        headers.put("User-Agent",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
        headers.put("Accept-Language", "zh-cn,zh;q=0.5");
        headers.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
        headers.put(
                "Accept",
                " image/gif, image/x-xbitmap, image/jpeg, "
                        + "image/pjpeg, application/x-silverlight, application/vnd.ms-excel, "
                        + "application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
    
    }

    /**
     * 异常或者没拿到返回结果的情况下,result为""
     *
     * @param url
     * @param param
     * @return
     */ 
    public static String httpPost(String url, Map<String, Object> param) {
        logger.info("httpPost URL [" + url + "] start ");
        DefaultHttpClient httpclient = null;
        HttpPost httpPost = null;
        HttpResponse response = null;
        HttpEntity entity = null;
        String result = "";
        StringBuffer suf = new StringBuffer();
        try {
            httpclient = new DefaultHttpClient();
            // 设置cookie的兼容性---考虑是否需要
            httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                    CookiePolicy.BROWSER_COMPATIBILITY);
            httpPost = new HttpPost(url);
            // 设置各种头信息
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            // 传入各种参数
            if (null != param) {
                for (Map.Entry<String, Object> set : param.entrySet()) {
                    String key = set.getKey();
                    String value = set.getValue() == null ? "" : set.getValue()
                            .toString();
                    nvps.add(new BasicNameValuePair(key, value));
                    suf.append(" [" + key + "-" + value + "] ");
                }
            }
            logger.info("param " + suf.toString());
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            // 设置连接超时时间
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),
                    20000);
            // 设置读数据超时时间
            HttpConnectionParams.setSoTimeout(httpPost.getParams(),
                    20000);
            logger.info("====" + httpPost); 
            response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("HttpStatus ERROR" + "Method failed: "
                        + response.getStatusLine());
                return "";
            } else {
                entity = response.getEntity();
                if (null != entity) {
                    byte[] bytes = EntityUtils.toByteArray(entity);
                    result = new String(bytes, "UTF-8");
                } else {
                    logger.error("httpPost URL [" + url
                            + "],httpEntity is null.");
                }
                return result;
            }
        } catch (Exception e) {
            logger.error("httpPost URL [" + url + "] error, ", e);
            return "";
        } finally {
            if (null != httpclient) {
                httpclient.getConnectionManager().shutdown();
            }
            logger.info("RESULT:  [" + result + "]");
            logger.info("httpPost URL [" + url + "] end ");
        }
    }

    /**
     *
     * @param url
     * @param param
     * @return
     */
    public static String postJson(String url,String param){
        String response = null;
        try {
            HttpClient client = new HttpClient();
            PostMethod method = new PostMethod(url);
            method.addRequestHeader("Content-Type",
                    "application/x-www-form-urlencoded;charset=utf-8");
            // MD5加密
            String MD5Auth = MD5Util.getMd5String(param);
            System.out.println("鉴权密钥：" + MD5Auth);

            // 封裝HEAD信息
            method.setParameter("jsonData", param);
            System.out.println("传的参数========================"+param);
            method.setRequestHeader("authorization", MD5Auth.toString());
            // 请求接口并返回
            client.executeMethod(method);
            response = method.getResponseBodyAsString();

            method.releaseConnection();
            return  response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
    
    public static String httpPostJson(String url,Map<String, Object> param) {
		String result = "";
		try {
			HttpClient client = new HttpClient();
			PostMethod postMethod = new PostMethod(url);
			postMethod.addRequestHeader("Content-Type",
					"application/json; charset=utf-8");
			postMethod.addRequestHeader("Accept", "application/json");
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
    
    public static String httpPostJsonObj(String url,JSONObject jsonObject) {
    	logger.info("httpPost URL [" + url + "] start ");
        DefaultHttpClient httpClient = null;
        HttpResponse response = null;
        HttpEntity entity = null;
        String result = "";
        StringBuffer suf = new StringBuffer();
    	try {
			httpClient = new DefaultHttpClient();
			HttpPost method = new HttpPost(url);
			StringEntity entity1 = new StringEntity(jsonObject.toString(),"utf-8");//解决中文乱码问题
			//entity1.setContentEncoding("UTF-8");    
			//entity1.setContentType("application/json");    
			entity1.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
			method.setEntity(entity1); 
			response = httpClient.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
                logger.error("HttpStatus ERROR" + "Method failed: "
                        + response.getStatusLine());
                return "";
            } else {
                entity = response.getEntity();
                if (null != entity) {
                    byte[] bytes = EntityUtils.toByteArray(entity);
                    result = new String(bytes, "UTF-8");
                } else {
                    logger.error("httpPost URL [" + url
                            + "],httpEntity is null.");
                }
                return result;
            }
			
		} catch (Exception e) {
			logger.error("httpPost URL [" + url + "] error, ", e);
            return "";
		}finally {
            if (null != httpClient) {
            	httpClient.getConnectionManager().shutdown();
            }
            logger.info("RESULT:  [" + result + "]");
            logger.info("httpPost URL [" + url + "] end ");
        }
    }
}

