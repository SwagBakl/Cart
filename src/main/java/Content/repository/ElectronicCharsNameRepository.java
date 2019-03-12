package Content.repository;

import Content.entity.ElectronicCharactsName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectronicCharsNameRepository extends JpaRepository<ElectronicCharactsName, Long> {

    List<ElectronicCharactsName> findBycharName(String charName);

}
