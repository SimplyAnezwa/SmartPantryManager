package com.example.smartpantrymanager.model;

public class Recipe {

    private int id;
    private String name;
    private String description;
    private String instructions;
    private String imageName;

    public Recipe() {
    }

    public Recipe(
            int id,
            String name,
            String description,
            String instructions,
            String imageName) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.instructions = instructions;
        this.imageName = imageName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}