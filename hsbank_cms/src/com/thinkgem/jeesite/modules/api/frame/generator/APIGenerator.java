package com.thinkgem.jeesite.modules.api.frame.generator;



import com.thinkgem.jeesite.modules.api.frame.generator.convert.APIBuilder;
import com.thinkgem.jeesite.modules.api.frame.generator.convert.APIConvertAction;
import com.thinkgem.jeesite.modules.api.frame.generator.convert.APINodeConvertAction;
import com.thinkgem.jeesite.modules.api.frame.generator.convert.HSAPIConvertAction;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.API;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APICollectionNode;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APINode;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APIObjectNode;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by 万端瑞 on 2016/5/19.
 */
public class APIGenerator {

    //默认api转换策略
    private static APIConvertAction defaultCovertAction;

    static{
        //初始化默认api转换策略
        defaultCovertAction = new HSAPIConvertAction(){
            @Override
            public Map<String, Object> convert(Object dataObject) {
                return this.getDefaultConvertAction().convert(dataObject);
            }
        };
    }

    public static void main(String[] args){
        System.out.println(Object.class.isAssignableFrom(String.class));
        System.out.println(String.class.isAssignableFrom(Object.class));
    }

    /**
     * 如果action为null，则返回默认的转换器
     * @param action
     * @param convertActionType action的类型
     * @param <A>
     * @return
     */
    private static <A extends APINodeConvertAction> A getRealCovertAction(A action, Class<? super APIConvertAction> convertActionType){
        APINodeConvertAction realCovertAction = action;
        if (realCovertAction == null){
            realCovertAction = defaultCovertAction;
        }

        if(!convertActionType.isAssignableFrom(realCovertAction.getClass())){
            throw new RuntimeException("参数action和convertActionType的类型不匹配！");
        }

        return (A)realCovertAction;
    }

    /**
     * 转换数据对象为APINode对象
     * @param data 被转换的数据对象
     * @param action 转换策略，如果为null则使用默认转换策略
     * @param <V> 被转换的数据对象的类型
     * @return
     */
    public static <V> APINode toAPINode(V data, APINodeConvertAction<V> action) {
        if (action == null){
            action = defaultCovertAction;
        }
        return new APIObjectNode(action.convert(data));
    }

    /**
     * 转换数据对象为APINode对象，使用默认的转换策略
     * @param data 数据
     * @return
     */
    public static <T> APINode toAPINode(T data) {
        return toAPINode(data, null);
    }

    /**
     * 转换数据对象集合为APINode对象集合
     * @param dataCollection 被转换的数据对象集合
     * @param action 转换策略，如果为null则使用默认转换策略
     * @return
     */
    public static <T> APINode toAPINodeWithCollection(Collection<T> dataCollection, APINodeConvertAction<T> action) {
        action = getRealCovertAction(action,APINodeConvertAction.class);

        APICollectionNode data = new APICollectionNode();
        for(T t : dataCollection){
            data.add(toAPINode(t, action));
        }

        Collections.sort(data,new APIComparator(action));

        return data;
    }

    /**
     *  转换数据对象集合为APINode对象集合，使用默认的转换策略
     * @param dataCollection 被转换的数据对象集合
     * @param <T>  被转换的数据对象的类型
     * @return
     */
    public static <T> APINode toAPINodeWithCollection(Collection<T> dataCollection) {
        return toAPINodeWithCollection(dataCollection, null);
    }


    /**
     *  封装数据对象为API对象
     * @param dataObject 被转换的数据对象
     * @param action API转换策略，如果为null则使用默认转换策略
     * @param <T>
     * @return
     */
    public static <T> API toAPI(T dataObject, APIConvertAction<T> action){
        action = getRealCovertAction(action,APIConvertAction.class);

        APINode apiNode = toAPINode(dataObject,action);

        return action.toAPIByNode(apiNode);
    }

    /**
     *  封装数据对象为API对象
     * @param dataObject 被转换的数据对象,使用默认转换策略
     * @param <T>
     * @return
     */
    public static <T> API toAPI(T dataObject){
        return toAPI(dataObject, null);
    }

    /**
     *  将集合对象转为API对象
     * @param dataCollection 被转换的数据对象集合
     * @param action  API转换策略，如果为null则使用默认转换策略
     * @param <T>
     * @return
     */
    public static <T> API toAPIWithCollection(Collection<T> dataCollection, APIConvertAction<T> action){
        action = getRealCovertAction(action,APIConvertAction.class);

        APINode apiNode = toAPINodeWithCollection(dataCollection,action);
        return action.toAPIByNode(apiNode);
    }

    /**
     * 将集合对象转为API对象,使用默认转换策略
     * @param dataCollection 被转换的数据对象集合
     * @param <T>
     * @return
     */
    public static <T> API toAPIWithCollection(Collection<T> dataCollection){
        return toAPIWithCollection(dataCollection, null);
    }

    /**
     * 创建API创建者
     * @param <T>
     * @return
     */
    public static <T> APIBuilder createAPIBuilder(){
        return new APIBuilder(defaultCovertAction);
    }

    /**
     * 返回成功API，没有数据
     * @return
     */
    public static API createSuccessAPI() {
        return createResultAPI(true);
    }

    /**
     * 参数为true返回成功api，参数为false返回失败api
     * @param yesOrNo
     * @return
     */
    public static API createResultAPI(Boolean yesOrNo) {
        return createResultAPI(yesOrNo, null);
    }

    /**
     * 参数为true返回成功api，参数为false返回失败api
     * @param yesOrNo true代表成功，false代表失败
     * @param message 消息
     * @return
     */
    public static API createResultAPI(Boolean yesOrNo,String... message) {
        return defaultCovertAction.getSuccessBaseAPI(yesOrNo,message);
    }

    /**
     *  参数为true返回成功api，参数为false返回失败api
     * @param yesOrNo
     * @param code
     * @param message
     * @return
     */
    public static API createResultAPI(Boolean yesOrNo,Integer code,String... message) {
        return defaultCovertAction.getSuccessBaseAPI(yesOrNo,code,message);
    }

    public static API createResultAPI(Integer code,String... message){
        return defaultCovertAction.getSuccessBaseAPI(false,code,message);
    }
}
