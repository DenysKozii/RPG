package rpg.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rpg.entity.ActionEnum;
import rpg.entity.Race;
import rpg.entity.Specialisation;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDto extends BaseDto {

    private String name;

    private Integer number;

    private Integer maxNumber;

    private Integer rating;

    private Integer coolDown = 0;

    private boolean customised;

    private Race           race;

    private Specialisation specialisation;

    private ActionEnum first;

    private ActionEnum second;

    private ActionEnum third;

}
