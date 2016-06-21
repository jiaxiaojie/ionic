package com.thinkgem.jeesite.modules.api.frame.util;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by pc on 2016/6/12.
 */
public class XmlUtils {
    public static <T> T fileToObject(InputStream is, Class<?> clazz) {
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line).append("\r\n");
            }
            if (is != null) {
                is.close();
            }
            if (br != null) {
                br.close();
            }
            return (T) JaxbMapper.fromXml(sb.toString(), clazz);
        } catch (IOException e) {

        }
        return null;
    }
}
