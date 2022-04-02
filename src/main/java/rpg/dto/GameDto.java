package rpg.dto;

import rpg.enums.GameStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GameDto extends BaseDto {

    private GameStatus status;

    private boolean ready;

    private boolean completed;

    private String winner;

    private List<UserDto> users;

    private UserDto current;

    public GameDto setCurrent(UserDto current) {
        this.current = current;
        return this;
    }
}
