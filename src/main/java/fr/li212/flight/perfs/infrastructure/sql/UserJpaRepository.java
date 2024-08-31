package fr.li212.flight.perfs.infrastructure.sql;

import fr.li212.flight.perfs.domain.model.User;
import fr.li212.flight.perfs.domain.model.exception.UserAlreadyExistsException;
import fr.li212.flight.perfs.domain.repository.UserRepository;
import fr.li212.flight.perfs.infrastructure.sql.db_entities.UserDbEntity;

import java.util.Optional;

public class UserJpaRepository implements UserRepository {

    private final UserDbEntityRepository userDbEntityRepository;

    public UserJpaRepository(final UserDbEntityRepository userDbEntityRepository) {
        this.userDbEntityRepository = userDbEntityRepository;
    }

    @Override
    public Optional<User> findById(String userId) {
        return this.userDbEntityRepository.findById(userId).map(UserDbEntity::toDomain);
    }

    @Override
    public User save(User user) {
        if (this.userDbEntityRepository.existsByNickname(user.nickname())) {
            throw new UserAlreadyExistsException();
        }
        return this.userDbEntityRepository.save(UserDbEntity.fromDomain(user)).toDomain();
    }
}
