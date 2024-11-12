package com.example.surveyapp.Security;

import com.example.surveyapp.Models.User;
import com.example.surveyapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        userOptional.orElseThrow(() -> new UsernameNotFoundException("Username does not exist"));
        System.out.println("hellolol");
        return userOptional.map(user -> new MyUserDetails(user)).get();
    }
}