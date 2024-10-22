package ec.edu.ups.sgadnuxt.service;


import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDocumentosExternosDTO;
import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EventoBitacoraDTO;
import org.springframework.http.ResponseEntity;

public interface EnviarCorreoService {
     public ResponseEntity<?> enviarCorreo(EventoBitacoraDTO eventoBitacoraDTO);
     public ResponseEntity<?> enviarCodigoUsuarioExterno(BitacoraDocumentosExternosDTO BitacoraDocumentosExternosDTO, String numeroSumilla);
     public ResponseEntity<?> enviarEmailBySedeReasignado(BitacoraDocumentosExternosDTO BitacoraDocumentosExternosDTO, Long sede);
     public ResponseEntity<?> enviarEmailDestinatario(BitacoraDocumentosExternosDTO BitacoraDocumentosExternosDTO);
     public ResponseEntity<?> enviarSolicitudDocumentacionFisica(BitacoraDocumentosExternosDTO BitacoraDocumentosExternosDTO);
     public ResponseEntity<?> enviarMailRemitenteRespuestaElectronica(BitacoraDocumentosExternosDTO BitacoraDocumentosExternosDTO);
     public ResponseEntity<?> enviarMailDestinatarioDocFisica(BitacoraDTO bitacoraDTO);
     public ResponseEntity<?> enviarMailDestinatarioReasignado(EventoBitacoraDTO eventoBitacoraDTO);

}
