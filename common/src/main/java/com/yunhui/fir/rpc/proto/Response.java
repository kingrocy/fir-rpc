package com.yunhui.fir.rpc.proto;

/**
 * @Date : 2021/1/22 11:36 上午
 * @Author : dushaoyun
 */
public interface Response {

    long getRequestId();

    void setRequestId(long requestId);

    Object getValue();

    void setValue(Object value);

    Throwable getThrowable();

    void setThrowable(Throwable throwable);

    Enum<?> getStatus();

    void setStatus(Enum<?> status);

    boolean isError();

    void build();

}
