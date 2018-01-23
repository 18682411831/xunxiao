package com.gbm.mgb.service.rbac.sms.impl;

import com.gbm.mgb.core.Result;
import com.gbm.mgb.core.ResultCode;
import com.gbm.mgb.domain.sms.SMSDao;
import com.gbm.mgb.dto.sms.SMS;
import com.gbm.mgb.service.rbac.common.RemoteService;
import com.gbm.mgb.service.rbac.sms.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 短信验证service实现类
 *
 * @author Administrator
 */
@Service
@Transactional
public class SMSServiceImpl implements SMSService {


    @Autowired
    private SMSDao sMSDao;

    /**
     * 保存验证码
     * @param sms
     * @return
     */
    @Override
    public Result save(SMS sms) {

        int result = sMSDao.save(sms);
        if (result == -1) {
            return Result.build(Integer.parseInt(ResultCode.FAIL_OUT.toString()),"保存异常");
        } else {
            return Result.ok("操作成功");
        }
    }
    @Override
    public Result findAppkey(String merchantNo) {

        Map<String, Object> map = sMSDao.findAppKey(merchantNo);
        String phoneNumber = (String) map.get("mobile_phone");
        String appKey = (String) map.get("app_key");
        System.out.println(appKey);
        String result = RemoteService.sendWarningMessage(phoneNumber, "8", appKey);
        if (result.contains("000000")) {
            return Result.ok("操作成功!");
        } else {
            return Result.build(ResultCode.FAIL,"短信发送失败!");
        }
    }

	/*public APIMessage sendMessageForApp(String phoneNumber,Integer type) {

		int result = sMSDao.save(sms);
		if (result == -1) {
			return APIMessage.FAIL(StatusCode.SAVE_ERROR.getCode(), StatusCode.SAVE_ERROR.getDesc());
		}else {
			return APIMessage.OK(StatusCode.SUCCESS.getDesc());
		}
	}
*/
    @Override
    public Integer findIsSend(SMS sms) {
        return sMSDao.findIsSend(sms);
    }
    @Override
    public Date getValidExpire(SMS sms) {
        return sMSDao.getValidExpire(sms);
    }


}
