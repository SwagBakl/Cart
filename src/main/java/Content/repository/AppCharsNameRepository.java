package Content.repository;

import Content.entity.AppliancesCharName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppCharsNameRepository extends JpaRepository<AppliancesCharName, Long> {

    List<AppliancesCharName> findBycharName(String charName);

}
