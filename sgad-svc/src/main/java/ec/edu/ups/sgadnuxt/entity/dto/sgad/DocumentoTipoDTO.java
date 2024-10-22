package ec.edu.ups.sgadnuxt.entity.dto.sgad;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumentoTipo;

public record DocumentoTipoDTO(
        Long codigo,
        @JsonProperty("doc_codigo") DocumentoDTO documentoDTO,
        @JsonProperty("tid_codigo") TipoDocumentalDTO tipoDocumentalDTO,
        @JsonProperty("aud_adicionado") String audAdicionado


) {
    public static DocumentoTipoDTO toDTO(SgadDocumentoTipo model) {
        return new DocumentoTipoDTO(
                model.getDotCodigo(),
                DocumentoDTO.toDTO(model.getDocCodigo()),
                TipoDocumentalDTO.toDTO(model.getTidCodigo()),
                model.getAudAdicionado()
        );
    }
}
