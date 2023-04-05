package com.example.demo.security;

import com.example.demo.User;
import com.example.demo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("user does not exist");
        }
        List<GrantedAuthorityImpl> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
        return new UserDetailImpl(
                username,
                user.getPassword(),
                grantedAuthorities
        );
    }
}
