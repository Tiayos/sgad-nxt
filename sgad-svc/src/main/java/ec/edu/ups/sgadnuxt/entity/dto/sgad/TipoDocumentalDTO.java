package ec.edu.ups.sgadnuxt.entity.dto.sgad;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadTipoDocumental;

public record TipoDocumentalDTO(
        Long codigo,
        String descripcion

        ) {
    public static TipoDocumentalDTO toDTO(SgadTipoDocumental model) {
        return new TipoDocumentalDTO(
                model.getTidCodigo(),
                model.getTidDescripcion()
                );
    }
}
