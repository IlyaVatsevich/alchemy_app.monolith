package com.example.alchemy_app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Builder(setterPrefix = "with")
@AllArgsConstructor
@Getter
@Setter
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "token",unique = true,nullable = false,length = 36)
    private String token;

    @OneToOne
    @MapsId()
    @JoinColumn(name = "user_id",nullable = false,unique = true)
    private User user;

    @Column(name = "expiry_date",nullable = false)
    private LocalDateTime expiryDate;

}
