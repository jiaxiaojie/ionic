package com.thinkgem.jeesite.common.ticket.intelligent_ticket.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
public class RecTicketTest {
    public static void main(String[] args) {
        List<String> dataList = new ArrayList<String>();
        dataList.add("a");
        dataList.add("b");
        dataList.add("c");
        dataList.add("d");
        dataList.add("e");
        dataList.add("f");
        int size = dataList.size();
        for(int i=0;i<dataList.size();i++){
            method(null,0,dataList.subList(i, size));
        }
    }
    //使用递归方式
    public static void method(String head,int idx,List<String> dataList){
        if(idx == 0){
            head = dataList.get(idx);
            System.out.println(head);
            method(head,++idx,dataList);
        }else{
            String ahead =null;
            for(;idx<dataList.size();idx++){
                ahead = head+dataList.get(idx);
                System.out.println(ahead+"  idx="+idx+" head="+head);
                method(ahead,idx+1,dataList);
            }
        }
    }

}
