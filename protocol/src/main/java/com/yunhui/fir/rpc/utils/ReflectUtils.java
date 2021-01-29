package com.yunhui.fir.rpc.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2021/1/29 9:52 上午
 * @Author : dushaoyun
 */
public class ReflectUtils {

    public static List<Method> parseMethod(Class clazz) {
        Method[] methods = clazz.getMethods();
        List<Method> ret = new ArrayList<>();
        for (Method method : methods) {
            boolean isPublic = Modifier.isPublic(method.getModifiers());
            boolean isNotObjectClass = method.getDeclaringClass() != Object.class;
            if (isPublic && isNotObjectClass) {
                ret.add(method);
            }
        }
        return ret;
    }

    public static String getMethodDesc(Method method) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getName()).append("(");
        if (method.getParameterCount() > 0) {
            for (Class c : method.getParameterTypes()) {
                sb.append(c.getName()).append(",");
            }
            sb.setLength(sb.length() - ",".length());
        }
        sb.append(")");
        return sb.toString();
    }

}
