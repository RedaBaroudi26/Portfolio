package com.smaaaak.Portfolio.repository;

import com.smaaaak.Portfolio.model.User;
import com.smaaaak.Portfolio.model.projection.ProfileProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByAccount_Username(String username) ;

    Optional<ProfileProjection> findUserByFullName(String fullName) ;

}