package com.thinkgem.jeesite.modules.api.frame.format.config;

import com.thinkgem.jeesite.common.utils.JsonUtils;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.APIItemElement;

/**
 * Created by 万端瑞 on 2016/6/21.
 */
public class APIFormat {
    private String path;
    private Object foramt;

    public APIFormat(APIItemElement apiItemElement,String path) {
        this.foramt = JsonUtils.getObjectByJson(apiItemElement.getData().getFormat());
        this.path = path;
    }

    public Object getForamt() {
        return foramt;
    }

    public void setForamt(Object foramt) {
        this.foramt = foramt;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
