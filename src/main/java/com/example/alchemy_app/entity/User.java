package com.example.alchemy_app.entity;


import com.example.alchemy_app.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

@Entity
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
