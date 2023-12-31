package com.practice.board.global.security.auth;

import com.practice.board.domain.persistence.user.User;
import com.practice.board.domain.persistence.user.UserRepository;
import com.practice.board.domain.service.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return new CustomUserDetails(user.getUsername());
    }

}