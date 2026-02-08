package com.smha.sms.user.service;

import com.smha.sms.user.model.entity.User;
import com.smha.sms.user.model.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username)
                );

        List<String> roleNames = user.getUserRoles();

        // Convert roles to GrantedAuthority
        /*Set<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());*/


        /*Set<GrantedAuthority> authorities = new HashSet<>();

        for( Role role : user.getRoles() ) {  // similar of Stream<Stream<Permission>>
            for( Permission permission : role.getPermissions() ) {   // similar of Stream<Permission>
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }*/

        /*Set<GrantedAuthority> authorities = new HashSet<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getName()))
            );
        });*/

        Set<GrantedAuthority> authorities =
                user.getRoles().stream()
                        .flatMap(role -> Stream.concat(
                                Stream.of(new SimpleGrantedAuthority("ROLE_" + role.getName())),
                                role.getPermissions().stream()
                                        .map(p -> new SimpleGrantedAuthority(p.getCode()))
                        ))
                        .collect(Collectors.toSet());

        /*Set<GrantedAuthority> authorities =
                user.getRoles().stream()
                    .flatMap(role -> role.getPermissions().stream())
                        .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toSet());*/

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);

        /*return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roleNames.toArray(new String[0]))
                .build();*/
    }
}
