package Content.repository;

import Content.entity.ElectronicCharactsValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectronicCharsValueRepository extends JpaRepository<ElectronicCharactsValue, Long> {

    List<ElectronicCharactsValue> findBycharValue(String charValue);
}
