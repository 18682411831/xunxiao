package com.gbm.mgb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by 劵码核销类 on 2018/1/3.
 */
@Data
@NoArgsConstructor
public class Coupon {
    private Integer id;//主键
    private Integer password;
    private Integer playStatus;
    private Integer status;
    private String ticket;
    private String iPhone;
    private String payNo;
    private Date createData;
    private Date updateData;
}
