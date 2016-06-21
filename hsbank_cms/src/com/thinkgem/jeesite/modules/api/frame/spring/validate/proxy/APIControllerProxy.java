package com.thinkgem.jeesite.modules.api.frame.spring.validate.proxy;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.MyBeanUtils;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.frame.format.config.APIConfigUtils;
import com.thinkgem.jeesite.modules.api.frame.format.foramter.WebAPIForamter;
import com.thinkgem.jeesite.modules.api.frame.spring.validate.login.Client;
import com.thinkgem.jeesite.modules.api.frame.spring.validate.login.ParamsUtils;
import com.thinkgem.jeesite.modules.api.frame.spring.validate.login.Token;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.API;
import com.thinkgem.jeesite.modules.api.frame.generator.APIGenerator;
import com.thinkgem.jeesite.modules.api.frame.format.foramter.APIForamter;
import com.thinkgem.jeesite.modules.api.frame.format.annotation.Formater;
import com.thinkgem.jeesite.modules.api.frame.spring.validate.annotation.AutoValidate;
import com.thinkgem.jeesite.modules.api.frame.util.ObjectUtils;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * api的控制器代理，为在控制器方法调用前后进行数据校验 登陆等相关操作
 * Created by 万端瑞 on 2016/5/25.
 */
public class APIControllerProxy implements ControllerProxy {
    private Object apiControllerObj;
    private Enhancer enhancer = new Enhancer();
    private Logger logger = null;

    public Object getProxy(Object apiControllerObj){
        this.apiControllerObj = apiControllerObj;
        this.logger = LoggerFactory.getLogger(apiControllerObj.getClass());
        enhancer.setSuperclass(apiControllerObj.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //去除访问限制
        Reflections.makeAccessible(method);

        String methodName = method.getName();

        //判断调用的方法是否是RequestMapping方法，不是则不执行附加操作
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if(requestMapping == null){
            return method.invoke(apiControllerObj,args);
        }

        //---------------------------执行到这里说明是调用的RequestMapping方法--------------------------------

        //对入参进行处理
        if(args != null){
            for(Object arg : args){
                if(arg != null){

                    //1.数据校验 
                    //如果该对象上存在此注解
                    if(arg.getClass().getAnnotation(AutoValidate.class) != null){
                        //则进行自动数据校验
                        List<String> messages  = ApiUtil.validateBean(arg);
                        if(messages != null && messages.size()>0){
                            return APIGenerator.createResultAPI(false, ApiConstant.API_PARAMS_VALID_RESULT_FAIL,messages.toArray(new String[]{}));
                        }
                    }

                    //2.登陆操作
                    //判断是否需要登录
                    if(arg instanceof Token){
                        //到这里表示需要登录,且已传入token字段

                        //执行登陆操作
                        //解析Token参数
                        Token token = (Token)arg;
                        Token genToken = ParamsUtils.generateToken(token.getToken(),token.getClient());

                        //如果登陆成功
                        if(genToken != null){
                            //为token参数写入必要数据
                            ObjectUtils.copyFields(token,genToken);

                        }else{//登陆失败
                            return APIGenerator.createResultAPI(false,"权限验证失败，token无效！");
                        }
                    }

                    //3.解析Client参数
                    else if(arg instanceof Client){
                        Client client = (Client)arg;
                        //解析CLient参数
                        Client genClient = ParamsUtils.generateClient(client.getClient());
                        //如果解析成功
                        if(genClient != null){
                            ObjectUtils.copyFields(client,genClient);
                        }else{
                            return APIGenerator.createResultAPI(false,"client解析失败，client无效！");
                        }
                    }


                }
            }
        }

        //---------------------------执行到这里说明参数校验通过--------------------------------
        /*Object result = null;
        String methodName = method.getName();

        if("toString".equals(methodName)){
            result = method.invoke(apiControllerObj,args)+"@APIControllerProxy";
        }
        else{
            result = method.invoke(apiControllerObj,args);
        }*/

        //执行方法调用
        Object result = null;
        try{
            //调用前打印日志
            RequestMapping controllerRequestMapping = apiControllerObj.getClass().getAnnotation(RequestMapping.class);
            String methodStr = controllerRequestMapping.value()[0]+"/"+requestMapping.value()[0];
            Date startTime = new Date();
            logger.info("【" + DateUtils.formatDateTime(startTime) + "】"+methodStr+" start...");

            //调用方法
            result = method.invoke(apiControllerObj,args);

            //调用后打印日志
            Date endTime = new Date();
            logger.info("【" + DateUtils.formatDateTime(endTime) + "】"+methodStr+" end...");
            logger.info(methodStr+" total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        }catch (InvocationTargetException e){
            Throwable t = e.getTargetException();
            if(t instanceof ServiceException){
                return APIGenerator.createResultAPI(false,t.getMessage());
            }else{
                throw t;
            }
        }


        //对返回结果进行格式化
        Formater formater = method.getAnnotation(Formater.class);
        if(formater != null && result != null && result instanceof Map){
            //到这里表示需要对返回结果进行格式化
            //1.获取格式化配置ID
            String path = formater.path();
            if(StringUtils.isNotBlank(path)){
                String formatPath = APIConfigUtils.getRealPath(path);
                result = APIForamter.formatAPIByFormatPath((Map<String,Object>)result,formatPath);
            }else{
                result = WebAPIForamter.formatAPI((Map<String,Object>)result);
            }

            result = new API((Map)result);
        }

        return result;
    }




}
