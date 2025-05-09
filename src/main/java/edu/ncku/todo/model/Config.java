package edu.ncku.todo.model;

import java.util.Locale;
import java.util.Hashtable;

public abstract class Config {
    private static Hashtable<String, String> cfg = new Hashtable<>();
    
    public static Hashtable<String, String> getCFG() { return cfg; } 
    public static void initialize(Hashtable<String, String> cfg) { Config.cfg = cfg; }

    public static Locale getLocale() { return Locale.forLanguageTag(get("lang")); }
    public static String get(String key) { return cfg.get(key); }
    public static void set(String key, String value) { cfg.put(key, value); }
}
