package com.gbm.mgb.dto.sms;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class SMS {
	private Long userId;
	private String mobilePhone;
	private String validCode;
	private Date validExpire; 
	private String smsContent;
	private Integer type;
	private Integer smsState;
	private Integer confirmState;
	private Date createDate;
	private Date updateDate;


	
}

