package com.thinkgem.jeesite.modules.api.frame.util.expression.path;

import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.frame.util.ArrayUtils;
import com.thinkgem.jeesite.modules.api.frame.util.ValueObjectUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 万端瑞 on 2016/6/7.
 */
public class PathParser {
    private Boolean isEach = false;
    public static final String THIS = "this";
    /**
     * 解析path节点
     * @param columns
     * @return
     */
    public List<PathNode> parseColumn(String ...columns){

        List<PathNode> result = null;
        try {

            if (columns != null){
                result = new LinkedList<>();
                for (String column : columns){
                    PathNode resultItem = null;
                    StringBuffer realPath = new StringBuffer(column);
                    Pattern pattern = Pattern.compile("\\s*(\\w*)\\[([0-9]*)\\]\\s*");
                    Matcher matcher = pattern.matcher(column);

                    while (matcher.find()){
                        String columnName = matcher.group(1);
                        String indexStr = matcher.group(2);

                        Integer index = -1;
                        if(StringUtils.isNotBlank(indexStr)){
                            index = Integer.parseInt(indexStr);
                        }
                        resultItem = new PathNode(columnName,index);
                    }

                    if(resultItem == null){
                        resultItem = new PathNode(column,null);
                    }
                    result.add(resultItem);
                }
            }


        }catch (Exception e){
            throw new RuntimeException("path节点解析失败！");
        }


        return result;
    }

    public static Object getValueByColumnName(Object dataItem, PathNode pathNode){
        Object result = null;
        try{
            if(pathNode != null){
                if(THIS.equals(pathNode.getColumnName())){
                    result = dataItem;
                }else if(StringUtils.isBlank(pathNode.getColumnName())){
                    if(pathNode.getIndex() >= 0){
                        //到这里表示dataItem应该是一个集合
                        result = ArrayUtils.toList(dataItem,Object.class,true).get(pathNode.getIndex());
                    }
                }else{
                    //到这里表示dataItem是一个对象
                    if(dataItem instanceof Map){
                        result = ((Map<String,Object>)dataItem).get(pathNode.getColumnName());
                    }else {
                        result = Reflections.invokeGetter(dataItem,pathNode.getColumnName());
                    }
                    if(pathNode.getIndex() != null){
                        if(pathNode.getIndex() >= 0){
                            result = ArrayUtils.toList(result,Object.class,true).get(pathNode.getIndex());
                        }
                    }
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException("读取数据失败，通过PathNode:"+pathNode);
        }

        return result;
    }

    /**
     * 解析path
     * @param path
     * @return
     */
    public List<PathNode> parsePath(String path){
        String[] nodeStrs = (path.indexOf(".") != -1? StringUtils.split(path,"."):new String[]{path});
        return parseColumn(nodeStrs);
    }

    /**
     * 通过path获取值，值可能是list或Object
     * @param data
     * @param path
     * @return
     */
    public Object readValue(Object data,String path){
        List<PathNode> pathNodes = parsePath(path);
        return readValue(data, pathNodes,false);//TODO
    }

    public Object readValueByPathNode(Object data,PathNode pathNode){

        return getValueByColumnName(data,pathNode);
    }

    public Object readValue(Object data, List<PathNode> pathNodes,Boolean isEachNodeOnCurrent){
        Object result = null;

        if(isEachNodeOnCurrent){
            isEach = true;
        }

        if(pathNodes.size() == 0){
            return data;
        }

        PathNode currentPathNode = pathNodes.remove(0);//TODO 不确定返回的是否是删除的节点

        List<Object> dataItems = null;
        try {
            dataItems = ArrayUtils.toList(data, Object.class,isEachNodeOnCurrent?true:false);
        }catch (Exception e){
            if(isEachNodeOnCurrent){
                throw new RuntimeException("需要遍历节点："+currentPathNode.getColumnName()+"，但此节点数据并非集合类型");
            }else{
                throw e;
            }
        }

        List<Object> dataItemsValue = new LinkedList<>();
        for (Object dataItem : dataItems){
            Object dataItemValue = readValueByPathNode(dataItem,currentPathNode);
            //dataItemsValue.add(dataItemValue);
            //继续解析后面的节点
            Boolean nextNodeIsEachNodeOnCurrent = (currentPathNode.getIndex()!= null && currentPathNode.getIndex() == -1);
            Object rootValue = readValue(dataItemValue, new LinkedList<>(pathNodes), nextNodeIsEachNodeOnCurrent);

            if(nextNodeIsEachNodeOnCurrent){
                dataItemsValue.addAll((List<Object>)rootValue);
            }else {
                dataItemsValue.add(rootValue);
            }

        }
        return isEach?dataItemsValue:dataItemsValue.get(0);
    }

    public static void main(String[] args){
        Map<String,Object> dataItem1 = new LinkedHashMap<>();
        dataItem1.put("code","c1");
        dataItem1.put("text","t1");

        Map<String,Object> dataItem2 = new LinkedHashMap<>();
        dataItem2.put("code","c2");
        dataItem2.put("text","t2");

        Map<String,Object> dataItem3 = new LinkedHashMap<>();
        dataItem3.put("code","c3");
        dataItem3.put("text","t3");

        Map<String,Object> dataItem4 = new LinkedHashMap<>();
        dataItem4.put("code","c4");
        dataItem4.put("text","t4");

        List<Object> list1 = new LinkedList<>();
        list1.add(dataItem1);
        list1.add(dataItem2);

        List<Object> list2 = new LinkedList<>();
        list2.add(dataItem3);
        list2.add(dataItem4);

        Map<String,Object> dataNode1 = new LinkedHashMap<>();
        dataNode1.put("list",list1);

        Map<String,Object> dataNode2 = new LinkedHashMap<>();
        dataNode2.put("list",list2);

        List<Object> dataNodeList1 = new LinkedList<>();
        dataNodeList1.add(dataNode1);
        dataNodeList1.add(dataNode2);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("result",dataNodeList1);



        Object r = new PathParser().readValue(result,"result[].list[].code");
        //Object r = new PathParser().readValue(dataNodeList1,"[1]");
        System.out.println(r);
        //ValueObjectUtils.getValueByColumnName()
    }
}
