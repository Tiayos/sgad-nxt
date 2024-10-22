package ec.edu.ups.sgadnuxt.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import java.util.Date;

public record PersonaDTO(
        Long codigo,
        @JsonProperty("numero_identificacion") String numeroIdentificacion,
        @JsonProperty("per_apellidos") String perApellidos,
        @JsonProperty("per_nombres") String perNombres,
        @JsonProperty("per_genero") String perGenero,
        @JsonProperty("fecha_nacimiento") Date fechaNacimiento,
        @JsonProperty("calle_principal") String callePrincipal,
        @JsonProperty("numero_casa") String numeroCasa

) {
    public static PersonaDTO toDTO(GthPersona model) {
        return new PersonaDTO(
                model.getPerCodigo(),
                model.getPerNroIdentificacion(),
                model.getPerApellidos(),
                model.getPerNombres(),
                model.getPerGenero(),
                model.getPerFechaNacimiento(),
                model.getPerCallePrincipal(),
                model.getPerNroCasa()
                );
    }
}
