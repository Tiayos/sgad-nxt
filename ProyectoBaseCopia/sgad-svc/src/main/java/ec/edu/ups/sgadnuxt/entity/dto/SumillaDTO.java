package ec.edu.ups.sgadnuxt.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.model.SumillaModel;
import java.time.LocalDate;

public record SumillaDTO(
        Long codigo,
        @JsonProperty("fecha_sumilla") LocalDate fechaSumilla,
        @JsonProperty("hora_sumilla") String horaSumilla,
        @JsonProperty("nombre_responsable") String nombreResponsable,
        @JsonProperty("apellido_responsable") String apellidoResponsable,
        @JsonProperty("numero_hojas") Integer numeroHojas,
        PersonaDTO mensajero

        ) {

    public static SumillaDTO toDTO(SumillaModel model) {
        return new SumillaDTO(
                model.getCodigo(),
                model.getFechaSumilla(),
                model.getHoraSumilla(),
                model.getNombreResponsableSumilla(),
                model.getApellidoResponsableSumilla(),
                model.getNumeroHojas(),
                PersonaDTO.toDTO(model.getMensajero())
        );
    }
}
