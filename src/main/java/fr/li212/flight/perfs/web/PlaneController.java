package fr.li212.flight.perfs.web;

import fr.li212.flight.perfs.domain.model.CreatePlaneCommand;
import fr.li212.flight.perfs.domain.model.Plane;
import fr.li212.flight.perfs.domain.model.SearchCommand;
import fr.li212.flight.perfs.domain.model.UpdatePlaneCommand;
import fr.li212.flight.perfs.domain.repository.PlaneRepository;
import fr.li212.flight.perfs.security.AuthenticatedEntity;
import fr.li212.flight.perfs.web.model.CreatePlaneCommandDto;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/planes")
public class PlaneController {

    private final PlaneRepository planeRepository;

    public PlaneController(final PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @GetMapping
    public Collection<Plane> search(
            @RequestParam(value = "registration", required = false) final String registration,
            @RequestParam(value = "name", required = false) final String name,
            @RequestParam(value = "ownerName", required = false) final String ownerName) {

        final SearchCommand query = new SearchCommand(
                registration == null ? "" : registration,
                name == null ? "" : name,
                ownerName == null ? "" : ownerName
        );

        return this.planeRepository.search(query, 50);
    }

    @GetMapping("/{id}")
    public Plane get(@PathVariable("id") final String id) {
        return this.planeRepository.findById(id).orElseThrow();
    }

    @PutMapping(consumes = "application/json")
    public Plane create(@RequestBody final CreatePlaneCommandDto createPlaneCommand, AuthenticatedEntity authenticatedEntity) {
        final CreatePlaneCommand command = createPlaneCommand.toCreateCommandForUser(authenticatedEntity.getPrincipal());

        return this.planeRepository.create(command);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public Plane update(@PathVariable("id") final String planeId, @RequestBody final CreatePlaneCommandDto createPlaneCommand, AuthenticatedEntity authenticatedEntity) {
        final UpdatePlaneCommand command = createPlaneCommand.toUpdateCommandForId(planeId);
        return this.planeRepository.update(command, authenticatedEntity.getPrincipal());
    }
}
