package com.yunhui.fir.rpc.common;

import com.yunhui.fir.rpc.exception.FirRpcException;
import com.yunhui.fir.rpc.proto.DefaultResponse;
import com.yunhui.fir.rpc.proto.FirRpcProtocol;
import com.yunhui.fir.rpc.proto.ProtobufRequestDelegate;
import com.yunhui.fir.rpc.proto.ProtobufResponseDelegate;

/**
 * @Date : 2021/1/19 4:20 下午
 * @Author : dushaoyun
 */
public class DefaultCodec implements Codec {

    /**
     * 将Request、Response 编码成 ProtobufRequest、ProtobufResponse
     *
     * @param message
     * @return
     * @throws FirRpcException
     */
    @Override
    public byte[] encode(Object message) throws FirRpcException {
        if (message instanceof DefaultResponse) {
            ProtobufResponseDelegate response = new ProtobufResponseDelegate();
            DefaultResponse defaultResponse = (DefaultResponse) message;
            response.setRequestId(defaultResponse.getRequestId());
            response.setStatus(defaultResponse.getStatus());
            if (defaultResponse.getValue() != null) {
                response.setValue(defaultResponse.getValue());
            }
            if (defaultResponse.getThrowable() != null) {
                response.setThrowable(defaultResponse.getThrowable());
            }
            response.build();
            return encode(response);
        }
        throw new IllegalArgumentException();
    }

    /**
     * 将ProtobufRequest 、ProtobufResponse 解码成 Request、Response
     *
     * @param data
     * @param dataTypeEnum
     * @return
     * @throws FirRpcException
     */
    @Override
    public Object decode(byte[] data, DataTypeEnum dataTypeEnum) throws FirRpcException {
        try {
            if (DataTypeEnum.REQUEST == dataTypeEnum) {
                FirRpcProtocol.Request request = FirRpcProtocol.Request.parseFrom(data);
                return new ProtobufRequestDelegate(request);
            }
            if (DataTypeEnum.RESPONSE == dataTypeEnum) {
                FirRpcProtocol.Response response = FirRpcProtocol.Response.parseFrom(data);
                return new ProtobufResponseDelegate(response);
            }
            throw new FirRpcException("Illegal DataTypeEnum: " + dataTypeEnum);
        } catch (Exception e) {
            throw new FirRpcException("Decode error", e);
        }
    }
}
