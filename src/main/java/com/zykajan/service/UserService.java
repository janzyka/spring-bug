package com.zykajan.service;

import com.zykajan.dao.UsersDao;
import com.zykajan.data.User;
import com.zykajan.web.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    @Autowired
    private UsersDao usersDao;

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
        return loadUserByUsername(token.getName());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser;

        try {
            dbUser = usersDao.getUserByName(username);
        } catch (ResourceNotFoundException ex) {
            throw new UsernameNotFoundException("User '" + username + "' doesn't exist.");
        }

        return  new org.springframework.security.core.userdetails.User(
                dbUser.getName(),
                dbUser.getPassword(),
                Collections.<GrantedAuthority>emptyList()
        );
    }
}
