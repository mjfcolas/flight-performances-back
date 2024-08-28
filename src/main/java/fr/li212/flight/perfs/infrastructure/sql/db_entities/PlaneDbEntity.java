package fr.li212.flight.perfs.infrastructure.sql.db_entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.li212.flight.perfs.domain.model.CreatePlaneCommand;
import fr.li212.flight.perfs.domain.model.Plane;
import fr.li212.flight.perfs.domain.model.UnexpectedException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "plane")
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
public class PlaneDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "registration")
    private String registration;

    @Basic
    @Column(name = "performances")
    private String performances;

    @JoinColumn(name = "owner_id")
    @ManyToOne
    private UserDbEntity owner;

    public static PlaneDbEntity fromCreateCommand(final CreatePlaneCommand command) {
        return new PlaneDbEntity(
                null,
                command.name(),
                command.registration(),
                command.performances().toString(),
                UserDbEntity.fromId(command.ownerId())
        );
    }

    public static PlaneDbEntity fromDomain(final Plane plane) {
        return new PlaneDbEntity(
                Long.parseLong(plane.id()),
                plane.name(),
                plane.registration(),
                plane.performances().toString(),
                UserDbEntity.fromDomain(plane.owner())
        );
    }

    public Plane toDomain() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return new Plane(
                    this.id.toString(),
                    this.name,
                    this.registration,
                    mapper.readTree(this.performances),
                    this.owner.toDomain()
            );
        } catch (JsonProcessingException e) {
            throw new UnexpectedException(e);
        }
    }
}
