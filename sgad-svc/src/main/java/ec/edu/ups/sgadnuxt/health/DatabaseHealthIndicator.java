package ec.edu.ups.sgadnuxt.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Health health() {
        try {
            jdbcTemplate.execute("SELECT 1 FROM DUAL"); // Consulta simple para verificar la conexión
            return Health.up().build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("Error", "No se pudo conectar a la base de datos: " + e.getMessage())
                    .build();
        }
    }
}