package com.example.demo.repository;

import com.example.demo.domain.Recipe;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeRepository {

    private final JdbcTemplate jdbc;

    public RecipeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public long insertRecipe(String dishName) {
        // insert row, вернуть сгенерированный id
        // В H2 можно использовать запросы типа RETURN_GENERATED_KEYS
        var sql = "INSERT INTO recipes(dish_name) VALUES(?)";
        // update(...) с ключами:
        var keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
        jdbc.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, dishName);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Recipe findById(long id) {
        var sql = "SELECT id, dish_name FROM recipes WHERE id = ?";
        return jdbc.query(sql, rs -> {
            if (rs.next()) {
                var recId = rs.getLong("id");
                var dishName = rs.getString("dish_name");
                return new Recipe(recId, dishName, null);
            }
            return null;
        }, id);
    }

    public List<Recipe> searchByName(String substring) {
        var sql = "SELECT id, dish_name FROM recipes "
                + "WHERE LOWER(dish_name) LIKE LOWER(CONCAT('%', ?, '%'))";
        return jdbc.query(sql,
                (rs, rowNum) -> new Recipe(rs.getLong("id"), rs.getString("dish_name"), null),
                substring
        );
    }

    public int deleteById(long id) {
        var sql = "DELETE FROM recipes WHERE id=?";
        return jdbc.update(sql, id);
    }
}