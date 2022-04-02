package rpg.services;

import rpg.dto.user.UserProfileDto;
import rpg.entity.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthorizationService {

    void authorizeUser(User user);

    UserProfileDto getProfileOfCurrent();

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
