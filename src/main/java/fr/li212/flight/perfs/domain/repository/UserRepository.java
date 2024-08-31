package fr.li212.flight.perfs.domain.repository;

import fr.li212.flight.perfs.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(final String userId);
    User save(final User user);
}
