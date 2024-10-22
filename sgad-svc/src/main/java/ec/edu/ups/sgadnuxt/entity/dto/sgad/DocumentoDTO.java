package ec.edu.ups.sgadnuxt.entity.dto.sgad;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumento;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DocumentoDTO(
        Long codigo,
        @JsonProperty("documento_asunto") String docAsunto,
        @JsonProperty("documento_paginas") Long docPaginas,
        @JsonProperty("documento_paginas") DetalleTransferenciaDTO detalleTransferenciaDTO,
        @JsonProperty("documento_num_orden") Long docNumOrden,
        @JsonProperty("aud_adicionado") String audAdicionado,
        @JsonProperty("documento_fecha")LocalDate docFechaDocu


) {
    public static DocumentoDTO toDTO(SgadDocumento model) {
        return new DocumentoDTO(
                model.getDocCodigo(),
                model.getDocAsunto(),
                model.getDocPaginas(),
                DetalleTransferenciaDTO.toDTO(model.getDetCodigo()),
                model.getDocNumOrdenLn(),
                model.getAudAdicionado(),
                model.getDocFechaDocu()
                );
    }
}
