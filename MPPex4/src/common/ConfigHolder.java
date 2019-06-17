package common;

import java.util.Properties;

/**
 * Created by green on 6/17/2019.
 */
public class ConfigHolder {


    private static Properties comm = new Properties();

    public static String getCommunicationProp(String key, String defVal){
        return comm.getProperty(key, defVal);
    }

    public static int getCommunicationIntProp(String key, int defVal){
        return Integer.getInteger(comm.getProperty(key, Integer.toString(defVal)));
    }
}
