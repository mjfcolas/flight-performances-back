package fr.li212.flight.perfs.infrastructure.sql;

import fr.li212.flight.perfs.infrastructure.sql.db_entities.PlaneDbEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaneDbEntityRepository extends CrudRepository<PlaneDbEntity, Long> {

    List<PlaneDbEntity> findAllByOwnerId(final String ownerId);


    @Query("""
            SELECT p FROM PlaneDbEntity p
            WHERE (:registration = '' OR p.registration LIKE :registration)
            AND (:name = '' OR p.name LIKE %:name%)
            AND (:ownerName = '' OR p.owner.displayedName LIKE %:ownerName%)
            """)
    List<PlaneDbEntity> search(
            @Param("registration") final String registration,
            @Param("name") final String name,
            @Param("ownerName") final String ownerName,
            Pageable pageable
    );
}
