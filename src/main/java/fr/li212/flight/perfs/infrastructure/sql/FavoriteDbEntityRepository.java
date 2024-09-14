package fr.li212.flight.perfs.infrastructure.sql;

import fr.li212.flight.perfs.infrastructure.sql.db_entities.FavoriteDbEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteDbEntityRepository extends CrudRepository<FavoriteDbEntity, Long> {

    List<FavoriteDbEntity> findAllByUserId(final String userId);

    void deleteByPlaneIdAndUserId(Long planeId, String userId);

    void deleteByPlaneId(Long planeId);
}
