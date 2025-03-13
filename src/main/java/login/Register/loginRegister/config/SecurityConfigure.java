package login.Register.loginRegister.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestClient;

@Configuration
@EnableWebSecurity
public class SecurityConfigure {

    private final JwtRequestFilter jwtRequestFilter;
    private final RestClient.Builder builder;

    public SecurityConfigure(JwtRequestFilter jwtRequestFilter, RestClient.Builder builder) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.builder = builder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
        //Retrieves the default authentication manager from AuthenticationConfiguration, which is automatically configured by Spring Security.
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // login/register open to all
                        .requestMatchers("/client/**").hasRole("Agent") //only agent
                        .requestMatchers("/images/upload").authenticated()
                        .requestMatchers("/admin/**").hasRole("Admin")
                        .requestMatchers("/agent/**").hasAnyRole("Agent", "Admin")
                        .requestMatchers("/client/**").hasAnyRole("Client", "Agent", "Admin")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter , UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
