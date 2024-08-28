package fr.li212.flight.perfs.web;

import fr.li212.flight.perfs.domain.model.Plane;
import fr.li212.flight.perfs.domain.repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users/current")
public class UserController {

    public final static String CURRENT_USER_ID = "1";

    private final PlaneRepository planeRepository;

    @Autowired
    public UserController(final PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @GetMapping("/created-planes")
    public Collection<Plane> getPlanes() {
        return planeRepository.forUser(CURRENT_USER_ID);
    }

    @GetMapping("/favorite-planes")
    public Collection<Plane> getFavoritePlanes() {
        return planeRepository.favoritesForUser(CURRENT_USER_ID);
    }

    @PutMapping("/favorite-planes/{planeId}")
    public void putFavoritePlane(@PathVariable("planeId") final String planeId) {
        planeRepository.addFavoritePlaneForUser(planeId, CURRENT_USER_ID);
    }

    @DeleteMapping("/favorite-planes/{planeId}")
    public void deleteFavoritePlane(@PathVariable("planeId") final String planeId) {
        planeRepository.removeFavoritePlaneForUser(planeId, CURRENT_USER_ID);
    }
}
