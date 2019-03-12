package Content.repository;

import Content.entity.AppliancesCharValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppCharsValueRepository extends JpaRepository<AppliancesCharValue, Long> {

    List<AppliancesCharValue> findBycharValue(String charValue);

}
