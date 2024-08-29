package fr.li212.flight.perfs.infrastructure.sql;

import fr.li212.flight.perfs.domain.model.CreatePlaneCommand;
import fr.li212.flight.perfs.domain.model.Plane;
import fr.li212.flight.perfs.domain.model.SearchCommand;
import fr.li212.flight.perfs.domain.model.UpdatePlaneCommand;
import fr.li212.flight.perfs.domain.model.exception.NotFoundException;
import fr.li212.flight.perfs.domain.repository.PlaneRepository;
import fr.li212.flight.perfs.infrastructure.sql.db_entities.FavoriteDbEntity;
import fr.li212.flight.perfs.infrastructure.sql.db_entities.PlaneDbEntity;
import fr.li212.flight.perfs.infrastructure.sql.db_entities.UserDbEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PlaneJpaRepository implements PlaneRepository {

    private final PlaneDbEntityRepository planeDbEntityRepository;
    private final FavoriteDbEntityRepository favoriteDbEntityRepository;
    private final UserDbEntityRepository userDbEntityRepository;

    public PlaneJpaRepository(
            final PlaneDbEntityRepository planeDbEntityRepository,
            final FavoriteDbEntityRepository favoriteDbEntityRepository,
            final UserDbEntityRepository userDbEntityRepository
    ) {
        this.planeDbEntityRepository = planeDbEntityRepository;
        this.favoriteDbEntityRepository = favoriteDbEntityRepository;
        this.userDbEntityRepository = userDbEntityRepository;
    }

    @Override
    public Collection<Plane> forUser(final String userId) {
        return planeDbEntityRepository
                .findAllByOwnerId(userId).stream()
                .map(PlaneDbEntity::toDomain).toList();
    }

    @Override
    public Optional<Plane> findById(final String id) {
        return planeDbEntityRepository.findById(Long.parseLong(id)).map(PlaneDbEntity::toDomain);
    }

    @Override
    public List<Plane> favoritesForUser(final String userId) {

        return this.favoriteDbEntityRepository.findAllByUserId(userId).stream()
                .map(favoriteDbEntity -> favoriteDbEntity.getPlane().toDomain())
                .toList();
    }

    @Override
    @Transactional
    public Plane create(final CreatePlaneCommand command) {

        this.userDbEntityRepository.findById(command.ownerId())
                .orElseGet(() -> this.userDbEntityRepository.save(UserDbEntity.fromId(command.ownerId())));
        return this.planeDbEntityRepository.save(PlaneDbEntity.fromCreateCommand(command)).toDomain();
    }

    @Override
    public Plane update(final UpdatePlaneCommand command, final String authenticatedUserId) {
        final Plane toUpdate = this.findById(command.id()).orElseThrow(NotFoundException::new);
        if (!toUpdate.owner().id().equals(authenticatedUserId)) {
            throw new NotFoundException();
        }
        return this.planeDbEntityRepository.save(PlaneDbEntity.fromDomain(command.updatePlane(toUpdate))).toDomain();
    }

    @Override
    @Transactional
    public void addFavoritePlaneForUser(final String planeId, final String userId) {
        this.userDbEntityRepository.findById(userId)
                .orElseGet(() -> this.userDbEntityRepository.save(UserDbEntity.fromId(userId)));
        this.favoriteDbEntityRepository.save(FavoriteDbEntity.fromDomain(planeId, userId));
    }

    @Override
    @Transactional
    public void removeFavoritePlaneForUser(final String planeId, final String userId) {
        this.favoriteDbEntityRepository.deleteByPlaneIdAndUserId(Long.parseLong(planeId), userId);
    }

    @Override
    public List<Plane> search(final SearchCommand searchCommand, final int limit) {
        return this.planeDbEntityRepository
                .search(searchCommand.registration(), searchCommand.name(), searchCommand.ownerNickname(), PageRequest.of(0, limit))
                .stream()
                .map(PlaneDbEntity::toDomain)
                .toList();
    }
}
