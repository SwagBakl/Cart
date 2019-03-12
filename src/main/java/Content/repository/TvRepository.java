package Content.repository;

import Content.entity.TV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TvRepository extends JpaRepository<TV, Long> {

    Page<TV> findAll(Pageable pageable);

    Page<TV> findByModel(String model, Pageable pageable);

}
