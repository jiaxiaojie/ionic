package com.thinkgem.jeesite.modules.api.frame.exception;

import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * Created by 万端瑞 on 2016/6/20.
 */
public class APINodeException extends RuntimeException {
    private StringBuffer nodePath;
    private String msg;

    public StringBuffer getNodePath() {
        return nodePath;
    }

    public void setNodePath(StringBuffer nodePath) {
        this.nodePath = nodePath;
    }

    public APINodeException(String msg, StringBuffer beforeNodePath, String currentNode) {
        super(msg);
        this.msg = msg;
        this.nodePath = beforeNodePath;

        if(nodePath != null && StringUtils.isNotBlank(nodePath.toString())){
            nodePath.insert(0,".").insert(0,currentNode);
        }else {
            if(nodePath == null){
                nodePath = new StringBuffer();
            }

            nodePath.append(currentNode);
        }
    }
}
