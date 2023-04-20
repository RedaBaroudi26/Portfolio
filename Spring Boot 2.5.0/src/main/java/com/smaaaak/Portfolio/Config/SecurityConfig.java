package com.smaaaak.Portfolio.Config;



import com.smaaaak.Portfolio.filter.JWTAuthenticationFilter;
import com.smaaaak.Portfolio.filter.JWTAuthorizationFilter;
import com.smaaaak.Portfolio.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService ;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable() ;
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) ;
        http.headers().frameOptions().disable();
       // http.authorizeRequests().antMatchers("**").permitAll() ;
        http.authorizeRequests().antMatchers("/api/users/refreshToken").permitAll() ;
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/tags/**" , "/api/categories/**" ,"/api/comments/**", "/api/articles/**" , "/api/projects/**"  , "/api/users/getProfile" , "/api/skills/**" ).permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/comments/**" , "/api/replies/**" , "/login/**").permitAll();
        http.authorizeRequests().antMatchers("/api/tags/**" , "/api/categories/**" , "/api/articles/**" , "/api/roles/**" , "api/users/**", "/api/comments/**" ,"/api/skills/**" ).hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated() ;
        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
        http.addFilterBefore(new JWTAuthorizationFilter() , UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "http://localhost:4200", new CorsConfiguration().applyPermitDefaultValues()) ;
       // source.registerCorsConfiguration( "https://smaaaktest.web.app", new CorsConfiguration().applyPermitDefaultValues()) ;
       // source.registerCorsConfiguration( "https://Smaaaak-v1.web.app", new CorsConfiguration().applyPermitDefaultValues()) ;
        return source;
    }


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
