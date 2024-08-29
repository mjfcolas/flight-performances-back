package fr.li212.flight.perfs.application;

import fr.li212.flight.perfs.security.AuthenticatedEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    CorsConfigurationSource corsConfigurationSource(@Value("${allowed-origins}") final String[] allowedOrigins) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/planes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/planes/*").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oAuth2 -> oAuth2.jwt(jwt -> jwt.jwtAuthenticationConverter(this::getUserId)));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(final @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuer) {
        return NimbusJwtDecoder.withIssuerLocation(issuer).build();
    }

    private AuthenticatedEntity getUserId(final Jwt jwt) {
        String userId = jwt.getClaimAsString("sub");
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
        final AuthenticatedEntity result = new AuthenticatedEntity(userId, authorities);
        result.setAuthenticated(true);
        return result;
    }
}
