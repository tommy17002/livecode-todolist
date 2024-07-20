package enigma.to_do_list.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final UserSecurity userSecurity;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disabling csrf
                .csrf().disable()
                // Setting up endpoints authorizations
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints -> Permit All for auth endpoints (register and login feature)
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // Category Controller
                        .requestMatchers(HttpMethod.POST, "/api/v1/cars").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/cars", "/api/v1/cars/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/cars/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/cars/*").hasRole("ADMIN")

                        // Task Controller
                        .requestMatchers(HttpMethod.POST, "/api/v1/brands").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/brands", "/api/v1/brands/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/brands/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/brands/*").hasRole("ADMIN")

                        // User endpoints
                        .requestMatchers("/api/v1/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/{id}").access(userAuthorizationManager())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/topup").access(userAuthorizationManager())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/*").hasRole("ADMIN")

                        // Any other request
                        .anyRequest().authenticated()
                )
                // Configure session management to be stateless
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Set up a custom authentication provider and add authentication filter
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> userAuthorizationManager() {
        // Create authorization manager, first check if the user role is an Admin or not
        AuthorizationManager<RequestAuthorizationContext> adminAuth = AuthorityAuthorizationManager.hasRole("ADMIN");
        return (authentication, context) -> {
            // If the user is an Admin, grant the authorization
            if (adminAuth.check(authentication, context).isGranted()) {
                return new AuthorizationDecision(true);
            }
            try {
                // Get the user ID from the request context and check if the authenticated user matches the ID
                int userId = Integer.parseInt(context.getVariables().get("id"));
                return new AuthorizationDecision(userSecurity.isUser(authentication.get(), userId));
            } catch (NumberFormatException e) {
                // If the user ID doesn't match, deny the authorization
                return new AuthorizationDecision(false);
            }
        };
    }
}
