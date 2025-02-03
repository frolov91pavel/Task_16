package com.example.demo.domain;

public class Ingredient {
    private Long id;
    private Long recipeId;
    private String ingredientName;
    private String amount;

    public Ingredient() {
    }

    public Ingredient(Long id, Long recipeId, String ingredientName, String amount) {
        this.id = id;
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return ingredientName + " (" + amount + ")";
    }
}