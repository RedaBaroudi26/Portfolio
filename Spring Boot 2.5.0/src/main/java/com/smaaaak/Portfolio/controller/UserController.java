package com.smaaaak.Portfolio.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smaaaak.Portfolio.model.User;
import com.smaaaak.Portfolio.model.projection.ProfileProjection;
import com.smaaaak.Portfolio.service.UserService;
import com.smaaaak.Portfolio.share.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
public class UserController {

    private UserService userService ;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody User newUser){
        return new ResponseEntity<>(this.userService.addNewUser(newUser) , HttpStatus.CREATED) ;
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return new ResponseEntity<>(this.userService.updateUser(user) , HttpStatus.OK) ;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long idUser){
        this.userService.deleteUser(idUser); ;
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request , HttpServletResponse response )throws Exception{

        String authToken = request.getHeader(JWTUtil.AUT_HEADER) ;

        if(authToken != null && authToken.startsWith(JWTUtil.PREFIX)){

            try {

                String refreshToken = authToken.substring(JWTUtil.PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = this.userService.loadUserByUsername(username) ;

                String accessToken = JWT.create()
                        .withSubject(user.getAccount().getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",user.getAccount().getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> Tokens = new HashMap<>();
                Tokens.put("accessToken" , accessToken) ;
                Tokens.put("refreshToken" , refreshToken) ;

                response.setContentType("application/json") ;
                new ObjectMapper().writeValue(response.getOutputStream() , Tokens) ;


            }catch (Exception e){
                throw  e ;
            }

        }else{
            throw new RuntimeException(" Refresh Token required !!! ") ;
        }
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<User> profile(@PathVariable("username") String username){
        return new ResponseEntity<>(this.userService.loadUserByUsername(username) , HttpStatus.OK) ;
    }

    @GetMapping("/isAuth/{username}")
    public ResponseEntity<Boolean> isAuth(@PathVariable("username") String username){
        return new ResponseEntity<>(this.userService.loadUserByUsername(username) != null , HttpStatus.OK) ;
    }

    @GetMapping("/getProfile")
    public ResponseEntity<ProfileProjection> getProfile(){
        return new ResponseEntity<>(this.userService.getProfile() , HttpStatus.OK) ;
    }


}
