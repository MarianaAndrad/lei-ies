package com.example.demo;

public class Greeting {
    private final long id;
    private final String name;
    private final String message;

    public Greeting(long id, String name,String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
