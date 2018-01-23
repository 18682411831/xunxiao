package com.gbm.mgb.service.rbac.sms;

import com.gbm.mgb.core.Result;
import com.gbm.mgb.dto.sms.SMS;

import java.util.Date;

public interface SMSService {

	/**
	 * 发送短信验证
	 * @param phoneNumber
	 * @return
	 */
	//public APIMessage sendMessageForApp(String phoneNumber,Integer type);
	/**
	 * 发送短信验证
	 * @param phoneNumber
	 * @return
	 */
	//public APIMessage sendMessage(String phoneNumber,Integer type);
	/**
	 * 查询是否发送过短信
	 * @param sms
	 * @return
	 */
	public Integer findIsSend(SMS sms);
	/**
	 * 获得短信过期时间
	 * @param sms
	 * @return
	 */
	public Date getValidExpire(SMS sms);
	/**
	 * 保存短信记录
	 * @param sms
	 * @return
	 */
	public Result save(SMS sms);

	/**
	 * 查询appkey
	 * @param merchantNo
	 * @return
     */
	public Result findAppkey(String merchantNo);

}
