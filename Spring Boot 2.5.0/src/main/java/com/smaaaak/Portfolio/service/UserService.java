package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.model.User;
import com.smaaaak.Portfolio.model.projection.ProfileProjection;

import java.util.Optional;

public interface UserService {

    User addNewUser(User newUser) ;

    ProfileProjection getProfile() ;

    User updateUser(User user) ;

    void deleteUser(Long idUser) ;

    User loadUserByUsername(String username) ;

}
