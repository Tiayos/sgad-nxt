package ec.edu.ups.sgadnuxt.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class    SecurityConfig {

    @Autowired
    JwtAuthConverter authConverter;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/v1/sumilla/saveSumillaExt").permitAll()
//                        .requestMatchers("/api/v1/documentosExternos/**").permitAll()
//                        .requestMatchers("/api/v1/persona/findEmail").permitAll()
//                        .requestMatchers("/api/v1/persona/**").permitAll()
//                        .requestMatchers("/api/v1/email/sendCodigoUsuarioExterno").permitAll()
//                        .requestMatchers("/api/v1/bitacoraExternos/**").permitAll()
//                        .requestMatchers("/api/v1/sumilla/getSedeByEmail").authenticated()
//                        .requestMatchers("/api/v1/bitacora/**").authenticated()
//                        .requestMatchers("/api/v1/email/**").authenticated()
//                        .requestMatchers("/api/v1/sumilla/**").hasRole("RECEPCIONIST")
//                        .anyRequest().permitAll()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .jwtAuthenticationConverter(authConverter)
//                        )
//                );
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Deshabilitar CSRF si es necesario para peticiones públicas
                .authorizeHttpRequests(authorize -> authorize
                        // Rutas públicas sin autenticación
                        .requestMatchers("/api/v1/sumilla/saveSumillaExt").permitAll()
                        .requestMatchers("/api/v1/documentosExternos/**").permitAll()
                        .requestMatchers("/api/v1/persona/findEmail").permitAll()
                        .requestMatchers("/api/v1/persona/**").permitAll()
                        .requestMatchers("/api/v1/email/sendCodigoUsuarioExterno").permitAll()
                        .requestMatchers("/api/v1/bitacoraExternos").permitAll()
                        .requestMatchers("/api/v1/bitacoraExternos/**").permitAll()

                        // Rutas protegidas que requieren autenticación
                        .requestMatchers("/api/v1/sumilla/getSedeByEmail").permitAll()
                        .requestMatchers("/api/v1/bitacora/**").permitAll()
                        .requestMatchers("/api/v1/email/**").permitAll()
                        .requestMatchers("/api/v1/sumilla/**").permitAll()

                        // Cualquier otra ruta permitida sin autenticación
                        .anyRequest().permitAll()
                )
                // Configuración de OAuth2 con JWT para las rutas especificadas
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(authConverter)
                        )
                );
                // Definir las rutas que estarán bajo la protección del servidor de recursos OAuth2
//                .securityMatcher("/api/v1/sumilla/**", "/api/v1/bitacora/**", "/api/v1/email/**");  // Aplica la seguridad solo a estas rutas
        return http.build();
    }
}