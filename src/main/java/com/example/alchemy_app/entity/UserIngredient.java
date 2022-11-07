package com.example.alchemy_app.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Builder(setterPrefix = "with")
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user_ingredient")
public class UserIngredient {

    @Id
    @Column(name = "ingredient_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredientId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "count")
    private int count;

}
