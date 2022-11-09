package com.example.alchemy_app.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Builder(setterPrefix = "with")
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user_ingredient")
public class UserIngredient {

    @EmbeddedId
    private UserIngredientId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id",nullable = false)
    private User userId;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id",nullable = false)
    private Ingredient ingredient;

    @Column(name = "count",nullable = false)
    private Integer count;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserIngredientId implements Serializable {

        private static final long serialVersionUID = 1L;

        @Column(name = "ingredient_id",nullable = false)
        private Long ingredientId;

        @Column(name = "user_id",nullable = false)
        private Long userId;

    }

}
