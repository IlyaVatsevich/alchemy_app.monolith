package com.example.alchemy_app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@Table(name = "recipes")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "recipe_ingredient_id")
    @ReadOnlyProperty
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "recipe_ingredient_id")
    private Ingredient recipeIngredientId;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "recipes",
            joinColumns = { @JoinColumn(name = "recipe_ingredient_id") },
            inverseJoinColumns = { @JoinColumn(name = "ingredients_id") }
    )
    private List<Ingredient> ingredientsId;

}
