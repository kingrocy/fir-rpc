package com.yunhui.fir.rpc.invoker;

import com.yunhui.fir.rpc.proto.Request;
import com.yunhui.fir.rpc.proto.Response;

/**
 * @Date : 2021/1/29 9:39 上午
 * @Author : dushaoyun
 */
public interface Invoker<T> {

    Class<T> getInterface();

    Response invoke(Request request);

}
