package com.smaaaak.Portfolio.service;

import com.smaaaak.Portfolio.Exception.ApiRequestException;
import com.smaaaak.Portfolio.Exception.UserNotFoundException;
import com.smaaaak.Portfolio.dto.ProfileDto;
import com.smaaaak.Portfolio.mapper.MapStructMapper;
import com.smaaaak.Portfolio.model.User;
import com.smaaaak.Portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder ;
    private final MapStructMapper mapper ;

    @Override
    public User addNewUser(User newUser) {
        this.userRepository.findUserByAccount_Username(newUser.getAccount().getUsername()).ifPresent(
                (user) -> { throw new ApiRequestException("username already exists") ; }
        );
        String password = newUser.getAccount().getPassword() ;
        newUser.getAccount().setPassword(passwordEncoder.encode(password));

        return this.userRepository.save(newUser);
    }

    @Override
    public ProfileDto getProfile() {
        return mapper.userToProfileDTO( this.userRepository.findUserByFullName("Reda Baroudi").orElseThrow(
                () -> new ApiRequestException(" User Not Found")
        )) ;
    }

    @Override
    public User updateUser(User user) {
        this.userRepository.findById(user.getIdUser()).orElseThrow(
                () -> new ApiRequestException("user doesn't exists")
        );
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(Long idUser) {
        this.userRepository.findById(idUser).orElseThrow(
                () -> new ApiRequestException("user doesn't exists")
        );
        this.userRepository.deleteById(idUser);
    }

    @Override
    public User loadUserByUsername(String username) {
        this.userRepository.findUserByAccount_Username(username).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );
        return this.userRepository.findUserByAccount_Username(username).get() ;
    }

}
