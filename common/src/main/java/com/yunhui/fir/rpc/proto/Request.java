package com.yunhui.fir.rpc.proto;

/**
 * @Date : 2021/1/22 11:34 上午
 * @Author : dushaoyun
 */
public interface Request {

    long getRequestId();

    void setRequestId(long requestId);

    String getInterfaceName();

    void setInterfaceName(String interfaceName);

    String getMethodName();

    void setMethodName(String methodName);

    Object[] getArgsValue();

    void setArgsValue(Object[] argsValue);

    void build();
}
