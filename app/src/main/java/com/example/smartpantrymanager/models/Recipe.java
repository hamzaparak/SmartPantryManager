package com.example.smartpantrymanager.models;

public class Recipe {

    private int id;
    private String name;
    private String method;

    public Recipe(int id, String name, String method) {
        this.id = id;
        this.name = name;
        this.method = method;
    }

    public Recipe(String name, String method) {
        this.name = name;
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}