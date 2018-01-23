package com.gbm.mgb.domain.sms;

import com.gbm.mgb.dto.sms.SMS;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;
@Repository
public interface SMSDao {

	/**
	 * 保存短信验证信息
	 * @param sms
	 * @return
	 */
	 int save(SMS sms);

	/**
	 * 查询验证码根据手机号
	 */
	 SMS findSMSByPhoneAndCode(@Param("mobilePhone") String mobilePhone,@Param("validCode")Integer validCode);
	/**
	 * 更改确认状态
	 */
	 int updateState(Map<String, Object> map);


	/**
	 * 查询过期时间
	 * @param sms
	 * @return
	 */
	 Date getValidExpire(SMS sms);


	 int updateCodeState(Map<String, Object> map);

	/**
	 * 查询是否发过短信
	 * @param sms
	 * @return
	 */
	 Integer findIsSend(SMS sms);

	/**
	 * 查询appkey
	 * @param merchantNo
	 * @return
     */
	 Map<String,Object> findAppKey(String merchantNo);

}
