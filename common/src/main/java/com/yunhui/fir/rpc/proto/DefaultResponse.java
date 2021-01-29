package com.yunhui.fir.rpc.proto;

/**
 * @Date : 2021/1/29 10:06 上午
 * @Author : dushaoyun
 */
public class DefaultResponse implements Response {

    private long requestId;

    private Enum<?> status;

    private Object value;

    private Throwable throwable;


    public DefaultResponse() {
    }

    public DefaultResponse(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public long getRequestId() {
        return requestId;
    }

    @Override
    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public Enum<?> getStatus() {
        return status;
    }

    @Override
    public void setStatus(Enum<?> status) {
        this.status = status;
    }


    @Override
    public boolean isError() {
        return throwable != null;
    }

    @Override
    public void build() {

    }
}
