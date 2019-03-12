package Content.repository;

import Content.entity.Microwave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MicrowaveRepository extends JpaRepository<Microwave, Long> {

    Page<Microwave> findAll(Pageable pageable);

    Page<Microwave> findByModel(String model, Pageable pageable);

}
