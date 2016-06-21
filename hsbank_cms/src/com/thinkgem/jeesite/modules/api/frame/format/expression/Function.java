package com.thinkgem.jeesite.modules.api.frame.format.expression;

import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.FunctionElement;
import com.thinkgem.jeesite.modules.api.frame.format.convert.ConvertorServiceFactory;

import java.lang.reflect.Method;

/**
 * Created by 万端瑞 on 2016/5/23.
 */
public class Function {
    private Class[] paramTypes;
    private Class returnType;

    private Method method;

    private Class clazz;

    public Object invoke(Object[] params){
        Object result = null;
        //1.如果参数类型不匹配则试图转换格式
        for(int i = 0; i < paramTypes.length;i++){
            params[i] = ConvertorServiceFactory.getConvertService().covert(params[i],paramTypes[i]);
        }

        try {
            result = method.invoke(clazz,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Function(FunctionElement functionElement){
        try {
            Class clazz = Class.forName(functionElement.getClazz());
            String methodName = functionElement.getMethod();
            Class returnType =  Class.forName(functionElement.getReturnType());

            Class[] paramTypes = new Class[functionElement.getParamTypes().size()];
            for(int i = 0; i < functionElement.getParamTypes().size(); i++){
                String paramTypeStr = functionElement.getParamTypes().get(i);
                Class paramsTypeClazz = Class.forName(paramTypeStr);
                paramTypes[i] = paramsTypeClazz;
            }


            this.paramTypes = paramTypes;
            this.returnType = returnType;
            this.method = clazz.getMethod(methodName, this.paramTypes);
            this.clazz = clazz;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
