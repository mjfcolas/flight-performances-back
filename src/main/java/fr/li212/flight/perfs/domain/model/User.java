package fr.li212.flight.perfs.domain.model;

import lombok.With;

@With
public record User(String id, String nickname) {

    public static User fromId(final String id) {
        return new User(id, null);
    }
}
