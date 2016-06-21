package com.thinkgem.jeesite.modules.api.frame.generator;

import com.thinkgem.jeesite.modules.api.frame.generator.convert.APINodeConvertAction;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APIObjectNode;


import java.util.Comparator;

/**
 * Created by pc on 2016/6/14.
 */
public class APIComparator implements Comparator<APIObjectNode> {
    private APINodeConvertAction apiNodeConvertAction;
    public APIComparator(APINodeConvertAction apiNodeConvertAction){
        this.apiNodeConvertAction = apiNodeConvertAction;
    }
    private APIComparator(){}



    public int compare(APIObjectNode o1, APIObjectNode o2) {
        return apiNodeConvertAction.compare(o1, o2);
    }
}
