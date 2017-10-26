package com.gbm.mgb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 基础账户信息
 * Created by Waylon on 2017/9/27.
 */
@Data
@AllArgsConstructor
public class AccountCredentials {

    private String username;
    private String password;



}
