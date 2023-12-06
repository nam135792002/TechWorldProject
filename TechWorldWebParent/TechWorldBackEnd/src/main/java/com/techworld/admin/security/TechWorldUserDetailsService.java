package com.techworld.admin.security;

import com.techworld.admin.user.UserRepository;
import com.techworld.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TechWorldUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if(user != null){
            return new TechWorldUserDetails(user);
        }
        throw new UsernameNotFoundException("Could bot find user with email: " + email);
    }
}
