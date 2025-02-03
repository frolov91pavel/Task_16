package com.example.demo.repository;

import com.example.demo.domain.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IngredientRepository {

    private final JdbcTemplate jdbc;

    public IngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void insertIngredients(List<Ingredient> ingredients) {
        var sql = "INSERT INTO ingredients(recipe_id, ingredient_name, amount) VALUES(?,?,?)";
        jdbc.batchUpdate(sql, ingredients, ingredients.size(),
                (ps, ing) -> {
                    ps.setLong(1, ing.getRecipeId());
                    ps.setString(2, ing.getIngredientName());
                    ps.setString(3, ing.getAmount());
                });
    }

    public List<Ingredient> findByRecipeId(long recipeId) {
        var sql = "SELECT id, recipe_id, ingredient_name, amount "
                + "FROM ingredients WHERE recipe_id=?";
        return jdbc.query(sql,
                (rs, rowNum) -> new Ingredient(
                        rs.getLong("id"),
                        rs.getLong("recipe_id"),
                        rs.getString("ingredient_name"),
                        rs.getString("amount")
                ),
                recipeId
        );
    }

    public int deleteByRecipeId(long recipeId) {
        var sql = "DELETE FROM ingredients WHERE recipe_id=?";
        return jdbc.update(sql, recipeId);
    }
}