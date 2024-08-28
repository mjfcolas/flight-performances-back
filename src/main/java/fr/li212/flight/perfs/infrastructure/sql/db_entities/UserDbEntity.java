package fr.li212.flight.perfs.infrastructure.sql.db_entities;

import fr.li212.flight.perfs.domain.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "application_user")
@AllArgsConstructor
@NoArgsConstructor
public class UserDbEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Basic
    @Column(name = "displayed_name")
    private String displayedName;

    public static UserDbEntity fromId(final String userId) {
        return new UserDbEntity(
                userId,
                null
        );
    }

    public static UserDbEntity fromDomain(final User user) {
        return new UserDbEntity(
                user.id(),
                user.displayedName()
        );
    }

    public User toDomain() {
        return new User(id, displayedName);
    }
}
