package com.zykajan;

import com.zykajan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // TODO: Comment out @Autowired to make Boot load  data.sql
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated();
        http
                .csrf().disable()
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?errorFormLogin")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll()
                    .and()
                .openidLogin()
                    .loginPage("/login")
                    .failureUrl("/login?errorOpenIdLogin")
                    .permitAll()
                    .authenticationUserDetailsService(userService)
                    .attributeExchange("https://www.google.com/.*")
                        .attribute("email")
                        .type("http://axschema.org/contact/email")
                        .required(true)
                        .and()
                    .attribute("firstname")
                        .type("http://axschema.org/namePerson/first")
                        .required(true)
                        .and()
                    .attribute("lastname")
                        .type("http://axschema.org/namePerson/last")
                        .required(true);
    }

    @Configuration
    protected static class AuthenticationConfiguration extends
            GlobalAuthenticationConfigurerAdapter {

        // TODO: Comment out @Autowired to make Boot load  data.sql
        @Autowired
        private UserService userService;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {

            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

            authProvider.setPasswordEncoder(new PlaintextPasswordEncoder());
            authProvider.setUserDetailsService(userService);

            auth.authenticationProvider(authProvider);
        }

    }
}