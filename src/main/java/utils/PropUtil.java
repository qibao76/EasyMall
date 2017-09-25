package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/5/19.
 */
public class PropUtil {
    private Properties properties=null;


    public PropUtil (String conf){
        properties=new Properties();
        try {
            properties.load(new FileInputStream(PropUtil.class.getClassLoader().getResource(conf).getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  Properties getProperties(){
        return properties;
    }
    public  String getKey(String key){
       return  properties.getProperty(key);
    }
}
