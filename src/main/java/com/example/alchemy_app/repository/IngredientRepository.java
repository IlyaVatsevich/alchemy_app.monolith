package com.example.alchemy_app.repository;

import com.example.alchemy_app.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {}
