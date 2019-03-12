package Content.repository;

import Content.entity.HomeTheater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeTheaterRepository extends JpaRepository<HomeTheater, Long> {

    Page<HomeTheater> findAll(Pageable pageable);

    Page<HomeTheater> findByModel(String model, Pageable pageable);

}
