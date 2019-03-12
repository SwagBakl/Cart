package Content.repository;

import Content.entity.Tab;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TabRepository extends JpaRepository<Tab, Long> {

    Page<Tab> findAll(Pageable pageable);

    Page<Tab> findByModel(String model, Pageable pageable);

}
