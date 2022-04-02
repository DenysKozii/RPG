package rpg.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rpg.dto.GameDto;
import rpg.entity.ActionEnum;
import rpg.entity.Game;
import rpg.entity.User;
import rpg.enums.GameStatus;
import rpg.exception.EntityNotFoundException;
import rpg.mapper.GameMapper;
import rpg.mapper.UserMapper;
import rpg.repositories.GameRepository;
import rpg.repositories.UserRepository;
import rpg.services.AuthorizationService;
import rpg.services.GameService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository       gameRepository;
    private final UserRepository       userRepository;
    private final AuthorizationService authorizationService;

    @Override
    public GameDto get() {
        Long currentId = authorizationService.getProfileOfCurrent().getId();
        User user = userRepository.findById(currentId)
                                  .orElseThrow(() -> new EntityNotFoundException("User with id " + currentId + " doesn't exists!"));
        return GameMapper.INSTANCE.mapToDto(user.getGame()).setCurrent(UserMapper.INSTANCE.mapToDto(user));
    }

    @Override
    public GameDto play() {
        Long currentId = authorizationService.getProfileOfCurrent().getId();
        User user = userRepository.findById(currentId)
                                  .orElseThrow(() -> new EntityNotFoundException("User with id " + currentId + " doesn't exists!"));
        user.setCoolDown(Math.max(0, user.getCoolDown() - 1));
        Game game;
        if (user.getGame() == null) {
            game = gameRepository.findByStatus(GameStatus.WAITING).orElse(new Game());
            user.setGame(game);
            if (GameStatus.WAITING.equals(game.getStatus())) {
                game.setReady(true);
                game.setStatus(GameStatus.RUNNING);
            } else {
                game.setStatus(GameStatus.WAITING);
            }
            List<ActionEnum> actions = new ArrayList<>(List.of(ActionEnum.values()));
            Collections.shuffle(actions);
            user.setFirst(actions.get(0));
            user.setSecond(actions.get(1));
            user.setThird(actions.get(2));
            userRepository.save(user);
            game.getUsers().add(user);
            gameRepository.save(game);
        } else {
            game = user.getGame();
            if (game.getStatus().equals(GameStatus.RUNNING)) {
                game.setDeadline(game.getDeadline() - 1);
                gameRepository.save(game);
            }
            if (game.getDeadline() <= 0 && !game.isCompleted()) {
                game.setStatus(GameStatus.COMPLETED);
                game.setCompleted(true);
                User first = game.getUsers().get(0);
                User second = game.getUsers().get(1);
                if (first.getNumber() > second.getNumber()) {
                    first.setRating(first.getRating() + 10);
                    second.setRating(Math.max(0, second.getRating() - 5));
                    game.setWinner(first.getName());
                } else if (first.getNumber() < second.getNumber()) {
                    second.setRating(second.getRating() + 10);
                    first.setRating(Math.max(0, first.getRating() - 5));
                    game.setWinner(second.getName());
                } else {
                    game.setWinner("No winner!");
                }
//                first.setGame(null);
//                second.setGame(null);
//                userRepository.save(first);
//                userRepository.save(second);
//                gameRepository.delete(game);
            }
        }
        System.out.println(game.getDeadline());
        return GameMapper.INSTANCE.mapToDto(game).setCurrent(UserMapper.INSTANCE.mapToDto(user));
    }


    @Override
    public void delete() {
        Long currentId = authorizationService.getProfileOfCurrent().getId();
        User user = userRepository.findById(currentId)
                                  .orElseThrow(() -> new EntityNotFoundException("User with id " + currentId + " doesn't exists!"));
        Game game = user.getGame();
        if (game != null) {
            User first = game.getUsers().get(0);
            User second = game.getUsers().get(1);
            first.setGame(null);
            second.setGame(null);
            userRepository.save(first);
            userRepository.save(second);
            gameRepository.delete(game);
        }
    }

    @Override
    public GameDto select(ActionEnum action) {
        Long currentId = authorizationService.getProfileOfCurrent().getId();
        User current = userRepository.findById(currentId)
                                     .orElseThrow(() -> new EntityNotFoundException("User with id " + currentId + " doesn't exists!"));
        Game game = current.getGame();
        for (User user : game.getUsers()) {
            if (!user.equals(current)) {
                switch (action) {
                    case MINUS_50:
                        user.setNumber(user.getNumber() - 50);
                        break;
                    case MINUS_500:
                        user.setNumber(user.getNumber() - 500);
                        break;
                    case DIVIDE_2:
                        user.setNumber(user.getNumber() / 2);
                        break;
                    case DIVIDE_3:
                        user.setNumber(user.getNumber() / 3);
                        break;
                }
                user.setNumber(Math.max(0, user.getNumber()));
                user.setNumber(Math.min(user.getMaxNumber(), user.getNumber()));
                userRepository.save(user);
            }
        }

        switch (action) {
            case PLUS_50:
                current.setNumber(current.getNumber() + 50);
                break;
            case PLUS_0:
                break;
            case PLUS_100:
                current.setNumber(current.getNumber() + 100);
                break;
            case PLUS_1000:
                current.setNumber(current.getNumber() + 1000);
                break;
            case MULTIPLY_2:
                current.setNumber(current.getNumber() * 2);
                break;
            case MULTIPLY_3:
                current.setNumber(current.getNumber() * 3);
                break;
            case MULTIPLY_10:
                current.setNumber(current.getNumber() * 10);
                break;
        }
        // TODO optimize
        List<ActionEnum> actions = new ArrayList<>(List.of(ActionEnum.values()));
        Collections.shuffle(actions);
        if (action.equals(current.getFirst())) {
            current.setFirst(actions.get(0));
        }
        if (action.equals(current.getSecond())) {
            current.setSecond(actions.get(0));
        }
        if (action.equals(current.getThird())) {
            current.setThird(actions.get(0));
        }
        // TODO changeable
        current.setCoolDown(5);
        current.setNumber(Math.max(0, current.getNumber()));
        current.setNumber(Math.min(current.getMaxNumber(), current.getNumber()));
        userRepository.save(current);
        return GameMapper.INSTANCE.mapToDto(game).setCurrent(UserMapper.INSTANCE.mapToDto(current));
    }


}
