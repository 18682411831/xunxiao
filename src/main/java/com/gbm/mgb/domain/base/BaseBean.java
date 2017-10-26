package com.gbm.mgb.domain.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Waylon on 2017/10/20.
 */
@Data
public class BaseBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

     /**
     * 创建日期
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 更新日期
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 创建人id
     */
    @Column(name = "create_user_id")
    private String createUserId;

    /**
     * 更新人id
     */
    @Column(name = "update_user_id")
    private String updateUserId;
}
