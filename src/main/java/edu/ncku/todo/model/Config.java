package edu.ncku.todo.model;

import java.util.Locale;


public class Config {
    private String lang = "en"; // default language is English

    public String getLang() {
        return this.lang;
    }

    public Locale getLocale() {
        return Locale.forLanguageTag(this.lang);
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
