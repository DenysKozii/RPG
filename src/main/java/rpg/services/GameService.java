package rpg.services;


import rpg.dto.GameDto;
import rpg.entity.ActionEnum;

public interface GameService {

    GameDto get();

    GameDto play();

    GameDto select(ActionEnum action);

    void delete();
}
