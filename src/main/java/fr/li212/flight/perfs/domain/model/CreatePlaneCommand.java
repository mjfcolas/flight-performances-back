package fr.li212.flight.perfs.domain.model;

import com.fasterxml.jackson.databind.JsonNode;

public record CreatePlaneCommand(String name, String registration, JsonNode performances, String ownerId) {
}
