package com.thinkgem.jeesite.modules.api.frame.spring.validate.login;

import com.thinkgem.jeesite.common.utils.MyBeanUtils;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.client.ClientProperty;
import com.thinkgem.jeesite.modules.api.frame.util.ObjectUtils;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;

import static com.thinkgem.jeesite.modules.api.ApiUtil.getClient;

/**
 * Created by 万端瑞 on 2016/6/2.
 */
public class ParamsUtils {

    /**
     * 得到封装了数据后的token对象，如果失败则返回null
     * @param tokenString
     * @param clientString
     * @return
     */
    public static Token generateToken(String tokenString,String clientString){
        Token token = null;

        if(StringUtils.isBlank(tokenString) || StringUtils.isBlank(clientString)){
            return token;
        }



        try {
            //查询数据
            Client client = generateClient(clientString);
            CustomerClientToken clientToken = CustomerClientUtils.getByToken(tokenString,client.getOperTerm().getOperTermCode());

            if(client != null && clientToken != null){
                token = new Token();

                //设置Client参数
                ObjectUtils.copyFields(token,client);

                //设置token参数
                token.setToken(tokenString);
                Reflections.setFieldValue(token,"accountId",clientToken.getCustomerId());
            }
        }catch (Exception e){
            token = null;
        }


        return token;

    }

    /**
     * 得到封装了数据的Client对象，如果失败则返回null
     * @param clientString
     * @return
     */
    public static Client generateClient(String clientString){
        Client client = null;

        if(StringUtils.isBlank(clientString)){
            return client;
        }

        try{
            ClientProperty cProperty = getClient(clientString);
            OperTerm opTerm = OperTerm.getByTypeString(cProperty.getType());

            if(opTerm != null){
                client = new Client();
                client.setClient(clientString);
                Reflections.setFieldValue(client,"operTerm",opTerm);
            }
        }catch (Exception e){
            client = null;
        }


        return client;
    }

    public static void main(String[] args){

    }

}
