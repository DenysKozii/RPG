package rpg.config;

import rpg.services.AuthorizationService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthorizationService authorizationService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        return authorizationService.loadUserByUsername(username);
    }
}
