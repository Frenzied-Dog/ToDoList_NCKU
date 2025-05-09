package edu.ncku.todo.util;

import java.util.Locale;
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
        return bundle.getString(key);
    }
}
