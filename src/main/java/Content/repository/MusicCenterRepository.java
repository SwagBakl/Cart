package Content.repository;

import Content.entity.MusicCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicCenterRepository extends JpaRepository<MusicCenter, Long> {

    Page<MusicCenter> findAll(Pageable pageable);

    Page<MusicCenter> findByModel(String model, Pageable pageable);

}
