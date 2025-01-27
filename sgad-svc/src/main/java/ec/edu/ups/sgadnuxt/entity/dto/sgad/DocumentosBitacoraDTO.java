package ec.edu.ups.sgadnuxt.entity.dto.sgad;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumentoBitacora;

public record DocumentosBitacoraDTO(
        Long codigo,
        @JsonProperty("doc_archivo") byte[] docArchivo,
        BitacoraDTO bitacora,
        String adicionado,
        @JsonProperty("doc_nombre_archivo") String docNombreArchivo,
        @JsonProperty("estado_tramite") String estadoTramite


        ) {
    public static DocumentosBitacoraDTO toDTO(SgadDocumentoBitacora model) {
        return new DocumentosBitacoraDTO(
                model.getDocCodigo(),
                model.getDocArchivo(),
                model.getBitacoraModel() != null ? BitacoraDTO.toDTO(model.getBitacoraModel()) : null,
                model.getAudAdicionado(),
                model.getDocNombreArchivo(),
                model.getEstadoTramite()
                );
    }
}
