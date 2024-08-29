package fr.li212.flight.perfs.domain.repository;

import fr.li212.flight.perfs.domain.model.CreatePlaneCommand;
import fr.li212.flight.perfs.domain.model.Plane;
import fr.li212.flight.perfs.domain.model.SearchCommand;
import fr.li212.flight.perfs.domain.model.UpdatePlaneCommand;
import fr.li212.flight.perfs.security.AuthenticatedEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PlaneRepository {

    Collection<Plane> forUser(final String userId);

    List<Plane> favoritesForUser(final String userId);

    Plane create(final CreatePlaneCommand command);

    Plane update(final UpdatePlaneCommand command, final String authenticatedUserId);

    void addFavoritePlaneForUser(final String planeId, String userId);

    void removeFavoritePlaneForUser(String planeId, String userId);

    Optional<Plane> findById(String id);

    List<Plane> search(final SearchCommand searchQuery, final int limit);
}
