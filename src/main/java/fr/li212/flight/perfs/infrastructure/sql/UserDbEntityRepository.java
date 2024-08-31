package fr.li212.flight.perfs.infrastructure.sql;

import fr.li212.flight.perfs.infrastructure.sql.db_entities.UserDbEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDbEntityRepository extends CrudRepository<UserDbEntity, String> {

    boolean existsByNickname(final String nickname);
}
