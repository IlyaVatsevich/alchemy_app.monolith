package com.example.alchemy_app.repository;

import com.example.alchemy_app.dto.HighScoreTable;
import com.example.alchemy_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByMail(String mail);

    boolean existsByLogin(String login);
    @Query(name = "high_score_table_by_max_gold",nativeQuery = true)
    Page<HighScoreTable> findUsersByMaxGold(Pageable pageable);

    @Query(name = "high_score_table_by_max_ingredient_count",nativeQuery = true)
    Page<HighScoreTable> findUsersByMaxUserIngredientCount(Pageable pageable);

    Optional<User> findUserByLogin(String login);



}
