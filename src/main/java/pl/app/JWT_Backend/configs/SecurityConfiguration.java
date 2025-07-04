package pl.app.JWT_Backend.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.app.JWT_Backend.security.JwtAuthEntryPoint;
import pl.app.JWT_Backend.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthEntryPoint authEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        (authz) -> authz
                                .requestMatchers("/api/v1/auth/**")
                                .permitAll()
                                .requestMatchers("api/v1/user/permission-assigned-to-group**")
                                .permitAll()
                                .requestMatchers("api/v1/user/get-all-users-with-permission-groups")
                                .permitAll()
                                .requestMatchers("api/v1/user/permission")
                                .hasAuthority("HELPDESK")
//                                .hasAuthority("")
                                // Allow all requests to paths matching /api/some-path-here/**
//                                .requestMatchers("/api/v1/permission")
//                                .hasAnyAuthority("ADMIN")
//
//                                 Allow all GET requests to any path
//                                .requestMatchers(HttpMethod.GET)
//                                .permitAll()
//
//                                 Allow PUT requests only if the user has the 'USER' authority
//                                .requestMatchers(HttpMethod.PUT)
//                                .authenticated()
//
//                                 Allow all POST requests to any path
//                                .requestMatchers(HttpMethod.POST)
//                                .permitAll()

                                // Require authentication for any other requests not matched above
                                .anyRequest()
                        // Change to your required permission name .hasAuthority("")
                )
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}