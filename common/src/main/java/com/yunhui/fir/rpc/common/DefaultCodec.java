package com.yunhui.fir.rpc.common;

import com.yunhui.fir.rpc.exception.FirRpcException;

/**
 * @Date : 2021/1/19 4:20 下午
 * @Author : dushaoyun
 */
public class DefaultCodec implements Codec{

    @Override
    public byte[] encode(Object message) throws FirRpcException {
        return new byte[0];
    }

    @Override
    public Object decode(byte[] data, DataTypeEnum dataTypeEnum) throws FirRpcException {
        return null;
    }
}
