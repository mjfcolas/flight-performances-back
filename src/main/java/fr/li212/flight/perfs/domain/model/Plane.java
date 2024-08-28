package fr.li212.flight.perfs.domain.model;

import com.fasterxml.jackson.databind.JsonNode;

public record Plane(String id, String name, String registration, JsonNode performances, User owner) {
}
