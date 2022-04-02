package rpg.services.impl;

import rpg.dto.user.UserProfileDto;
import rpg.entity.User;
import rpg.repositories.UserRepository;
import rpg.services.AuthorizationService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserRepository userRepository;

    @Override
    public void authorizeUser(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getId(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

    @Override
    public UserProfileDto getProfileOfCurrent() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Long.valueOf(principal instanceof UserDetails ?
                ((UserDetails)principal).getUsername() : principal.toString());
        return new UserProfileDto(
                userId,
                userId.toString()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.valueOf(username)).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with id %s not found!", username))
        );

        return org.springframework.security.core.userdetails.User.withUsername(user.getId().toString())
                .password(user.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole())))
                .build();
    }
}
