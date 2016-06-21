package com.thinkgem.jeesite.modules.api.frame.format.convert;

/**
 * Created by pc on 2016/5/31.
 */
public class ConvertorServiceFactory {
    private static ConvertService convertService;
    public static ConvertService getConvertService(){
        if(convertService == null){
            try{
                convertService = new SpringConvertService();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return convertService;
    }
}
