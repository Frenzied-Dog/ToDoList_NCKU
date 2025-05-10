package edu.ncku.todo.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class Lang {
    private static ResourceBundle bundle;

    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("lang.ui", locale);
    }

    public static String get(String key) {
        if (bundle == null) {
            // 預設語言
            setLocale(Locale.ENGLISH);
        }
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            try {
                // 嘗試使用預設語言
                return ResourceBundle.getBundle("lang.ui", Locale.ENGLISH).getString(key);
            } catch (MissingResourceException ee) {
                System.err.println("Missing resource: " + key);
                return key; // 如果找不到對應的鍵，則返回鍵本身
            }
        }
    }
}
