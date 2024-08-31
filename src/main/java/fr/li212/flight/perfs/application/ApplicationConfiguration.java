package fr.li212.flight.perfs.application;

import fr.li212.flight.perfs.domain.repository.PlaneRepository;
import fr.li212.flight.perfs.domain.repository.UserRepository;
import fr.li212.flight.perfs.infrastructure.sql.*;
import fr.li212.flight.perfs.web.model.WebException;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "fr.li212.flight.perfs.infrastructure.sql")
@EntityScan("fr.li212.flight.perfs.infrastructure.sql")
public class ApplicationConfiguration {

    @Bean
    UserRepository userRepository(final UserDbEntityRepository userDbEntityRepository) {
        return new UserJpaRepository(userDbEntityRepository);
    }

    @Bean
    PlaneRepository planeRepository(
            final PlaneDbEntityRepository planeDbEntityRepository,
            final FavoriteDbEntityRepository favoriteDbEntityRepository,
            final UserRepository userRepository) {
        return new PlaneJpaRepository(planeDbEntityRepository, favoriteDbEntityRepository, userRepository);
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

                Throwable error = getError(webRequest);

                if (error instanceof final WebException exception) {
                    errorAttributes.put("errorCode", exception.getErrorCode());
                }
                return errorAttributes;
            }
        };
    }
}
