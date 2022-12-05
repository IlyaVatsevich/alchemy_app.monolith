package com.example.alchemy_app.repository;

import com.example.alchemy_app.dto.HighScoreTable;
import com.example.alchemy_app.entity.User;
import com.example.alchemy_app.entity.UserIngredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByMail(String mail);

    boolean existsByLogin(String login);
    @Query(name = "high_score_table_by_max_gold",nativeQuery = true)
    Page<HighScoreTable> findUsersByMaxGold(Pageable pageable);

    @Query(name = "high_score_table_by_max_ingredient_count",nativeQuery = true)
    Page<HighScoreTable> findUsersByMaxUserIngredientCount(Pageable pageable);

    @Query("SELECT ui " +
            "FROM User u JOIN UserIngredient ui ON u.id = ui.user.id " +
            "WHERE u = ?1 ")
    Page<UserIngredient> findUserIngredientByUser(User user,Pageable pageable);



}
