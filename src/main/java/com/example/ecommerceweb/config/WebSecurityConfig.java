package com.example.ecommerceweb.config;

import com.example.ecommerceweb.jwt.AuthEntryPointJwt;
import com.example.ecommerceweb.jwt.JwtAuthenticationFilter;
import com.example.ecommerceweb.service.implement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService) // cung cap userService cho Spring security
                .passwordEncoder(passwordEncoder()); // cung cap password encoder
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/","/home").permitAll()
//                .antMatchers("/api/bookstore/user/**").hasAnyAuthority("USER","ADMIN")
//                .antMatchers("/api/bookstore/admin/**").hasAuthority("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//              //  .defaultSuccessUrl("/api/bookstore/user/books")
//                .permitAll()
//                .and()
//                .logout().permitAll();
//                //.and()
//                //.exceptionHandling().accessDeniedPage("/403");
        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/bookstore/public/**").permitAll()
                .antMatchers("/api/bookstore/user/**").hasAuthority("USER")
                .antMatchers("/api/bookstore/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
