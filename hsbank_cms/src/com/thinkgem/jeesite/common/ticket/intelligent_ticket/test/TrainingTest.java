package com.thinkgem.jeesite.common.ticket.intelligent_ticket.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thinkgem.jeesite.common.mapper.JsonMapper;

public class TrainingTest {

	public TrainingTest() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args){
		String[] arr = new String[]{"1","2","3","4","5","6"};
		List<String> arrList = Arrays.asList(arr);
		System.out.println("arr:::"+arrList);
		Integer[] is = new Integer[]{1,2,3,4,5,6};
		List<Integer> isList = Arrays.asList(is);
		System.out.println("is:::"+isList);
		String arrJson = JsonMapper.toJsonString(arrList);
		String isJson  = JsonMapper.toJsonString(isList);
		
		System.out.println("arrJson:::"+arrJson);
		System.out.println("isJson:::"+isJson);
		
		StringBuffer buf = new StringBuffer();
		List<String> listB = new ArrayList<String>();
		buf.append(1).append(",").append(2).append(",").append(3);
		listB.add(buf.toString());
		
		System.out.println("listB:::"+listB);
		
		String listBJson = JsonMapper.toJsonString(listB);
		System.out.println("listBJson:::"+listBJson);
		
		
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		int listSize = list.size();
		for(int i=0;i<listSize;i++){//最外层循环
			// 以list中的每个字母都做一遍首字母
			String x = list.get(i);
			System.out.println(x);
			for(int j=i+1;j<listSize;j++){//中循环  分别以首字符之外的字母做组合，比如 ab{abc abcd}   ac{acd}  ad 
				String head = x+list.get(j);
				System.out.println(head);				
				int a = j+1;
				int tmp = 0;
				while(a <listSize){
					for(tmp=a;tmp<listSize;tmp++){
						String  head2 =list2String(list, a, tmp);
						System.out.println(head+head2);
					}
					a++;
				}
				
			}
			
		}
	}
	/**
	 * 获取下标s到e的字符串
	 * @param list
	 * @param s
	 * @param e
	 * @return
	 */
	private static String list2String(List<String> list,int s,int e){
		 String str = "";
		for(int i=s;i<=e;i++){
			str+=list.get(i);
		}
		return str;
	}

}
