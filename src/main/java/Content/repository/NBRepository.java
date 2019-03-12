package Content.repository;

import Content.entity.NoteBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NBRepository extends JpaRepository<NoteBook, Long> {

    Page<NoteBook> findAll(Pageable pageable);

    Page<NoteBook> findByModel(String model, Pageable pageable);

}
