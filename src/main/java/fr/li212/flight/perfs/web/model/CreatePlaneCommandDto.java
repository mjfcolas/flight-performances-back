package fr.li212.flight.perfs.web.model;

import com.fasterxml.jackson.databind.JsonNode;
import fr.li212.flight.perfs.domain.model.CreatePlaneCommand;
import fr.li212.flight.perfs.domain.model.UpdatePlaneCommand;

public record CreatePlaneCommandDto(String name, String registration, JsonNode performances) {

    public CreatePlaneCommand toCreateCommandForUser(final String userId) {
        return new CreatePlaneCommand(this.name(), this.registration(), this.performances(), userId);
    }

    public UpdatePlaneCommand toUpdateCommandForId(final String planeId) {
        return new UpdatePlaneCommand(planeId, this.name(), this.registration(), this.performances());
    }
}
