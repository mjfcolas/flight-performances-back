package fr.li212.flight.perfs.application;

import fr.li212.flight.perfs.domain.repository.PlaneRepository;
import fr.li212.flight.perfs.infrastructure.sql.FavoriteDbEntityRepository;
import fr.li212.flight.perfs.infrastructure.sql.PlaneDbEntityRepository;
import fr.li212.flight.perfs.infrastructure.sql.PlaneJpaRepository;
import fr.li212.flight.perfs.infrastructure.sql.UserDbEntityRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "fr.li212.flight.perfs.infrastructure.sql")
@EntityScan("fr.li212.flight.perfs.infrastructure.sql")
public class ApplicationConfiguration {

    @Bean
    PlaneRepository planeRepository(
            final PlaneDbEntityRepository planeDbEntityRepository,
            final FavoriteDbEntityRepository favoriteDbEntityRepository,
            final UserDbEntityRepository userDbEntityRepository) {
        return new PlaneJpaRepository(planeDbEntityRepository, favoriteDbEntityRepository, userDbEntityRepository);
    }

}
