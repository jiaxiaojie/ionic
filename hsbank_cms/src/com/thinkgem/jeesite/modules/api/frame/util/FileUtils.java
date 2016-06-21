package com.thinkgem.jeesite.modules.api.frame.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/6/21.
 */
public class FileUtils {

    /**
     * 返回更改时间晚于指定时间的文件列表
     * @param lastRefreshTime
     * @param configFiles
     * @return
     */
    public static File[] getEditOnAfterLastRefreshTimeFiles(Long lastRefreshTime,File... configFiles) {
        List<File> result = new ArrayList<File>();
        if(configFiles != null && configFiles.length > 0){
            for(File config : configFiles){
                if(config.lastModified() > lastRefreshTime){
                    result.add(config);
                }
            }
        }
        return result.toArray(new File[]{});
    }

}
