package Content.repository;

import Content.entity.Washer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasherRepository extends JpaRepository<Washer, Long> {

    Page<Washer> findAll(Pageable pageable);

    Page<Washer> findByModel(String model, Pageable pageable);

}
