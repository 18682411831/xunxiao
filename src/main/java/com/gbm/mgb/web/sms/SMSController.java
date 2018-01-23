package com.gbm.mgb.web.sms;

import com.gbm.mgb.core.Result;
import com.gbm.mgb.core.ResultCode;
import com.gbm.mgb.dto.sms.SMS;
import com.gbm.mgb.service.rbac.common.RemoteService;
import com.gbm.mgb.service.rbac.order.OrderService;
import com.gbm.mgb.service.rbac.sms.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 短信验证controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/sms")
public class SMSController {
	@Autowired
	private SMSService sMSService;
	@Autowired
	private OrderService orderService;
	/**
	 * 短信验证码
	 */
	@RequestMapping(value="/send",method = RequestMethod.POST)
	@ResponseBody
	public Result sendMessage(String mobilePhone, Integer type) {
		if (StringUtils.isEmpty(mobilePhone)) {
			return Result.build(ResultCode.FAIL,"手机号不能为空!!");
		}
		if (!orderService.isChinaPhoneLegal(mobilePhone)){
			return Result.build(400,"联系电话不正确!");
		}

		SMS sms = new SMS();
		sms.setMobilePhone(mobilePhone);
		sms.setType(type);
		Integer count = sMSService.findIsSend(sms);
		if (count == 0) {
			String vcode = RemoteService.sendMessage(mobilePhone,String.valueOf(type));
			sms.setValidCode(vcode);
			sms.setSmsContent("【迅销科技】尊敬客户您的登陆短信验证码为："+vcode+"，5分钟内有效。");
		}else {
			Date validExpire = sMSService.getValidExpire(sms);
			if (validExpire == null) {
				return Result.build(ResultCode.FAIL,"验证码过期时间为空");
			}else {
				//if (validExpire.after(new Date())) {
				//	return Result.build(201,"验证码已经发送,请不要重复点击!");
				//}else {
					String vcode = RemoteService.sendMessage(mobilePhone,String.valueOf(type));
					sms.setValidCode(vcode);
					sms.setSmsContent("【迅销科技】尊敬客户您的登陆短信验证码为："+vcode+"，5分钟内有效。");
				//}
			}
			
		}
		return sMSService.save(sms);
	}

}
