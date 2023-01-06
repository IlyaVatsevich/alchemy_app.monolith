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

    @Query(value =
            "SELECT u.login as login,u.gold as score, row_number() over (order by MAX(u.gold) DESC) as place " +
            "FROM usr u JOIN user_ingredient ui on u.id = ui.user_id " +
            "GROUP BY u.id " +
            "ORDER BY place ",nativeQuery = true)
    Page<HighScoreTable> findUsersByMaxGold(Pageable pageable);

    @Query(value =
            "SELECT u.login as login,SUM(ui.count) as score, row_number() over (order by SUM(ui.count) DESC) as place " +
            "FROM usr u JOIN user_ingredient ui on u.id = ui.user_id JOIN ingredient i on i.id = ui.ingredient_id " +
            "WHERE i.price >0 AND i.loss_probability>0 " +
            "GROUP BY u.id " +
            "ORDER BY place ",nativeQuery = true)
    Page<HighScoreTable> findUsersByMaxUserIngredientCount(Pageable pageable);

    Optional<User> findUserByLogin(String login);





}
