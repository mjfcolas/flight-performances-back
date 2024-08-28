package fr.li212.flight.perfs.web;

import fr.li212.flight.perfs.domain.model.CreatePlaneCommand;
import fr.li212.flight.perfs.domain.model.Plane;
import fr.li212.flight.perfs.domain.model.UpdatePlaneCommand;
import fr.li212.flight.perfs.domain.repository.PlaneRepository;
import fr.li212.flight.perfs.web.model.CreatePlaneCommandDto;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/planes")
public class PlaneController {

    private final PlaneRepository planeRepository;

    public PlaneController(final PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @GetMapping
    public Collection<Plane> search(@RequestParam("query") final String query) {
        return Collections.emptyList();
    }

    @GetMapping("/{id}")
    public Plane get(@PathVariable("id") final String id) {
        return this.planeRepository.findById(id).orElseThrow();
    }

    @PutMapping(consumes = "application/json")
    public Plane put(@RequestBody final CreatePlaneCommandDto createPlaneCommand) {
        final CreatePlaneCommand command = createPlaneCommand.toCreateCommandForUser(UserController.CURRENT_USER_ID);

        return this.planeRepository.create(command);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public Plane patch(@PathVariable("id") final String planeId, @RequestBody final CreatePlaneCommandDto createPlaneCommand) {
        final UpdatePlaneCommand command = createPlaneCommand.toUpdateCommandForId(planeId);
        return this.planeRepository.update(command);
    }
}
