package com.smaaaak.Portfolio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService ;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.smaaaak.Portfolio.model.User user = this.userService.loadUserByUsername(username) ;
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getAccount().getRoles().forEach(r -> {
                authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        return new User(user.getAccount().getUsername(), user.getAccount().getPassword(), authorities);

    }

}
