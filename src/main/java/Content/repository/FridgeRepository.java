package Content.repository;

import Content.entity.Fridge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<Fridge, Long> {

    Page<Fridge> findAll(Pageable pageable);

    Page<Fridge> findByModel(String model, Pageable pageable);

}
