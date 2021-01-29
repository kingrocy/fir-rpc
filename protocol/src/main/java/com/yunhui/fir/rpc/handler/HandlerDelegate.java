package com.yunhui.fir.rpc.handler;


import com.yunhui.fir.rpc.invoker.Invoker;
import com.yunhui.fir.rpc.proto.Request;

public class HandlerDelegate implements Handler {

    private Invoker serverImpl;

    public HandlerDelegate(Invoker serverImpl) {
        this.serverImpl = serverImpl;
    }

    @Override
    public String getServiceName() {
        return serverImpl.getInterface().getName();
    }

    @Override
    public Object handle(Object message) {
        return serverImpl.invoke((Request) message);
    }
}
