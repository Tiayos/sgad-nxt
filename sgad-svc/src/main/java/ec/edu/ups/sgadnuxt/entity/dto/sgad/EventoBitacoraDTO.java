package ec.edu.ups.sgadnuxt.entity.dto.sgad;
import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.dto.PersonaDTO;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadEventoBitacora;
import java.time.LocalDate;

public record EventoBitacoraDTO(
        Long codigo,
        LocalDate fecha,
        char vigencia,
        BitacoraDTO bitacora,
        EstadoDTO estado,
        String adicionado,
        @JsonProperty("per_codigo_responsable") PersonaDTO perCodigoResponsable,
        @JsonProperty("per_codigo_reasignado") PersonaDTO perCodigoReasignado


) {
    public static EventoBitacoraDTO toDTO(SgadEventoBitacora model) {
        return new EventoBitacoraDTO(
                model.getEbiCodigo(),
                model.getEbiFecha(),
                model.getEbiVigencia(),
//                BitacoraDTO.toDTO(model.getBitacoraModel()),
                model.getBitacoraModel() != null ? BitacoraDTO.toDTO(model.getBitacoraModel()) : null,
//                EstadoDTO.toDTO(model.getSgadEstado()),
                model.getSgadEstado() != null ? EstadoDTO.toDTO(model.getSgadEstado()) : null,
                model.getAudAdicionado() != null ? model.getAudAdicionado() : "",
//                model.getAudAdicionado() ,
                model.getPerCodigo() != null ? PersonaDTO.toDTO(model.getPerCodigo()) : null,
                model.getPerCodigoReasignado() != null ? PersonaDTO.toDTO(model.getPerCodigoReasignado()) : null

        );
    }
}
