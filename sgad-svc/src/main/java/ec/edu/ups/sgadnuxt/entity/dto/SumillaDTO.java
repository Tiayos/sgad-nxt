package ec.edu.ups.sgadnuxt.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.model.SumillaModel;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;

import java.time.LocalDate;

public record SumillaDTO(
        Long codigo,
        @JsonProperty("fecha_sumilla") LocalDate fechaSumilla,
        @JsonProperty("hora_sumilla") String horaSumilla,
        PersonaDTO responsable,
        @JsonProperty("numero_hojas") Integer numeroHojas,
        @JsonProperty("numero_sumilla") String numeroSumilla,
        @JsonProperty("numero_tramite") Long numeroTramite,
        @JsonProperty("sum_sede") Long sumSede

        ) {

    public static SumillaDTO toDTO(SumillaModel model) {
        return new SumillaDTO(
                model.getCodigo(),
                model.getFechaSumilla(),
                model.getHoraSumilla(),
                model.getResponsable() != null ? PersonaDTO.toDTO(model.getResponsable()) : null,
                model.getNumeroHojas(),
                model.getNumeroSumilla(),
                model.getNumeroTramite(),
                model.getSumSede()
        );
    }
}
