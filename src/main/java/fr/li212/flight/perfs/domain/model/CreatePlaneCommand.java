package fr.li212.flight.perfs.domain.model;

public record CreatePlaneCommand(String name, String registration, String performances, String ownerId) {
}
