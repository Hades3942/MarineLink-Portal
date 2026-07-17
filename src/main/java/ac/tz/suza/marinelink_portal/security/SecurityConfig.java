package ac.tz.suza.marinelink_portal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .cors()
            .and()
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // Allow CORS preflight
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Public endpoints
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // LISTINGS (FISHER)
                .requestMatchers(HttpMethod.GET, "/api/listings").hasAnyRole("FISHER", "BUYER", "REGULATOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/listings").hasRole("FISHER")
                .requestMatchers(HttpMethod.PUT, "/api/listings/**").hasRole("FISHER")
                .requestMatchers(HttpMethod.DELETE, "/api/listings/**").hasRole("FISHER")

                // TRANSACTIONS
                .requestMatchers(HttpMethod.POST, "/api/transactions/purchase").hasRole("BUYER")
                .requestMatchers(HttpMethod.GET, "/api/transactions/fisher/**").hasRole("FISHER")
                .requestMatchers(HttpMethod.GET, "/api/transactions/buyer/**").hasRole("BUYER")

                // COMPLIANCE REPORTS (REGULATOR)
                .requestMatchers("/api/reports/**").hasRole("REGULATOR")

                // ADMIN DASHBOARD
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // Analytics (ADMIN)
                .requestMatchers("/api/analytics/**").hasRole("ADMIN")

                // Reports viewer (REGULATOR + ADMIN)
                .requestMatchers("/api/reports/viewer/**").hasAnyRole("REGULATOR", "ADMIN")

                // Everything else requires authentication
                .anyRequest().authenticated()
            )

            .formLogin(login -> login.disable())
            .httpBasic(basic -> basic.disable())

            // Add JWT filter BEFORE UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
