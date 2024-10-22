package ec.edu.ups.sgadnuxt.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    JwtAuthConverter authConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                                .anyRequest().permitAll()
                        .requestMatchers("/api/v1/sumilla/saveSumillaExt").permitAll()
                        .requestMatchers("/api/v1/persona/findEmail").permitAll()
                        .requestMatchers("/api/v1/persona/**").permitAll()
                        .requestMatchers("/api/v1/bitacoraExternos/**").permitAll()
                        .requestMatchers("/api/v1/sumilla/getSedeByEmail").authenticated()
                        .requestMatchers("/api/v1/bitacora/**").authenticated()
                        .requestMatchers("/api/v1/email/**").authenticated()
                        .requestMatchers("/api/v1/sumilla/**").hasRole("RECEPCIONIST")
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(authConverter)
                        )
                );
        return http.build();
    }
}