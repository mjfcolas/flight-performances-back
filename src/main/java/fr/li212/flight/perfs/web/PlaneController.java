package fr.li212.flight.perfs.web;

import fr.li212.flight.perfs.domain.model.CreatePlaneCommand;
import fr.li212.flight.perfs.domain.model.Plane;
import fr.li212.flight.perfs.domain.model.SearchCommand;
import fr.li212.flight.perfs.domain.model.UpdatePlaneCommand;
import fr.li212.flight.perfs.domain.model.exception.NotFoundException;
import fr.li212.flight.perfs.domain.repository.PlaneRepository;
import fr.li212.flight.perfs.security.AuthenticatedEntity;
import fr.li212.flight.perfs.web.model.CreatePlaneCommandDto;
import fr.li212.flight.perfs.web.model.NotFoundWebException;
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
        return this.planeRepository.find(id).orElseThrow(NotFoundWebException::new);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final String id, AuthenticatedEntity authenticatedEntity) {
        try {
            this.planeRepository.delete(id, authenticatedEntity.getPrincipal());
        } catch (NotFoundException e) {
            throw new NotFoundWebException();
        }
    }

    @PutMapping(consumes = "application/json")
    public Plane create(@RequestBody final CreatePlaneCommandDto createPlaneCommand, AuthenticatedEntity authenticatedEntity) {
        final CreatePlaneCommand command = createPlaneCommand.toCreateCommandForUser(authenticatedEntity.getPrincipal());

        return this.planeRepository.create(command);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public Plane update(@PathVariable("id") final String planeId, @RequestBody final CreatePlaneCommandDto createPlaneCommand, AuthenticatedEntity authenticatedEntity) {
        try {
            final UpdatePlaneCommand command = createPlaneCommand.toUpdateCommandForId(planeId);
            return this.planeRepository.update(command, authenticatedEntity.getPrincipal());
        } catch (NotFoundException e) {
            throw new NotFoundWebException();
        }
    }
}
