package com.example.alchemy_app.repository;

import com.example.alchemy_app.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    @Query("SELECT i FROM Ingredient i WHERE i.price = 0")
    Set<Ingredient> getAllBasicIngredients();

    boolean existsIngredientByName(String name);

    @Query("SELECT i.id " +
            "FROM Ingredient i " +
            "WHERE SIZE(i.ingredients) = ?1 ")
    List<Long> findIngredientByIngredients(Integer size);

    @Query(value =
            "SELECT matches.result " +
            "FROM (SELECT COUNT(r.recipe_ingredient_id) as result " +
                    "FROM recipe r " +
                    "WHERE r.ingredient_id IN (?2) AND r.recipe_ingredient_id IN (?1) " +
                    "GROUP BY r.recipe_ingredient_id) matches",nativeQuery = true)
    List<Long> findByIngredients(List<Long> ingredientsIds,List<Long> ingredientIdsInIngredient);

}
