package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.Exception.UserNotFoundException;
import com.smaaaak.Portfolio.model.User;
import com.smaaaak.Portfolio.model.projection.ProfileProjection;
import com.smaaaak.Portfolio.repository.UserRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository ;
    private PasswordEncoder passwordEncoder ;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addNewUser(User newUser) {
        if(this.userRepository.findUserByAccount_Username(newUser.getAccount().getUsername()).isPresent()){
            throw new ApiRequestException("username already exists") ;
        }
        String password = newUser.getAccount().getPassword() ;
        newUser.getAccount().setPassword(passwordEncoder.encode(password));
        return this.userRepository.save(newUser);
    }

    @Override
    public ProfileProjection getProfile() {
        return this.userRepository.findUserByFullName("Reda Baroudi").get();
    }

    @Override
    public User updateUser(User user) {
        if(!this.userRepository.findById(user.getIdUser()).isPresent()){
            throw new ApiRequestException("user doesn't exists") ;
        }
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(Long idUser) {
        if(!this.userRepository.findById(idUser).isPresent()){
            throw new ApiRequestException("user doesn't exists") ;
        }
        this.userRepository.deleteById(idUser);
    }

    @Override
    public User loadUserByUsername(String username) {
        if(!this.userRepository.findUserByAccount_Username(username).isPresent() ){
            throw new UserNotFoundException("User Not Found") ;
        }
        return this.userRepository.findUserByAccount_Username(username).get() ;
    }

}
