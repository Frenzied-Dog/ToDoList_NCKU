package edu.ncku.todo.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class Lang {
    public static ResourceBundle bundle = ResourceBundle.getBundle("lang.ui", Locale.ENGLISH);

    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("lang.ui", locale);
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            try {
                // try to use default language
                return ResourceBundle.getBundle("lang.ui", Locale.ENGLISH).getString(key);
            } catch (MissingResourceException ee) {
                System.err.println("Missing resource: " + key);
                return key; // return the key itself if not found
            }
        }
    }
}
