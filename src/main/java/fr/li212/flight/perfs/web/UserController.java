package fr.li212.flight.perfs.web;

import fr.li212.flight.perfs.domain.model.Plane;
import fr.li212.flight.perfs.domain.model.User;
import fr.li212.flight.perfs.domain.model.exception.UserAlreadyExistsException;
import fr.li212.flight.perfs.domain.repository.PlaneRepository;
import fr.li212.flight.perfs.domain.repository.UserRepository;
import fr.li212.flight.perfs.security.AuthenticatedEntity;
import fr.li212.flight.perfs.web.model.NotFoundWebException;
import fr.li212.flight.perfs.web.model.UserAlreadyExistsWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users/current")
public class UserController {

    private final PlaneRepository planeRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserController(
            final PlaneRepository planeRepository,
            final UserRepository userRepository
    ) {
        this.planeRepository = planeRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public User getCurrentUser(final AuthenticatedEntity authenticatedEntity) {
        return userRepository.findById(authenticatedEntity.getPrincipal()).orElseThrow(NotFoundWebException::new);
    }

    @PutMapping
    public User updateCurrentUser(@RequestBody final User user, final AuthenticatedEntity authenticatedEntity) {
        try {
            return userRepository.save(user.withId(authenticatedEntity.getPrincipal()));
        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsWebException();
        }
    }

    @GetMapping("/created-planes")
    public Collection<Plane> getPlanes(final AuthenticatedEntity authenticatedEntity) {
        return planeRepository.forUser(authenticatedEntity.getPrincipal());
    }

    @GetMapping("/favorite-planes")
    public Collection<Plane> getFavoritePlanes(final AuthenticatedEntity authenticatedEntity) {
        return planeRepository.favoritesForUser(authenticatedEntity.getPrincipal());
    }

    @PutMapping("/favorite-planes/{planeId}")
    public void putFavoritePlane(@PathVariable("planeId") final String planeId, final AuthenticatedEntity authenticatedEntity) {
        planeRepository.addFavoritePlaneForUser(planeId, authenticatedEntity.getPrincipal());
    }

    @DeleteMapping("/favorite-planes/{planeId}")
    public void deleteFavoritePlane(@PathVariable("planeId") final String planeId, final AuthenticatedEntity authenticatedEntity) {
        planeRepository.removeFavoritePlaneForUser(planeId, authenticatedEntity.getPrincipal());
    }
}
