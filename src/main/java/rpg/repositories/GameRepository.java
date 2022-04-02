package rpg.repositories;

import rpg.entity.Game;
import rpg.entity.User;
import rpg.enums.GameStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long>, PagingAndSortingRepository<Game, Long> {

    Optional<Game> findByUsers(User user);

    Optional<Game> findByStatus(GameStatus status);

}
