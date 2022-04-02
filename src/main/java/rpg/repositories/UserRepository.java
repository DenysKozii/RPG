package rpg.repositories;

import rpg.entity.Game;
import rpg.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    Set<User> findAllByGame(Game game);

}
