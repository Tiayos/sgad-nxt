package ec.edu.ups.sgadnuxt.controller.mail;

import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDocumentosExternosDTO;
import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EventoBitacoraDTO;
import ec.edu.ups.sgadnuxt.service.EnviarCorreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
@RestController
@RequestMapping("/api/v1/email")
public class EnviarCorreo {

    @Autowired
    public EnviarCorreoService enviarCorreoService;

    @PostMapping("/enviarCorreo")
    public ResponseEntity<?> enviarCorreo(@RequestBody EventoBitacoraDTO eventoBitacoraDTO) {
        return enviarCorreoService.enviarCorreo(eventoBitacoraDTO);
    }

    @PostMapping("/sendCodigoUsuarioExterno")
    public ResponseEntity<?> enviarCodigoUsuarioExterno(@RequestBody BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO,
                                                        @RequestParam String numeroSumilla) {
        return enviarCorreoService.enviarCodigoUsuarioExterno(bitacoraDocumentosExternosDTO, numeroSumilla);
    }

    @PostMapping("/sendEmailReasignado")
    public ResponseEntity<?> enviarMailReasignado(@RequestBody BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO, @RequestParam Long sede) {
        return enviarCorreoService.enviarEmailBySedeReasignado(bitacoraDocumentosExternosDTO, sede);
    }

    @PostMapping("/sendEmailDestinatario")
    public ResponseEntity<?> enviarMailDestinatario(@RequestBody BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
        return enviarCorreoService.enviarEmailDestinatario(bitacoraDocumentosExternosDTO);
    }
    @PostMapping("/sendEmailDocumentacionFisica")
    public ResponseEntity<?> enviarMailRemitenteDocumentacionFisica(@RequestBody BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
        return enviarCorreoService.enviarSolicitudDocumentacionFisica(bitacoraDocumentosExternosDTO);
    }

    @PostMapping("/sendEmailRemitenteRespuesta")
    public ResponseEntity<?> enviarMailRemitenteRespuestaElectronica(@RequestBody BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
        return enviarCorreoService.enviarMailRemitenteRespuestaElectronica(bitacoraDocumentosExternosDTO);
    }


    @PostMapping("/sendEmailDocFisicaDestinatario")
    public ResponseEntity<?> enviarMailRemitenteRespuestaElectronica(@RequestBody BitacoraDTO bitacoraDTO) {
        return enviarCorreoService.enviarMailDestinatarioDocFisica(bitacoraDTO);
    }

    @PostMapping("/sendEmailDocReasignado")
    public ResponseEntity<?> enviarMailDocumentacionFisicaReasignada(@RequestBody EventoBitacoraDTO eventoBitacoraDTO) {
        return enviarCorreoService.enviarMailDestinatarioReasignado(eventoBitacoraDTO);
    }

}
