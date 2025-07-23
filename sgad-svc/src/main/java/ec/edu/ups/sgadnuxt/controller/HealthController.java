package ec.edu.ups.sgadnuxt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/health")
    public String checkHealth() {
        try {
            jdbcTemplate.execute("SELECT 1 FROM DUAL");
            return "OK";
        } catch (Exception e) {
            throw new RuntimeException("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }
}