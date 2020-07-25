package com.pizzeria.store.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
                        INSTANCE.properties = readPropertiesFile("/Users/rohitgupta/Documents/Workplace/pocs/pizza-factory/src/main/resources/application.properties");
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
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return prop;
    }
}
