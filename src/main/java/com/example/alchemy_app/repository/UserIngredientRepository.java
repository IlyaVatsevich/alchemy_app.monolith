package com.example.alchemy_app.repository;

import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserIngredientRepository extends JpaRepository<UserIngredient, UserIngredient.UserIngredientId> {

    Page<UserIngredient> findUserIngredientByUser(User user, Pageable pageable);

}
