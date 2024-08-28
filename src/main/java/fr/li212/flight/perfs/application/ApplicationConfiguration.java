package fr.li212.flight.perfs.application;

import fr.li212.flight.perfs.domain.repository.PlaneRepository;
import fr.li212.flight.perfs.infrastructure.sql.FavoriteDbEntityRepository;
import fr.li212.flight.perfs.infrastructure.sql.PlaneDbEntityRepository;
import fr.li212.flight.perfs.infrastructure.sql.PlaneJpaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaRepositories(basePackages = "fr.li212.flight.perfs.infrastructure.sql")
@EntityScan("fr.li212.flight.perfs.infrastructure.sql")
public class ApplicationConfiguration {

    @Bean
    PlaneRepository planeRepository(
            final PlaneDbEntityRepository planeDbEntityRepository,
            final FavoriteDbEntityRepository favoriteDbEntityRepository) {
        return new PlaneJpaRepository(planeDbEntityRepository, favoriteDbEntityRepository);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(@Value("${allowed-origins}") final String[] allowedOrigins) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins(allowedOrigins);
            }
        };
    }
}
