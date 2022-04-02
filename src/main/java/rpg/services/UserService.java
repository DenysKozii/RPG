package rpg.services;

import rpg.dto.UserDto;
import rpg.entity.Race;
import rpg.entity.Specialisation;

public interface UserService {

    UserDto addUser(String username);

    UserDto getCurrent();

    UserDto race(Race race);

    UserDto specialisation(Specialisation specialisation);
}
