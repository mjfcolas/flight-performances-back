package fr.li212.flight.perfs.infrastructure.sql;

import fr.li212.flight.perfs.infrastructure.sql.db_entities.PlaneDbEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaneDbEntityRepository extends CrudRepository<PlaneDbEntity, Long> {

    List<PlaneDbEntity> findAllByOwnerId(final String ownerId);
}
