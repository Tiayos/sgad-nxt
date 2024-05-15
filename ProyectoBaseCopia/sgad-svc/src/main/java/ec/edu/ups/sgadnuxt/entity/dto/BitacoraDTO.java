package ec.edu.ups.sgadnuxt.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.model.BitacoraModel;

import java.time.LocalDate;

public record BitacoraDTO(
        Long codigo,
        @JsonProperty("nombres_remitente") String nombresRemitente,
        @JsonProperty("apellidos_remitente") String apellidosRemitente,
        PersonaDTO destinatario,
        String asunto,
        @JsonProperty("lugar_destino") String lugarDestino,
        PersonaDTO mensajero,
        @JsonProperty("numero_guia") String numeroGuia,
        @JsonProperty("registro_sgad") char registroSgad,
        @JsonProperty("numero_tramite") String numeroTramite,
        String observaciones,
        @JsonProperty("usr_emisor") PersonaDTO usrEmisor,
        @JsonProperty("usr_receptor") PersonaDTO usrReceptor,
        @JsonProperty("fecha_entrega") LocalDate fechaEntrega,
        @JsonProperty("hora_entrega") String horaEntrega

) {

    public static BitacoraDTO toDTO(BitacoraModel model) {
        return new BitacoraDTO(
                model.getCodigo(),
                model.getNombresRemitente(),
                model.getApellidosRemitente(),
                PersonaDTO.toDTO(model.getDestinatario()),
                model.getAsunto(),
                model.getLugarDestino(),
                PersonaDTO.toDTO(model.getMensajero()),
                model.getNumeroGuia(),
                model.getRegistroSgad(),
                model.getNumeroTramite(),
                model.getObservaciones(),
                PersonaDTO.toDTO(model.getUsrEmisor()),
                PersonaDTO.toDTO(model.getReceptor()),
                model.getFechaEntrega(),
                model.getHoraEntrega()
        );
    }
}
