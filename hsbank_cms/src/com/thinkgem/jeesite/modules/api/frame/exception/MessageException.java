package com.thinkgem.jeesite.modules.api.frame.exception;

/**
 * Created by 万端瑞 on 2016/6/16.
 */
public class MessageException extends CustomException {
    private Integer code;
    public MessageException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
