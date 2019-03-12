package Content.repository;

import Content.entity.PcCharacts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PcCharactRepository extends JpaRepository<PcCharacts, Long> {


}
