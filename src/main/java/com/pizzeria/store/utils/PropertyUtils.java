package com.pizzeria.store.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

    private Properties properties = null;
    private static final PropertyUtils INSTANCE = new PropertyUtils();

    private PropertyUtils() {
    }

    public static PropertyUtils getInstance() {
        if (INSTANCE.properties == null) {
            synchronized (INSTANCE) {
                if (INSTANCE.properties == null) {
                    try {
                        INSTANCE.properties = readPropertiesFile("application.properties");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return INSTANCE;
    }

    public String getPropertyValue(String key) {
        if (properties != null) {
            return properties.getProperty(key);
        }
        return null;
    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        InputStream inputStream = null;
        Properties prop = null;
        try {
            prop = new Properties();
            inputStream = PropertyUtils.class.getClassLoader().getResourceAsStream(fileName);
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return prop;
    }
}
