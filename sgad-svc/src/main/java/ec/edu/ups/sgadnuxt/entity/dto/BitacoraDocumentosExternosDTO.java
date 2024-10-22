package ec.edu.ups.sgadnuxt.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.model.BitacoraDocumentosExternosModel;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;

public record BitacoraDocumentosExternosDTO(
        Long codigo,
        @JsonProperty("nombres_remitente") String nombresRemitente,
        @JsonProperty("apellidos_remitente") String apellidosRemitente,
        String asunto,
        @JsonProperty("correo_remitente") String correoRemitente,
        @JsonProperty("correo_destinatario") String correoDestinatario,
        SumillaDTO sumilla,
        String adicionado,
        @JsonProperty("nombre_completo_destinatario") String nombreCompletoDestinatario,
        Long estado,
        PersonaDTO destinatario,
        PersonaDTO responsable,
        String observacion,
        @JsonProperty("nombre_organizacion") String nombreOrganizacion,
        @JsonProperty("codigo_consulta") String codigoConsulta

) {

    public static BitacoraDocumentosExternosDTO toDTO(BitacoraDocumentosExternosModel model) {
        return new BitacoraDocumentosExternosDTO(
                model.getCodigo(),
                model.getNombresRemitente(),
                model.getApellidosRemitente(),
                model.getAsunto(),
                model.getCorreoRemitente(),
                model.getCorreoDestinatario(),
                model.getSumilla() != null ? SumillaDTO.toDTO(model.getSumilla()) : null,
                model.getAdicionado(),
                model.getNombreCompletoDestinatario(),
                model.getEstado(),
                model.getDestinatario() != null ? PersonaDTO.toDTO(model.getDestinatario()) : null,
                model.getResponsable() != null ? PersonaDTO.toDTO(model.getResponsable()) : null,
                model.getObservacion(),
                model.getNombreOrganizacion(),
                model.getCodigoConsulta()
        );
    }
}
