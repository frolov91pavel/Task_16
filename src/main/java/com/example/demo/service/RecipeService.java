package com.example.demo.service;

import com.example.demo.domain.Ingredient;
import com.example.demo.domain.Recipe;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepo;
    private final IngredientRepository ingredientRepo;

    public RecipeService(RecipeRepository recipeRepo, IngredientRepository ingredientRepo) {
        this.recipeRepo = recipeRepo;
        this.ingredientRepo = ingredientRepo;
    }

    @Transactional
    public long addRecipe(String dishName, List<Ingredient> ingredients) {
        // 1) Создаём запись в recipes (получаем ID)
        long recipeId = recipeRepo.insertRecipe(dishName);

        // 2) Заполняем recipeId для каждого ингредиента и вставляем в ingredients
        for (Ingredient ing : ingredients) {
            ing.setRecipeId(recipeId);
        }
        ingredientRepo.insertIngredients(ingredients);

        return recipeId;
    }

    @Transactional(readOnly = true)
    public Recipe findRecipeById(long id) {
        Recipe r = recipeRepo.findById(id);
        if (r != null) {
            var ing = ingredientRepo.findByRecipeId(id);
            r.setIngredients(ing);
        }
        return r;
    }

    @Transactional(readOnly = true)
    public List<Recipe> searchRecipes(String substring) {
        // ищем рецепты
        var found = recipeRepo.searchByName(substring);
        // подтягиваем ингредиенты для каждого
        for (Recipe r : found) {
            var ing = ingredientRepo.findByRecipeId(r.getId());
            r.setIngredients(ing);
        }
        return found;
    }

    @Transactional
    public boolean removeRecipe(long recipeId) {
        // Сначала удаляем ингредиенты
        ingredientRepo.deleteByRecipeId(recipeId);
        // потом рецепт
        int rows = recipeRepo.deleteById(recipeId);
        return rows > 0;
    }
}