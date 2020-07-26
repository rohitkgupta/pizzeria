package com.pizzeria.store.utils;

import java.util.HashMap;
import java.util.Map;

public class PropertyUtils {

    //We can read from property file instead of map
    private Map<String, String> properties = null;
    private static final PropertyUtils INSTANCE = new PropertyUtils();

    private PropertyUtils() {
    }

    public static PropertyUtils getInstance() {
        if (INSTANCE.properties == null) {
            synchronized (INSTANCE) {
                INSTANCE.properties = new HashMap<>();
                INSTANCE.properties.put("PIZZA-rules", "veg-pizza-topping-rule,paneer-topping-rule,pizza-crust-rule,nonveg-pizza-topping-rule");
                INSTANCE.properties.put("PIZZA-offerrules", "large-pizza-topping-offer");
            }
        }
        return INSTANCE;
    }


    public String getPropertyValue(String key) {
        if (properties != null) {
            return properties.get(key);
        }
        return null;
    }
}
