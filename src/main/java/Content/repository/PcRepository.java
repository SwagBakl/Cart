package Content.repository;

import Content.entity.Pc;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PcRepository extends CrudRepository<Pc, Long> {

    List<Pc> findByModel(String model);

    List<Pc> findByPrice(Integer price);

}
