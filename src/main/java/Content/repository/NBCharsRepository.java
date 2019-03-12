package Content.repository;

import Content.entity.NoteBookChars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NBCharsRepository extends JpaRepository<NoteBookChars, Long> {
}
