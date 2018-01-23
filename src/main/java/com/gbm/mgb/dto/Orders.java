package com.gbm.mgb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 订单实体 on 2018/1/3.
 */
@Data
@NoArgsConstructor
public class Orders {
    private Integer oid;
    private BigDecimal money;
    private Integer status;
    private Integer playType;
    private String phone;
    private String orderId;
    private String tradeNo;
    private String goods;
    private String payNo;
    private String name;
    private String ophone;
    private String express;
    private String receiveAddress;
    private String address;
    private String tMember;
    private Date cretaData;
}
