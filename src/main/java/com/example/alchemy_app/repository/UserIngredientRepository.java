package com.example.alchemy_app.repository;

import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Set;

public interface UserIngredientRepository extends JpaRepository<UserIngredient, UserIngredient.UserIngredientId> {

    Set<UserIngredient> findUserIngredientsByUser(User user);

    Page<UserIngredient> findUserIngredientByUser(User user, Pageable pageable);

    boolean existsUserIngredientByIngredientAndUser(Ingredient ingredient, User user);

    UserIngredient findUserIngredientByIngredientAndUser(Ingredient ingredient, User user);

    @Query("SELECT (count(ui)>0) FROM UserIngredient ui WHERE ui.ingredient.id = ?1 AND ui.user = ?2 AND ui.count > 0")
    boolean existsUserIngredientByIngredientIdAndUserAndCountGreaterThan(Long ingredientId,User user);

}
