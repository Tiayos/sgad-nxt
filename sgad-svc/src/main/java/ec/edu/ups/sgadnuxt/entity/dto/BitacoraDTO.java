package ec.edu.ups.sgadnuxt.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ec.edu.ups.sgadnuxt.entity.model.BitacoraModel;
import jakarta.annotation.Nullable;
import java.time.LocalDate;

public record BitacoraDTO(
        Long codigo,
        @JsonProperty("nombres_remitente") String nombresRemitente,
        @JsonProperty("apellidos_remitente") String apellidosRemitente,
        @JsonProperty("receptor_documento") PersonaDTO receptorDocumento,
        PersonaDTO destinatario,
        String asunto,
        @JsonProperty("lugar_destino") String lugarDestino,
        PersonaDTO mensajero,
        @JsonProperty("numero_guia") String numeroGuia,
        String observaciones,
        @Nullable
        @JsonProperty("usr_emisor") PersonaDTO usrEmisor,
        @Nullable
        @JsonProperty("usr_receptor") PersonaDTO usrReceptor,
        @JsonProperty("fecha_entrega") LocalDate fechaEntrega,
        @JsonProperty("hora_entrega") String horaEntrega,
        @JsonProperty("fecha_recepcion") LocalDate fechaRecepcion,
        @JsonProperty("hora_recepcion") String horaRecepcion,
        SumillaDTO sumilla,
        @JsonProperty("doc_archivo") byte[] docArchivo,
        @JsonProperty("nombre_archivo") String nombreArchivo,
        @JsonProperty("estado_transferencia") char estadoTransferencia,
        String adicionado,
        @JsonProperty("mensajero_externo") String mensajeroExterno,
        @JsonProperty("documento_reasignado") boolean documentoReasignado,
        @JsonProperty("secuencial_sede") Long secuencialSede,
        @JsonProperty("secuencial_documento") Long secuencialDocumento,
        @JsonProperty("codigo_recepcion_reasignado") Long codigoRecepcionReasignado


) {

    public static BitacoraDTO toDTO(BitacoraModel model) {
        return new BitacoraDTO(
                model.getCodigo(),
                model.getNombresRemitente(),
                model.getApellidosRemitente(),
                model.getUsrReceptorDocumento() != null ? PersonaDTO.toDTO(model.getUsrReceptorDocumento()) : null,
                model.getDestinatario() != null ? PersonaDTO.toDTO(model.getDestinatario()) : null,
                model.getAsunto(),
                model.getLugarDestino(),
                model.getMensajero() != null ? PersonaDTO.toDTO(model.getMensajero()) : null,
                model.getNumeroGuia(),
                model.getObservaciones(),
                model.getUsrEmisor() != null ? PersonaDTO.toDTO(model.getUsrEmisor()) : null,
                model.getReceptor() != null ? PersonaDTO.toDTO(model.getReceptor()) : null,
                model.getFechaEntrega(),
                model.getHoraEntrega(),
                model.getFechaRecepcion(),
                model.getHoraRecepcion(),
                SumillaDTO.toDTO(model.getSumilla()),
                model.getDocArchivo(),
                model.getNombreArchivo(),
                model.getEstadoTransferencia(),
                model.getAdicionado(),
                model.getMensajeroExterno(),
                false,
                model.getSecuencialSede(),
                model.getSecuencialDocumento(),
                model.getCodigoRecepcionReasignado()
        );
    }
}
