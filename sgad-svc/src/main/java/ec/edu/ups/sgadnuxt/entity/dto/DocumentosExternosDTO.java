package ec.edu.ups.sgadnuxt.entity.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumentosExternosModal;
public record DocumentosExternosDTO(
        Long codigo,
        @JsonProperty("doe_archivo") byte[] doeArchivo,
        @JsonProperty("doe_nombre_archivo") String doeNombreArchivo,
        @JsonProperty("documentos_externos") BitacoraDocumentosExternosDTO documentosExternos,
        String adicionado,
        @JsonProperty("estado_documento_electronico") char estadoDocumentoElectronico
        ) {
    public static DocumentosExternosDTO toDTO(SgadDocumentosExternosModal model) {
        return new DocumentosExternosDTO(
                model.getDoeCodigo(),
                model.getDoeArchivo(),
                model.getDoeNombreArchivo(),
                model.getBitacoraDocumentosExternosModel() != null ? BitacoraDocumentosExternosDTO.toDTO(model.getBitacoraDocumentosExternosModel()) : null,
                model.getAudAdicionado(),
                model.getEstadoDocumentoElectronico()
        );
    }
}
