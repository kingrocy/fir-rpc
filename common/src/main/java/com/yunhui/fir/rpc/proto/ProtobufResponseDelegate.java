package com.yunhui.fir.rpc.proto;

import com.google.protobuf.Any;
import com.google.protobuf.Any.Builder;
import com.google.protobuf.Message;
import com.yunhui.fir.rpc.exception.FirRpcException;

public class ProtobufResponseDelegate implements Response {

    private FirRpcProtocol.Response.Builder builder;
    private FirRpcProtocol.Response delegatedResponse;
    private Object value;

    public ProtobufResponseDelegate() {
        builder = FirRpcProtocol.Response.newBuilder();
    }

    public ProtobufResponseDelegate(FirRpcProtocol.Response delegatedResponse) {
        this.delegatedResponse = delegatedResponse;
    }

    @Override
    public long getRequestId() {
        if (delegatedResponse == null) {
            return 0;
        }
        return delegatedResponse.getRequestId();
    }

    @Override
    public Enum getStatus() {
        if (delegatedResponse == null) {
            return null;
        }
        return delegatedResponse.getStatus();
    }

    @Override
    public Object getValue() {
        if (delegatedResponse == null) {
            return value;
        }
        return delegatedResponse.getResult();
    }

    @Override
    public Throwable getThrowable() {
        return new FirRpcException(delegatedResponse.getErrorMessage());
    }

    @Override
    public void setRequestId(long requestId) {
        builder.setRequestId(requestId);
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Any) {
            builder.setResult((Any) value);
        } else if (value instanceof Builder) {
            builder.setResult((Builder) value);
        } else if (value instanceof Message) {
            builder.setResult(Any.pack((Message) value));
        } else {
            this.value = value;
        }
    }

    @Override
    public void setThrowable(Throwable throwable) {
        builder.setErrorMessage(throwable.getMessage());
    }

    @Override
    public void setStatus(Enum status) {
        if (status instanceof FirRpcProtocol.RpcStatus) {
            builder.setStatus((FirRpcProtocol.RpcStatus) status);
        }
    }

    @Override
    public void build() {
        if (delegatedResponse == null) {
            delegatedResponse = builder.build();
        }
    }

    @Override
    public boolean isError() {
        if (delegatedResponse == null) {
            return false;
        }
        return delegatedResponse.getStatus() != FirRpcProtocol.RpcStatus.OK;
    }

    public FirRpcProtocol.Response getDelegatedResponse() {
        return delegatedResponse;
    }
}
