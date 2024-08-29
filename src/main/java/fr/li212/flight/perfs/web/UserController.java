package fr.li212.flight.perfs.web;

import fr.li212.flight.perfs.domain.model.Plane;
import fr.li212.flight.perfs.domain.repository.PlaneRepository;
import fr.li212.flight.perfs.security.AuthenticatedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users/current")
public class UserController {

    private final PlaneRepository planeRepository;

    @Autowired
    public UserController(final PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
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
