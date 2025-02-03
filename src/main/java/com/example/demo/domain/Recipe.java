package com.example.demo.domain;

import java.util.List;

public class Recipe {
    private Long id;
    private String dishName;
    private List<Ingredient> ingredients;

    public Recipe() {
    }

    public Recipe(Long id, String dishName, List<Ingredient> ingredients) {
        this.id = id;
        this.dishName = dishName;
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Recipe{id=");
        sb.append(id)
                .append(", dishName='")
                .append(dishName)
                .append("', ingredients=[");
        if (ingredients != null) {
            for (Ingredient ing : ingredients) {
                sb.append(ing).append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}