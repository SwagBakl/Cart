package Content.repository;

import Content.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Page<Player> findAll(Pageable pageable);

    Page<Player> findByModel(String model, Pageable pageable);

}
