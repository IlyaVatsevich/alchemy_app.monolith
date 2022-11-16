package com.example.alchemy_app.repository;

import com.example.alchemy_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {

}
