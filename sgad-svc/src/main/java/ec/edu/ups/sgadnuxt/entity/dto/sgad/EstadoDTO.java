package ec.edu.ups.sgadnuxt.entity.dto.sgad;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadEstado;

public record EstadoDTO(
        Long codigo,
        @JsonProperty("estado_descripcion") String estDescripcion

) {
    public static EstadoDTO toDTO(SgadEstado model) {
        return new EstadoDTO(
                model.getEstCodigo(),
                model.getEstDescripcion()
                );
    }
}
