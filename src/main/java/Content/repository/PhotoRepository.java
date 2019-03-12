package Content.repository;

import Content.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Page<Photo> findAll(Pageable pageable);

    Page<Photo> findByModel(String model, Pageable pageable);
}
