package com.thinkgem.jeesite.modules.api.frame.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 万端瑞 on 2016/6/15.
 */
public class ProbabilityUtils {
    //每个样本权重，和为denominator的值
    private Map<String, Integer> sampleProbability = null;
    private Integer denominator = 0;//权重和
    private HashMap<Integer, ArrayList<String>> eventList = new HashMap<Integer, ArrayList<String>>();


    public ProbabilityUtils(Integer[] weights){
        intiSampleProbability(weights);
        System.out.println();
    }

    /**
     * 初始化操作
     * @param weights
     */
    private void intiSampleProbability(Integer[] weights) {
        for(Integer weight : weights){
            denominator+= weight;
        }

        Integer eventCount = weights.length-1;

        //初始化事件列表
        initEventList(eventCount,"",0);

        //每个样本权重map
        int x = 1;
        for(Integer key : eventList.keySet()){
            x *= eventList.get(key).size();
        }

        denominator = denominator*x;

        sampleProbability = new HashMap<>();
        for(int i = 0; i < weights.length; i++){
            //当前事件包含基本事件数
            BigDecimal step1 = (MathUtils.factorial(eventCount-i).multiply(MathUtils.factorial(i)));
            Integer currentElementaryEventCount =(MathUtils.factorial(eventCount).divide(step1)).intValue();

            //每个基本事件的权重
            Integer currentElementaryEventWeight = (weights[i]*x)/currentElementaryEventCount;

            //计算当前事件包含基本事件数的01字符串形式，0代表事件没发生，1代表事件发生
            ArrayList<String> currentEventList = eventList.get(i);
            for(String currentEvent : currentEventList){
                sampleProbability.put(currentEvent,currentElementaryEventWeight);
            }
        }
    }


    /**
     * 得到当前结果的权重
     * @param result
     * @return
     */
    private  Integer currentResultWeight(String result){
        if("".equals(result)){
            return denominator;
        }
        Integer weight = 0;
        for(String key : sampleProbability.keySet()){
            if(key.startsWith(result)){
                weight += sampleProbability.get(key);
            }
        }
        return weight;
    }

    /**
     * 根据之前的结果，计算下一次事件发生的概率
     * @param r
     * @return
     */
    public Fraction calculateNextEventHappenProbability(String r){
        Fraction probability = new Fraction(currentResultWeight(r),currentResultWeight(r+"1"));
        return probability;
    }

    public  Fraction calculateNextEventHappenProbability(Boolean... beforeResults){
        StringBuffer sb = new StringBuffer("");
        if (beforeResults != null && beforeResults.length > 0){
            for (Boolean resultItem : beforeResults){
                sb.append(resultItem?"1":"0");
            }
        }
        return calculateNextEventHappenProbability(sb.toString());
    }



    public void initEventList(Integer index, String currentResult, Integer eventHappenCount){
        if(index >= 0){
            if(index == 0){
                ArrayList<String>  list = eventList.get(eventHappenCount);
                if(list == null){
                    list = new ArrayList<String>();
                    eventList.put(eventHappenCount,list);
                }
                list.add(currentResult);
            }else{
                --index;
                initEventList(index,currentResult+"0" ,eventHappenCount);
                initEventList(index,currentResult+"1" , eventHappenCount+1);
            }
        }
    }

    public static void main(String[] arags){
        Fraction probability = new ProbabilityUtils(new Integer[]{0,55,40,5}).calculateNextEventHappenProbability("00");
        System.out.println(probability);
    }


}
