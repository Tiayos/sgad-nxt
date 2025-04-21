package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDocumentosExternosDTO;
import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EventoBitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.model.SumillaModel;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthMail;
import ec.edu.ups.sgadnuxt.projection.GTHPersonaProjection;
import ec.edu.ups.sgadnuxt.repository.IBitacoraDao;
import ec.edu.ups.sgadnuxt.repository.IBitacoraDocumentosExternosDao;
import ec.edu.ups.sgadnuxt.repository.IGthMailDao;
import ec.edu.ups.sgadnuxt.repository.IPersonaDao;
import ec.edu.ups.sgadnuxt.service.EnviarCorreoService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnviarCorreoImpl implements EnviarCorreoService {

	private static final Logger log = LoggerFactory.getLogger(EnviarCorreoImpl.class);

	String title1 = "UNIVERSIDAD POLITÉCNICA SALESIANA";
	String title2 = "SISTEMA DE GESTIÓN DOCUMENTAL";
	String messageContent1 = "Estimado/a ";
	String asuntoEmail = "Sistema de Gestión documental";
	String numeroSolicitud = "Número de solicitud";
	String projectStage = "DESARROLLO";

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private IPersonaDao iPersonaDao;
	@Autowired
	private IBitacoraDocumentosExternosDao iBitacoraDocumentosExternosDao;
	@Autowired
	private IBitacoraDao iBitacoraDao;
	@Autowired
	private IGthMailDao iGthMailDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<?> enviarCorreo(EventoBitacoraDTO eventoBitacoraDTO) {
		String mesaggeAtt = "Atentamente,";
		try {
			GTHPersonaProjection gthPersonaProjection;
			if (eventoBitacoraDTO.bitacora().codigoRecepcionReasignado() != null) {
				gthPersonaProjection = iPersonaDao.getGthPersonaAmbitoByPerCodigo(eventoBitacoraDTO.bitacora().codigoRecepcionReasignado());
			} else {
				gthPersonaProjection = iPersonaDao.getGthPersonaAmbitoByPerCodigo(eventoBitacoraDTO.bitacora().receptorDocumento().codigo());
			}

			String emailResponsable = gthPersonaProjection.getMAIL();
			String nombreResponsable = "";
			if (eventoBitacoraDTO.perCodigoReasignado() != null) {
				nombreResponsable = eventoBitacoraDTO.perCodigoReasignado().perNombres() + " " + eventoBitacoraDTO.perCodigoReasignado().perApellidos();
			} else {
				nombreResponsable = eventoBitacoraDTO.bitacora().destinatario().perNombres() + " " + eventoBitacoraDTO.bitacora().destinatario().perApellidos();
			}

			String nombreDestinatario = "";
			if (eventoBitacoraDTO.bitacora().codigoRecepcionReasignado() != null) {
				nombreDestinatario = gthPersonaProjection.getPERNOMBRES() + " " + gthPersonaProjection.getPERAPELLIDOS();
			} else {
				nombreDestinatario = eventoBitacoraDTO.bitacora().receptorDocumento().perNombres() + " " + eventoBitacoraDTO.bitacora().receptorDocumento().perApellidos();
			}

			String numeroTramite = eventoBitacoraDTO.bitacora().sumilla().numeroSumilla();
			String messageContent3 = "Se solicita su colaboración, proporcionado la documentación física del trámite  N° " + numeroTramite + " ya que, se requiere para su verificación y cumplir con el trámite.";

			if (numeroSolicitud != null) {
				String htmlContent3 =
						"</br>" + mesaggeAtt + "<br>" + nombreResponsable +
								"</td>" +
								"</tr>" +
								"<tr>" +
								"<td style=\"text-align:left;font-size:20px;color:#FFFFFF;background-color:#f2c351;border-radius: 0px 0px 4px 4px;\">" +
								"<div style=\"height:5px;\" />" +
								"</td>" +
								"</tr>" +
								"</tbody>" +
								"</table>" +
								"</td>" +
								"</tr>" +
								"<tr>" +
								"<td style=\"text-align:center;font-size:10px;color: #555;\">" +
								"</td>" +
								"</tr>" +
								"</tbody>" +
								"</table>";

				String htmlContent = prepararFormatoHtml(nombreDestinatario, "", messageContent3, "", htmlContent3, "");
				sendEmailUsuario(emailResponsable, htmlContent);
				return new ResponseEntity<>("Email enviado", HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("Error al enviar correo para evento de bitácora: {}", eventoBitacoraDTO, e);
			return new ResponseEntity<>("No se pudo enviar el email al estudiante", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Email no enviado", HttpStatus.OK);
	}

	public String prepararFormatoHtml(String nombreDestinatario, String messageContent2, String messageContent3, String messageContent4, String htmlContent3, String htmlContent4) {
		String htmlContent =
				"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f2f2f2;width:100%;\">" +
						"<tbody>" +
						"<tr>" +
						"<td style=\"padding: 20px 20px 5px 20px;\">" +
						"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;border:1px solid #DDD;border-radius: 5px;box-shadow: 0px 0px 3px 0px #CCC;border-collapse:separate;background-color: #FFF;\">" +
						"<tbody>" +
						"<tr>" +
						"<td style=\"text-align:left;font-size:20px;color:#FFFFFF;background-color:#0973b5;border-radius: 0px 0px 4px 4px;\">" +
						"<div style=\"height:5px;\" />" +
						"</td>" +
						"</tr>" +
						"<tr>" +
						"<td>" +
						"<div style=\"padding:10px;text-align:center;font-weight: 600;color:#2E74B5;\">" + title1 +
						"<br> <div style=\"text-align:center;font-weight: 600;color:#2E74B5;\">" + title2 +
						"</div></div>" +
						"</td>" +
						"</tr>" +
						"<tr>" +
						"<td style=\"padding:10px;\">" + messageContent1 + "<b>" + nombreDestinatario + "</b>" +
						"</br></br>" +
						"<p>" + messageContent2 + messageContent3 + "</p>" +
						"</td>" +
						"</tr>" +
						"<tr>" +
						"<td style=\"padding:10px;\">" + htmlContent3 +
						"</td>" +
						"</tr>" + messageContent4 +
						"<tr>" +
						"<td style=\"padding:10px;\">" + htmlContent4 +
						"</td>" +
						"</tr>" +
						"</tbody>" +
						"</table>" +
						"</td>" +
						"</tr>" +
						"<tr>" +
						"<td style=\"text-align:center;font-size:10px;color: #555;\">" +
						"</td>" +
						"</tr>" +
						"</tbody>" +
						"</table>";
		return htmlContent;
	}

	public void sendEmailUsuario(String destinatario, String htmlContent) throws MessagingException {
		String destinatarioEmail = null;
		String copiaOcultaDestinatarioEmail = "santiagobermeob@@gmail.com";
		if (projectStage.equals("DESARROLLO")) {
			destinatarioEmail = destinatario;
			copiaOcultaDestinatarioEmail = "santiagobermeob@@gmail.com";
		} else if (projectStage.equals("PRODUCCION")) {
			destinatarioEmail = destinatario;
			copiaOcultaDestinatarioEmail = "sbermeob@ups.edu.ec";
		}

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setTo(destinatarioEmail);
			helper.setFrom("notificaciones@ups.edu.ec");
			helper.setSubject(asuntoEmail);
			helper.setText(htmlContent, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			log.error("Error al enviar email al destinatario: {}", destinatario, e);
			throw new RuntimeException("Error en el envío de email: " + destinatario, e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<?> enviarCodigoUsuarioExterno(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO, String numeroSumilla) {
		String mesaggeAtt = "Atentamente,";
		try {
			BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO1 = BitacoraDocumentosExternosDTO.toDTO(iBitacoraDocumentosExternosDao.findById(bitacoraDocumentosExternosDTO.codigo()).get());
			String htmlContextBySede = htmlMailBySede(bitacoraDocumentosExternosDTO1);

			if (numeroSolicitud != null) {
				String htmlContent3 =
						"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f2f2f2;width:100%;\">" +
								"<tbody>" +
								"<tr>" +
								"<td style=\"padding: 20px 20px 5px 20px;\">" +
								"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;border:1px solid #DDD;border-radius: 5px;box-shadow: 0px 0px 3px 0px #CCC;border-collapse:separate;background-color: #FFF;\">" +
								"<tbody>" +
								"<tr>" +
								"<td style=\"text-align:center;font-size:20px;color:#FFFFFF;background-color:#0973b5;border-radius: 5px 5px 0 0;\">" +
								"<div style=\"height:5px;\" />" +
								"</td>" +
								"</tr>" +
								"<tr>" +
								"<td style=\"padding:10px;text-align:center;font-weight: 600;color:#2E74B5;\">" +
								title1 + "<br>" +
								"<span style=\"font-weight: 600;color:#2E74B5;\">" + title2 + "</span>" +
								"</td>" +
								"</tr>" +
								"<tr>" +
								"<td style=\"padding:10px;\">" +
								messageContent1 + "<b>" + bitacoraDocumentosExternosDTO.nombresRemitente() + " " + bitacoraDocumentosExternosDTO.apellidosRemitente() + "</b>" +
								"</td>" +
								"</tr>" +
								"<tr>" +
								"<td style=\"padding:10px;\">" +
								"Con los siguientes datos puede consultar el estado de su documento  <b>" +
								"</td>" +
								"</tr>" +
								"<tr>" +
								"<td style=\"padding:10px;\">" +
								"Número de sumilla: <b>" + numeroSumilla + "</b>" +
								"</td>" +
								"</tr>" +
								"<tr>" +
								"<td style=\"padding:10px;\">" +
								"Código del documento: <b>" + bitacoraDocumentosExternosDTO.codigoConsulta() + "</b>" +
								"</td>" +
								"</tr>" +
								"</tbody>" +
								"</table>" +
								"</td>" +
								"</tr>" +
								"<tr>" +
								"<td style=\"text-align:center;font-size:10px;color: #555;\">" +
								"</td>" +
								"</tr>" +
								"</tbody>" +
								"</table>";

				String htmlContent = htmlContent3;
				sendEmailUsuario(bitacoraDocumentosExternosDTO.correoRemitente(), htmlContent);

				switch (bitacoraDocumentosExternosDTO1.sumilla().sumSede().intValue()) {
					case 2:
						sendEmailUsuario("recepcioncue@ups.edu.ec", htmlContextBySede);
						break;
					case 3:
						sendEmailUsuario("recepcionqg@ups.edu.ec", htmlContextBySede);
						sendEmailUsuario("recepcionqs@ups.edu.ec", htmlContextBySede);
						break;
					case 4:
						sendEmailUsuario("recepciondocgyema@ups.edu.ec", htmlContextBySede);
						sendEmailUsuario("recepciondocgye@ups.edu.ec", htmlContextBySede);
						break;
				}

				return new ResponseEntity<>("Email enviado", HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("Error al enviar código a usuario externo para bitácora: {}", bitacoraDocumentosExternosDTO, e);
			return new ResponseEntity<>("No se pudo enviar el email al estudiante", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Email no enviado", HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<?> enviarEmailBySedeReasignado(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO, Long sede) {
		String mesaggeAtt = "Atentamente,";
		try {
			BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO1 = BitacoraDocumentosExternosDTO.toDTO(iBitacoraDocumentosExternosDao.findById(bitacoraDocumentosExternosDTO.codigo()).get());
			String htmlContextBySede = htmlMailBySedeReasignado(bitacoraDocumentosExternosDTO1);

			switch (sede.intValue()) {
				case 2:
					sendEmailUsuario("recepcioncue@ups.edu.ec", htmlContextBySede);
					break;
				case 3:
					sendEmailUsuario("recepcionqg@ups.edu.ec", htmlContextBySede);
					break;
				case 4:
					sendEmailUsuario("recepciongc@ups.edu.ec", htmlContextBySede);
					break;
			}
			return new ResponseEntity<>("Email enviado", HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error al enviar email por sede reasignado para bitácora: {}", bitacoraDocumentosExternosDTO, e);
			return new ResponseEntity<>("No se pudo enviar el email al estudiante", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<?> enviarEmailDestinatario(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
		String mesaggeAtt = "Atentamente,";
		try {
			BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO1 = BitacoraDocumentosExternosDTO.toDTO(iBitacoraDocumentosExternosDao.findById(bitacoraDocumentosExternosDTO.codigo()).get());
			String htmlContextBySede = htmlMailRecepcionDesinatario(bitacoraDocumentosExternosDTO1);
			GthMail gthMail = iGthMailDao.getMailByPerCodigo(bitacoraDocumentosExternosDTO1.destinatario().codigo());
			sendEmailUsuario(gthMail.getMaiDireccion(), htmlContextBySede);
			return new ResponseEntity<>("Email enviado", HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error al enviar email al destinatario para bitácora: {}", bitacoraDocumentosExternosDTO, e);
			return new ResponseEntity<>("No se pudo enviar el email al Destinatario", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<?> enviarSolicitudDocumentacionFisica(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
		try {
			String htmlContextDocFisica = htmlMailDocumentacionFisica(bitacoraDocumentosExternosDTO);
			sendEmailUsuario(bitacoraDocumentosExternosDTO.correoRemitente(), htmlContextDocFisica);
			return new ResponseEntity<>("Email enviado", HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error al enviar solicitud de documentación física para bitácora: {}", bitacoraDocumentosExternosDTO, e);
			return new ResponseEntity<>("No se pudo enviar el email al remitente solicitando documentación física", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<?> enviarMailRemitenteRespuestaElectronica(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
		try {
			String htmlContextDocElectronica = htmlMailDocumentacionElectronicaRespuesta(bitacoraDocumentosExternosDTO);
			sendEmailUsuario(bitacoraDocumentosExternosDTO.correoRemitente(), htmlContextDocElectronica);
			return new ResponseEntity<>("Email enviado", HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error al enviar email de respuesta electrónica al remitente para bitácora: {}", bitacoraDocumentosExternosDTO, e);
			return new ResponseEntity<>("No se pudo enviar el email al remitente con la respuesta correcta", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<?> enviarMailDestinatarioDocFisica(BitacoraDTO bitacoraDTO) {
		String mesaggeAtt = "Atentamente,";
		try {
			BitacoraDTO bitacoraDTO1 = BitacoraDTO.toDTO(iBitacoraDao.findById(bitacoraDTO.codigo()).get());
			String htmlContextBySede = htmlMailDestinatarioDocumentacionFisica(bitacoraDTO1);
			GthMail gthMail = iGthMailDao.getMailByPerCodigo(bitacoraDTO1.destinatario().codigo());
			sendEmailUsuario(gthMail.getMaiDireccion(), htmlContextBySede);
			return new ResponseEntity<>("Email enviado", HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error al enviar email de documentación física al destinatario para bitácora: {}", bitacoraDTO, e);
			return new ResponseEntity<>("No se pudo enviar el email al Destinatario", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<?> enviarMailDestinatarioReasignado(EventoBitacoraDTO eventoBitacoraDTO) {
		String mesaggeAtt = "Atentamente,";
		try {
			BitacoraDTO bitacoraDTO1 = BitacoraDTO.toDTO(iBitacoraDao.findById(eventoBitacoraDTO.bitacora().codigo()).get());
			String htmlContextBySede = htmlMailDocFisicaDesinatarioReasignado(bitacoraDTO1);
			GthMail gthMail = iGthMailDao.getMailByPerCodigo(eventoBitacoraDTO.perCodigoReasignado().codigo());
			sendEmailUsuario(gthMail.getMaiDireccion(), htmlContextBySede);
			return new ResponseEntity<>("Email enviado", HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error al enviar email a destinatario reasignado para evento de bitácora: {}", eventoBitacoraDTO, e);
			return new ResponseEntity<>("No se pudo enviar el email al Destinatario", HttpStatus.BAD_REQUEST);
		}
	}

	public String htmlMailDocumentacionElectronicaRespuesta(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
		return "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f2f2f2;width:100%;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"padding: 20px 20px 5px 20px;\">" +
				"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;border:1px solid #DDD;border-radius: 5px;box-shadow: 0px 0px 3px 0px #CCC;border-collapse:separate;background-color: #FFF;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:20px;color:#FFFFFF;background-color:#0973b5;border-radius: 5px 5px 0 0;\">" +
				"<div style=\"height:5px;\" />" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;text-align:center;font-weight: 600;color:#2E74B5;\">" +
				title1 + "<br>" +
				"<span style=\"font-weight: 600;color:#2E74B5;\">" + title2 + "</span>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;\">" +
				"El destinatario ha dado trámite a su documento con número de sumilla: <b>" + bitacoraDocumentosExternosDTO.sumilla().numeroSumilla() + "</b>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:10px;color: #555;\">" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>";
	}

	public String htmlMailDocumentacionFisica(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
		return "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f2f2f2;width:100%;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"padding: 20px 20px 5px 20px;\">" +
				"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;border:1px solid #DDD;border-radius: 5px;box-shadow: 0px 0px 3px 0px #CCC;border-collapse:separate;background-color: #FFF;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:20px;color:#FFFFFF;background-color:#0973b5;border-radius: 5px 5px 0 0;\">" +
				"<div style=\"height:5px;\" />" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;text-align:center;font-weight: 600;color:#2E74B5;\">" +
				title1 + "<br>" +
				"<span style=\"font-weight: 600;color:#2E74B5;\">" + title2 + "</span>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;\">" +
				"<b> El destinatario ha enviado la documentación física " + "</b>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:10px;color: #555;\">" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>";
	}

	public String htmlMailBySede(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
		return "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f2f2f2;width:100%;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"padding: 20px 20px 5px 20px;\">" +
				"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;border:1px solid #DDD;border-radius: 5px;box-shadow: 0px 0px 3px 0px #CCC;border-collapse:separate;background-color: #FFF;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:20px;color:#FFFFFF;background-color:#0973b5;border-radius: 5px 5px 0 0;\">" +
				"<div style=\"height:5px;\" />" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;text-align:center;font-weight: 600;color:#2E74B5;\">" +
				title1 + "<br>" +
				"<span style=\"font-weight: 600;color:#2E74B5;\">" + title2 + "</span>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;\">" +
				"Tiene un nuevo documento electrónico asignado con número de sumilla: <b>" + bitacoraDocumentosExternosDTO.sumilla().numeroSumilla() + "</b>" +
				"ingrese al siguiente enlace para visualizarlo: <a href=\"https://sgad.ups.edu.ec/sumilla\">" + "Sistema gestión documental" + "</a>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:10px;color: #555;\">" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>";
	}

	public String htmlMailRecepcionDesinatario(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
		return "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f2f2f2;width:100%;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"padding: 20px 20px 5px 20px;\">" +
				"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;border:1px solid #DDD;border-radius: 5px;box-shadow: 0px 0px 3px 0px #CCC;border-collapse:separate;background-color: #FFF;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:20px;color:#FFFFFF;background-color:#0973b5;border-radius: 5px 5px 0 0;\">" +
				"<div style=\"height:5px;\" />" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;text-align:center;font-weight: 600;color:#2E74B5;\">" +
				title1 + "<br>" +
				"<span style=\"font-weight: 600;color:#2E74B5;\">" + title2 + "</span>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;\">" +
				"Tiene un nuevo documento electrónico con número de sumilla: <b>" + bitacoraDocumentosExternosDTO.sumilla().numeroSumilla() + "</b>" +
				"ingrese al siguiente enlace para visualizarlo: <a href=\"https://sgad.ups.edu.ec/bitacora\">" + "Sistema gestión documental" + "</a>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:10px;color: #555;\">" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>";
	}

	public String htmlMailDestinatarioDocumentacionFisica(BitacoraDTO bitacoraDTO) {
		return "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f2f2f2;width:100%;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"padding: 20px 20px 5px 20px;\">" +
				"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;border:1px solid #DDD;border-radius: 5px;box-shadow: 0px 0px 3px 0px #CCC;border-collapse:separate;background-color: #FFF;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:20px;color:#FFFFFF;background-color:#0973b5;border-radius: 5px 5px 0 0;\">" +
				"<div style=\"height:5px;\" />" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;text-align:center;font-weight: 600;color:#2E74B5;\">" +
				title1 + "<br>" +
				"<span style=\"font-weight: 600;color:#2E74B5;\">" + title2 + "</span>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;\">" +
				"Tiene un nuevo documento electrónico con número de sumilla: <b>" + bitacoraDTO.sumilla().numeroSumilla() + "</b> " + ", " +
				"ingrese al siguiente enlace para visualizarlo: <a href=\"https://sgad.ups.edu.ec/bitacora\">" + "Sistema gestión documental" + "</a>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:10px;color: #555;\">" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>";
	}

	public String htmlMailBySedeReasignado(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
		return "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f2f2f2;width:100%;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"padding: 20px 20px 5px 20px;\">" +
				"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;border:1px solid #DDD;border-radius: 5px;box-shadow: 0px 0px 3px 0px #CCC;border-collapse:separate;background-color: #FFF;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:20px;color:#FFFFFF;background-color:#0973b5;border-radius: 5px 5px 0 0;\">" +
				"<div style=\"height:5px;\" />" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;text-align:center;font-weight: 600;color:#2E74B5;\">" +
				title1 + "<br>" +
				"<span style=\"font-weight: 600;color:#2E74B5;\">" + title2 + "</span>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;\">" +
				"Tiene un nuevo documento electrónico reasignado con número de sumilla: <b>" + bitacoraDocumentosExternosDTO.sumilla().numeroSumilla() + "</b>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:10px;color: #555;\">" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>";
	}

	public String htmlMailByBitacoraDocFisica(BitacoraDTO bitacoraDTO) {
		return "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f2f2f2;width:100%;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"padding: 20px 20px 5px 20px;\">" +
				"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;border:1px solid #DDD;border-radius: 5px;box-shadow: 0px 0px 3px 0px #CCC;border-collapse:separate;background-color: #FFF;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:20px;color:#FFFFFF;background-color:#0973b5;border-radius: 5px 5px 0 0;\">" +
				"<div style=\"height:5px;\" />" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;text-align:center;font-weight: 600;color:#2E74B5;\">" +
				title1 + "<br>" +
				"<span style=\"font-weight: 600;color:#2E74B5;\">" + title2 + "</span>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;\">" +
				"Tiene un nuevo documento electrónico reasignado con número de sumilla: <b>" + bitacoraDTO.sumilla().numeroSumilla() + "</b>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:10px;color: #555;\">" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>";
	}

	public String htmlMailDocFisicaDesinatarioReasignado(BitacoraDTO bitacoraDTO) {
		return "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: #f2f2f2;width:100%;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"padding: 20px 20px 5px 20px;\">" +
				"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:100%;border:1px solid #DDD;border-radius: 5px;box-shadow: 0px 0px 3px 0px #CCC;border-collapse:separate;background-color: #FFF;\">" +
				"<tbody>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:20px;color:#FFFFFF;background-color:#0973b5;border-radius: 5px 5px 0 0;\">" +
				"<div style=\"height:5px;\" />" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;text-align:center;font-weight: 600;color:#2E74B5;\">" +
				title1 + "<br>" +
				"<span style=\"font-weight: 600;color:#2E74B5;\">" + title2 + "</span>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"padding:10px;\">" +
				"Tiene un nuevo documento electrónico reasignado con número de sumilla: <b>" + bitacoraDTO.sumilla().numeroSumilla() + "</b>" + ", " +
				"ingrese al siguiente enlace para visualizarlo: <a href=\"https://sgad.ups.edu.ec/bitacora\">" + "Sistema gestión documental" + "</a>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style=\"text-align:center;font-size:10px;color: #555;\">" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>";
	}
}