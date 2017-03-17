package com.site.model.sign;

/**
 * Created by wang0 on 2016/9/13.
 */
public class ResponseMessage {

    private int code;

    public ResponseMessage(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
