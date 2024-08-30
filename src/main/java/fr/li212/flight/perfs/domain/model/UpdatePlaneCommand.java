package fr.li212.flight.perfs.domain.model;

public record UpdatePlaneCommand(String id, String name, String registration, String performances) {

    public Plane updatePlane(final Plane toUpdate) {
        return new Plane(toUpdate.id(), this.name(), this.registration(), this.performances(), toUpdate.owner());
    }
}
