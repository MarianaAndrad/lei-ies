package com.example.restservice;

public class Show {
    private final long id;
    private final String name;
    private final String slug;

    public Show(long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public long getId() {
        return id;
    }


}
