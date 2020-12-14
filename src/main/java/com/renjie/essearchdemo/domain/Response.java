package com.renjie.essearchdemo.domain;

import lombok.Data;

/**
 * @Auther: fan
 * @Date: 2020/12/9
 * @Description:
 */
@Data
public class Response {
    //返回code
    private Integer code;
    //返回信息
    private String message;
    //返回的主体
    private Object result;
}
