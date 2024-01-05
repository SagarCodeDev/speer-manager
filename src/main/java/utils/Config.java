package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    private static Properties properties = new Properties();

    public Config(){

    }

    public static void loadConfigs(String[] args){
        for(String str: args){
            try {
                Properties propTemp = new Properties();
                FileInputStream file = new FileInputStream(str);
                propTemp.loadFromXML(file);
                for (Object obj : propTemp.keySet()) {
                    properties.put(obj, propTemp.get(obj));
                }
            }
            catch (IOException ioException){
                Logger.getGlobal().log(Level.WARNING, "Error while reading file input");
            }
        }
    }

    public static String getConfigs(String key){
        if(properties.containsKey(key)){
            return properties.getProperty(key);
        }
        return null;
    }
}

