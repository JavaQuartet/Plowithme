package com.example.Plowithme.security;

import com.example.Plowithme.entity.User;
import com.example.Plowithme.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //이메일로
          User user = userRepository.findByEmail(username)
                 .orElseThrow(() ->
                         new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                user.getAuthorities());
    }
}
