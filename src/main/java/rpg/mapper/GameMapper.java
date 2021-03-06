package rpg.mapper;

import rpg.dto.GameDto;
import rpg.entity.Game;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GameMapper {
    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    GameDto mapToDto(Game game);
}
