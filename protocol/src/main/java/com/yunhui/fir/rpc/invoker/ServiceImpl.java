package com.yunhui.fir.rpc.invoker;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.yunhui.fir.rpc.exception.FirRpcException;
import com.yunhui.fir.rpc.proto.DefaultResponse;
import com.yunhui.fir.rpc.proto.FirRpcProtocol;
import com.yunhui.fir.rpc.proto.Request;
import com.yunhui.fir.rpc.proto.Response;
import com.yunhui.fir.rpc.utils.ReflectUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Date : 2021/1/29 9:48 上午
 * @Author : dushaoyun
 */
public class ServiceImpl<T> implements Invoker<T> {

    private T ref;

    private Map<String, Method> methodMap = new ConcurrentHashMap<>();

    private Class<T> clazz;

    public ServiceImpl(T ref, Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new FirRpcException(clazz + " is not interface");
        }
        this.clazz = clazz;
        this.ref = ref;
        //解析接口 获取method
        List<Method> methods = ReflectUtils.parseMethod(clazz);
        for (Method method : methods) {
//            String methodDesc = ReflectUtils.getMethodDesc(method);
//            methodMap.put(methodDesc, method);
            methodMap.put(method.getName(), method);
            //todo 这种写法 不支持方法重载
        }
    }

    @Override
    public Class getInterface() {
        return clazz;
    }

    @Override
    public Response invoke(Request request) {
        Response response = new DefaultResponse();
        String methodName = request.getMethodName();
        Method method = methodMap.get(methodName);
        if (method == null) {
            response.setStatus(FirRpcProtocol.RpcStatus.OUTER_ERROR);
            response.setThrowable(new FirRpcException("request:" + request + " method not exist"));
            return response;
        }
        try {
            Object[] argsValue = resolveArgsValue(request.getArgsValue(), method);
            Object value = method.invoke(ref, argsValue);
            response.setValue(value);
            response.setStatus(FirRpcProtocol.RpcStatus.OK);
        } catch (Exception e) {
            response.setStatus(FirRpcProtocol.RpcStatus.INNER_ERROR);
            response.setThrowable(
                    new FirRpcException("ServerImpl: exception when invoke method: " + methodName, e));
        } catch (Error e) {
            response.setStatus(FirRpcProtocol.RpcStatus.INNER_ERROR);
            response.setThrowable(
                    new FirRpcException("ServerImpl: error when invoke method: " + methodName, e));
        }
        return response;
    }

    private Object[] resolveArgsValue(Object[] argsValue, Method method) throws InvalidProtocolBufferException {
        if (argsValue == null) {
            return null;
        }
        Object[] resolvedArgs = new Object[argsValue.length];
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < argsValue.length; i++) {
            if (argsValue[i] instanceof Any && Message.class.isAssignableFrom(parameterTypes[i])) {
                resolvedArgs[i] = ((Any) argsValue[i]).unpack((Class<Message>) parameterTypes[i]);
            } else {
                resolvedArgs[i] = argsValue[i];
            }
        }
        return resolvedArgs;
    }
}
