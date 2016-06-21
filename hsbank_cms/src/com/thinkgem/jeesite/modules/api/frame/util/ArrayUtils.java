package com.thinkgem.jeesite.modules.api.frame.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by pc on 2016/6/6.
 */
public class ArrayUtils {
    /**
     *
     * @param array
     * @param clazz
     * @param asArrayType 是组数或集合类型，为null则不确定
     * @param <V>
     * @return
     */
    public static <V> List<V> toList(V array,Class<V> clazz, Boolean asArrayType){
        List<V> objParams = new LinkedList<>();
        if(asArrayType == null){
            if(array instanceof Collection){
                objParams.addAll((Collection)array);
            }else if(array instanceof Object[]){
                objParams.addAll(Arrays.asList((V[])array));
            }else{
                objParams.add((V)array);
            }
        }else if(asArrayType){
            if(array instanceof Collection){
                objParams.addAll((Collection)array);
            }else if(array instanceof Object[]){
                objParams.addAll(Arrays.asList((V[])array));
            }else{
                throw new RuntimeException("参数是数组或集合！");
            }
        }else {
            objParams.add((V)array);
        }


        return objParams;
    }

    public static <V> List<V> toList(V array,Class<V> clazz){
        return toList(array, clazz,null);
    }



}
