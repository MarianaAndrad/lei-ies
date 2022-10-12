package com.example.restservice;

public class Quote {

    private final String quote;
    private final String role;
    private final String show;
    private final Boolean contain_adult_lang;

    public Quote(String quote, String role, String show, Boolean contain_adult_lang) {
        this.quote = quote;
        this.role = role;
        this.show = show;
        this.contain_adult_lang = contain_adult_lang;
    }

    public String getQuote() {
        return quote;
    }

    public String getRole() {
        return role;
    }

    public String getShow() {
        return show;
    }

    public Boolean getContain_adult_lang() {
        return contain_adult_lang;
    }
}
