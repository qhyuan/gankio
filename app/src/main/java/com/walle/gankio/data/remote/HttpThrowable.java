package com.walle.gankio.data.remote;

public class HttpThrowable extends RuntimeException {
    private String msg;
    private int code;

    private HttpThrowable() {
    }

    public HttpThrowable(int code) {
        this.code = code;
        msg = HttpThrowType.getName(code);
    }

    @Override
    public String getMessage() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}