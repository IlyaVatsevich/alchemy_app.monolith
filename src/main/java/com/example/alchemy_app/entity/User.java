package com.example.alchemy_app.entity;


import com.example.alchemy_app.dto.HighScoreTable;
import com.example.alchemy_app.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import java.util.Set;

@Entity
@NamedNativeQuery(
        name = "high_score_table_by_max_gold",
        query = "SELECT u.login as login,u.gold as score, row_number() over (order by MAX(u.gold) DESC) as place " +
                "FROM usr u JOIN user_ingredient ui on u.id = ui.user_id " +
                "GROUP BY u.id " +
                "ORDER BY place ",
        resultSetMapping = "high_score_table")
@NamedNativeQuery(
        name = "high_score_table_by_max_ingredient_count",
        query = "SELECT u.login as login,SUM(ui.count) as score, row_number() over (order by SUM(ui.count) DESC) as place " +
                "FROM usr u JOIN user_ingredient ui on u.id = ui.user_id JOIN ingredient i on i.id = ui.ingredient_id " +
                "WHERE i.price >0 AND i.loss_probability>0 " +
                "GROUP BY u.id " +
                "ORDER BY place ",
        resultSetMapping = "high_score_table")
@SqlResultSetMappings(
        @SqlResultSetMapping(
                name = "high_score_table",
                classes = @ConstructorResult(
                        targetClass = HighScoreTable.class,
                        columns = {
                                @ColumnResult(name = "login",type = String.class),
                                @ColumnResult(name = "score",type = Long.class),
                                @ColumnResult(name = "place",type = Long.class)
                        }) )
)
@NoArgsConstructor
@Builder(setterPrefix = "with")
@AllArgsConstructor
@Getter
@Setter
@Table(name = "usr")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_id_seq",
            allocationSize = 100,initialValue = 500)
    private Long id;

    @ElementCollection(targetClass = UserRole.class,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> userRole;

    @Column(name = "mail",unique = true,nullable = false)
    private String mail;

    @Column(name = "login",unique = true,nullable = false)
    private String login;

    @Column(name = "password",nullable = false)
    private char[] password;

    @Column(name = "is_active",nullable = false)
    private boolean isActive;

    @Column(name = "gold",nullable = false)
    private int gold;

}
