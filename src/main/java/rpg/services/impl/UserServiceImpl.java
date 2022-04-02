package rpg.services.impl;

import rpg.dto.UserDto;
import rpg.entity.Race;
import rpg.entity.Role;
import rpg.entity.Specialisation;
import rpg.entity.User;
import rpg.exception.EntityNotFoundException;
import rpg.mapper.UserMapper;
import rpg.repositories.UserRepository;
import rpg.services.AuthorizationService;
import rpg.services.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthorizationService authorizationService;
    private final UserRepository       userRepository;
    private final PasswordEncoder      passwordEncoder;

    public UserDto addUser(String username) {
        User user = new User();
        user.setRole(Role.USER);
        user.setName(username);
        user.setPassword(passwordEncoder.encode(username));
        userRepository.save(user);
        authorizationService.authorizeUser(user);
        return UserMapper.INSTANCE.mapToDto(user);
    }

    @Override
    public UserDto getCurrent() {
        return UserMapper.INSTANCE.mapToDto(current());
    }

    private User current() {
        Long currentId = authorizationService.getProfileOfCurrent().getId();
        return userRepository.findById(currentId)
                             .orElseThrow(() -> new EntityNotFoundException("User with id " + currentId + " doesn't exists!"));
    }

    @Override
    public UserDto race(Race race) {
        User current = setUserNumbers(race.getDefaultNumber(), race.getMaxNumber());
        current.setRace(race);
        current.setCustomised(current.getRace() != null && current.getSpecialisation() != null);
        userRepository.save(current);
        return UserMapper.INSTANCE.mapToDto(current);
    }

    @Override
    public UserDto specialisation(Specialisation specialisation) {
        User current = setUserNumbers(specialisation.getDefaultNumber(), specialisation.getMaxNumber());
        current.setSpecialisation(specialisation);
        current.setCustomised(current.getRace() != null && current.getSpecialisation() != null);
        userRepository.save(current);
        return UserMapper.INSTANCE.mapToDto(current);
    }

    private User setUserNumbers(Integer defaultNumber, Integer maxNumber) {
        User current = current();
        current.setNumber(current.getNumber() + defaultNumber);
        current.setMaxNumber(current.getMaxNumber() + maxNumber);
        return current;
    }

}
