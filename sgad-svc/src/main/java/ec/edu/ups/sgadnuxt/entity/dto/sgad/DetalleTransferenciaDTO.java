package ec.edu.ups.sgadnuxt.entity.dto.sgad;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDetalleTransferencia;
import java.time.LocalDate;

public record DetalleTransferenciaDTO(
        Long codigo,
        @JsonProperty("detalle_descripcion") String detDescripcion,
        @JsonProperty("detalle_fecha_final") LocalDate detFechaFinal,
        @JsonProperty("detalle_fecha_inicial") LocalDate detFechaInicio,
        @JsonProperty("detalle_numero_caja") Long detNumCaja,
        @JsonProperty("detalle_numero_orden") String detNumOrden,
        @JsonProperty("transferencia_documental") TransDocumentalDTO transDocumentalDTO,
        @JsonProperty("aud_adicionado") String audAdicionado

        ) {

    public static DetalleTransferenciaDTO toDTO(SgadDetalleTransferencia model) {
        return new DetalleTransferenciaDTO(
                model.getDetCodigo(),
                model.getDetDescripcion(),
                model.getDetFechaFinal(),
                model.getDetFechaInicio(),
                model.getDetNumCaja(),
                model.getDetNumOrden(),
                TransDocumentalDTO.toDTO(model.getTrdCodigo()),
                model.getAudAdicionado()
                );
    }
}
