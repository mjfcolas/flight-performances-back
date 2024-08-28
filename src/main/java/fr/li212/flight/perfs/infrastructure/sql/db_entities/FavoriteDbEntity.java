package fr.li212.flight.perfs.infrastructure.sql.db_entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "favorite")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "application_user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "plane_id")
    private PlaneDbEntity plane;

    public static FavoriteDbEntity fromDomain(final String planeId, final String userId) {
        return new FavoriteDbEntity(
                0,
                userId,
                new PlaneDbEntity(Long.parseLong(planeId), null, null, null, null)
        );
    }
}
