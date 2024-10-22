package ec.edu.ups.sgadnuxt.entity.dto.sgad;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadTransDocumentales;
import java.time.LocalDate;

public record TransDocumentalDTO(
        Long codigo,
        @JsonProperty("codigo_ambito_receptor") Long ambRecCodigo,
        @JsonProperty("codigo_ambito_responsable") Long ambResCodigo,
        @JsonProperty("codigo_cargo_receptor") Long carRecCodigo,
        @JsonProperty("codigo_cargo_responsable") Long carResCodigo,
        @JsonProperty("fecha_emision") LocalDate fechaEmision,
        @JsonProperty("fecha_recepcion") LocalDate fechaRecepcion,
        @JsonProperty("numero_registro") Long numRegistro,
        @JsonProperty("numero_unidad") Long numUnidad,
        @JsonProperty("per_codigo_receptor") Long perRecCodigo,
        @JsonProperty("per_codigo_responsable") Long perResCodigo,
        @JsonProperty("tipo_transferencia") String tipoTransferencia,
        @JsonProperty("codigo_empresa_receptor") Long empRecCodigo,
        @JsonProperty("codigo_empresa") Long empCodigo,
        String estado,
        @JsonProperty("leer_responsable") String leerResponsable,
        @JsonProperty("leer_receptor") String leerReceptor,
        @JsonProperty("tipo_archivo") String tipoArchivo,
        @JsonProperty("aud_adicionado") String audAdicionado


) {

    public static TransDocumentalDTO toDTO(SgadTransDocumentales model) {
        return new TransDocumentalDTO(
                model.getTrdCodigo(),
                model.getAmbRecCodigo(),
                model.getAmbResCodigo(),
                model.getCarRecCodigo(),
                model.getCarResCodigo(),
                model.getTrdFechaEmision(),
                model.getTrdFechaRecepcion(),
                model.getTrdNumRegistro(),
                model.getTrdNumUnidad(),
                model.getPerRecCodigo(),
                model.getPerResCodigo(),
                model.getTrdTipoTransfe(),
                model.getEmpRecCodigo(),
                model.getEmpCodigo(),
                model.getTrdEstado(),
                model.getTrdLeerRes(),
                model.getTrdLeerRec(),
                model.getTrdTipoArchivo(),
                model.getAudAdicionado()
        );
    }
}
