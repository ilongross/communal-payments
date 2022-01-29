package com.ilongross.communal_payments.security;

import com.ilongross.communal_payments.model.entity.UserAuthEntity;
import com.ilongross.communal_payments.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DbUserDetailService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userAuthRepository
                .findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found."));
        var collect = user.getRoles().stream()
                .map(e -> new SimpleGrantedAuthority(e.getName())).toList();
        return new User(user.getUsername(), user.getPassword(), collect);
    }
}
