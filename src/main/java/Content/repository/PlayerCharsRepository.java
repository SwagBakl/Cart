package Content.repository;

import Content.entity.PlayerChars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerCharsRepository extends JpaRepository<PlayerChars, Long> {
}
