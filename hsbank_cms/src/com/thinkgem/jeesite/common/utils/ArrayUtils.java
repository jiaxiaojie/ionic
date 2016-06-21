package com.thinkgem.jeesite.common.utils;

import java.util.Arrays;
import java.util.Collection;

public class  ArrayUtils {

	public static abstract class ForeachAction<T>{
		public abstract void action(T t);
	}

	public static int[] integerArrayToIntArray(Integer[] integerArray){
		int [] intArray = new int[integerArray.length];
		for(int i = 0; i < integerArray.length; i++){
			intArray[i] = integerArray[i];
		}
		
		return intArray;
	}
	
	public static int[] charArrayToIntArray(char[] integerArray){
		int [] intArray = new int[integerArray.length];
		for(int i = 0; i < integerArray.length; i++){
			intArray[i] = Integer.parseInt(integerArray[i]+"");
		}
		
		return intArray;
	}

	public static String toStringWithSeparator(Object[] objs, String separator){
		String result = null;



		if(objs != null ){

			StringBuffer sb = new StringBuffer("");
			for(Object obj : objs){
				if(obj != null){
					sb.append(obj.toString()).append(separator);
				}
			}

			if(sb.length() == 0){
				result = null;
			}else{
				sb.deleteCharAt(sb.length()-1);
				result = sb.toString();
			}

		}
		return result;
	}

	public static <T> void foreach(Collection<T> collection,ForeachAction<T> action){
		if(collection != null && collection.size() > 0 && action != null){
			for(T t : collection){
				action.action(t);
			}
		}
	}

	public static <T> void foreach(T[] collection,ForeachAction<T> action){
		foreach(Arrays.asList(collection),action);
	}

	public static void main(String[] args){
		System.out.println(ArrayUtils.toStringWithSeparator(new String[]{"123","456","789"},","));
	}
}
