package com.smaaaak.Portfolio.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smaaaak.Portfolio.share.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class JWTAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if( request.getServletPath().equals("/api/user/refreshToken") ){
               filterChain.doFilter(request,response);
        }else{

            String authorizationToken = request.getHeader(JWTUtil.AUT_HEADER) ;

            if(authorizationToken != null && authorizationToken.startsWith(JWTUtil.PREFIX)){

                try {

                    String jwt = authorizationToken.substring(JWTUtil.PREFIX.length());
                    Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                    String username = decodedJWT.getSubject() ;
                    String [] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<GrantedAuthority> authorities = new ArrayList<>() ;
                    for(String r : roles){
                        authorities.add(new SimpleGrantedAuthority(r)) ;
                    }
                    UsernamePasswordAuthenticationToken  authenticationToken = new UsernamePasswordAuthenticationToken(username , null ,authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request , response);

                }catch(Exception e){

                  //  response.setHeader("message",e.getMessage());
                  //  response.sendError(HttpServletResponse.SC_FORBIDDEN);

                    Map<String, String> error = new HashMap<>();
                    error.put( "message" , e.getMessage() ) ;
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream() , error);

                }

            }else{

                filterChain.doFilter(request , response);

            }


        }

    }


}
