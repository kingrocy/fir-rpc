package com.yunhui.fir.rpc.common;

import com.yunhui.fir.rpc.exception.FirRpcException;


public interface Codec {

    byte[] encode(Object message) throws FirRpcException;

    Object decode(byte[] data, DataTypeEnum dataTypeEnum) throws FirRpcException;

    enum DataTypeEnum {
        REQUEST,
        RESPONSE,
        ;
    }

}
