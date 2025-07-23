package ec.edu.ups.sgadnuxt.service.implementation;
import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.*;
import ec.edu.ups.sgadnuxt.entity.model.BitacoraModel;
import ec.edu.ups.sgadnuxt.entity.model.SumillaModel;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDetalleTransferencia;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumento;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumentoTipo;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadTransDocumentales;
import ec.edu.ups.sgadnuxt.exception.NotFoundException;
import ec.edu.ups.sgadnuxt.projection.GTHPersonaProjection;
import ec.edu.ups.sgadnuxt.repository.IBitacoraDao;
import ec.edu.ups.sgadnuxt.repository.IPersonaDao;
import ec.edu.ups.sgadnuxt.repository.transferencia.*;
import ec.edu.ups.sgadnuxt.service.ITransferenciaDocumentalService;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransferenciaDocumentalmpl implements ITransferenciaDocumentalService {

    private ITransferenciaDocumentalDao iTransferenciaDocumentalDao;

    @Autowired
    private IBitacoraDao iBitacoraDao;
    @Autowired
    private IPersonaDao iPersonaDao;
    @Autowired
    private IDetalleTransferenciaDao iDetalleTransferenciaDao;
    @Autowired
    private IDocumentoDao iDocumentoDao;
    @Autowired
    private IDocumentoTipoDao iDocumentoTipoDao;
    @Autowired
    private ITipoDocumentalDao iTipoDocumentalDao;
    public TransferenciaDocumentalmpl(ITransferenciaDocumentalDao iTransferenciaDocumentalDao) {
        this.iTransferenciaDocumentalDao = iTransferenciaDocumentalDao;
    }
    @Override
    public List<TransDocumentalDTO> findAllTransferenciasDocumentales() {
        return iTransferenciaDocumentalDao.findAll()
                .stream()
                .map(TransDocumentalDTO::toDTO)
                .toList();
    }

    @Override
    public TransDocumentalDTO findTransferenciaDocumentalByCodigo(Long codigo) {
        return TransDocumentalDTO.toDTO(iTransferenciaDocumentalDao.findById(codigo).get());
    }

    @Override
    public void saveTransferenciaDocumental(String fechaInicio, String fechaFin,Long perCodigoDestinatarioGW, Long perCodigoResponsable) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate initialDate = LocalDate.parse(fechaInicio,formatoFecha);
        LocalDate lastDate = LocalDate.parse(fechaFin,formatoFecha);

        try {
            List<BitacoraDTO> bitacoraModels = iBitacoraDao.getBitacorasByFechasEstadoResCodigo(initialDate, lastDate, perCodigoResponsable)
                    .stream()
                    .map(BitacoraDTO::toDTO)
                    .toList();

            if(bitacoraModels.size()!=0){
                for (BitacoraDTO bitacoraDTO : bitacoraModels){
                    iBitacoraDao.findById(bitacoraDTO.codigo())
                            .map(
                                    bitacoraMap -> {
                                        bitacoraMap.setNombresRemitente(bitacoraDTO.nombresRemitente());
                                        bitacoraMap.setApellidosRemitente(bitacoraDTO.apellidosRemitente());
                                        bitacoraMap.setDestinatario(new GthPersona(bitacoraDTO.destinatario().codigo()));
                                        bitacoraMap.setAsunto(bitacoraDTO.asunto());
                                        bitacoraMap.setLugarDestino(bitacoraDTO.lugarDestino());
                                        bitacoraMap.setMensajero(new GthPersona(bitacoraDTO.mensajero().codigo()));
                                        bitacoraMap.setNumeroGuia(bitacoraDTO.numeroGuia());
                                        bitacoraMap.setObservaciones(bitacoraDTO.observaciones());
                                        bitacoraMap.setUsrEmisor(bitacoraDTO.usrEmisor() != null ? new GthPersona(bitacoraDTO.usrEmisor().codigo()) : null);
                                        bitacoraMap.setReceptor(bitacoraDTO.usrReceptor() != null ? new GthPersona(bitacoraDTO.usrReceptor().codigo()) : null);
                                        bitacoraMap.setFechaEntrega(bitacoraDTO.fechaEntrega());
                                        bitacoraMap.setHoraEntrega(bitacoraDTO.horaEntrega());
                                        SumillaModel sumillaModel = bitacoraMap.getSumilla();
                                        bitacoraMap.setDocArchivo(bitacoraDTO.docArchivo());
                                        bitacoraMap.setNombreArchivo(bitacoraDTO.nombreArchivo());
                                        if(sumillaModel != null ){
                                            sumillaModel.setCodigo(bitacoraDTO.sumilla().codigo());
                                        }
                                        bitacoraMap.setSumilla(sumillaModel);
                                        bitacoraMap.setEstadoTransferencia('S');
                                        return BitacoraDTO.toDTO(iBitacoraDao.save(bitacoraMap));
                                    }) .orElseThrow(
                                    () -> new NotFoundException("No se encontró la bitácora con número: ".concat(bitacoraDTO.codigo().toString()))
                            );
                }

                GTHPersonaProjection gthResponsableAmbito = iPersonaDao.getGthPersonaAmbitoByPerCodigo(bitacoraModels.get(0).sumilla().responsable().codigo());
                GTHPersonaProjection gthReceptorAmbitoGW = iPersonaDao.getGthPersonaAmbitoByPerCodigo(perCodigoDestinatarioGW); //RECEPTOR PERSONA DE GESTION WEB
                
                TransDocumentalDTO transDocumentalDTO1 = new TransDocumentalDTO(
                        null,
                        gthReceptorAmbitoGW.getCODIGOAMBITO(),
                        gthResponsableAmbito.getCODIGOAMBITO(),
                        gthReceptorAmbitoGW.getCARGOCODIGO(),
                        gthResponsableAmbito.getCARGOCODIGO(),
                        LocalDate.now(),
                        null,
                        0L,
                        1L,
                        gthReceptorAmbitoGW.getPERCODIGO(),
                        gthResponsableAmbito.getPERCODIGO(),
                        "Manual",
                        gthReceptorAmbitoGW.getEMPCODIGO(),
                        gthResponsableAmbito.getEMPCODIGO(),
                        "Enviado",
                        "B",
                        "A",
                        "I",
                        gthResponsableAmbito.getMAIL()
                );
              SgadTransDocumentales sgadTransDocumentalModel =  iTransferenciaDocumentalDao.save(new SgadTransDocumentales(transDocumentalDTO1));
              crearExpediente(initialDate, lastDate, sgadTransDocumentalModel, bitacoraModels);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void crearExpediente(LocalDate fechaInicio, LocalDate fechaFin, SgadTransDocumentales sgadTransDocumentalModel,  List<BitacoraDTO> bitacoraModels ) {

        try {
            TransDocumentalDTO transDocumentalDTO = TransDocumentalDTO.toDTO(sgadTransDocumentalModel);  // Asegúrate de usar el DTO correcto
            DetalleTransferenciaDTO detalleTransferenciaDTO = new DetalleTransferenciaDTO(
                    null,
                    "RADICACIÓN DE DOCUMENTOS",
                    fechaFin,
                    fechaInicio,
                    1L,
                    "1",
                    transDocumentalDTO,  // Usar el objeto guardado
                    sgadTransDocumentalModel.getAudAdicionado()
            );

            SgadDetalleTransferencia sgadDetalleTransferencia = new SgadDetalleTransferencia(detalleTransferenciaDTO);
            sgadDetalleTransferencia.setTrdCodigo(sgadTransDocumentalModel); // Asegúrate de establecer la entidad guardada
            iDetalleTransferenciaDao.save(sgadDetalleTransferencia);

            //CREAR DOCUMENTOS
            crearDocumento(bitacoraModels, sgadDetalleTransferencia);
            
        }catch (PersistenceException e){
            e.printStackTrace();
        }
    }

    public void crearDocumento(List<BitacoraDTO> bitacoraModels, SgadDetalleTransferencia sgadDetalleTransferencia){
        try{
            DetalleTransferenciaDTO detalleTransferenciaDTO = DetalleTransferenciaDTO.toDTO(sgadDetalleTransferencia);
            Long contadorNumOrden=0L;
            for (BitacoraDTO bitacoraDTO : bitacoraModels){
//                Long lastIdDocumento = iDocumentoDao.lastIdDocumento()+1;
                contadorNumOrden ++;
                DocumentoDTO documentoDTO = new DocumentoDTO(
                        null,
                        bitacoraDTO.asunto(),
                        bitacoraDTO.sumilla().numeroHojas().longValue(),
                        detalleTransferenciaDTO,
                        contadorNumOrden,
                        detalleTransferenciaDTO.audAdicionado(),
                        bitacoraDTO.sumilla().fechaSumilla()
                        );
                SgadDocumento sgadTransDocumentalModel = new SgadDocumento(documentoDTO);
                sgadTransDocumentalModel.setDetCodigo(sgadDetalleTransferencia);
                iDocumentoDao.save(sgadTransDocumentalModel);

                crearDocumentoTipo(sgadTransDocumentalModel);
            }

        }catch (PersistenceException e){
            e.printStackTrace();
        }
    }

    public void crearDocumentoTipo(SgadDocumento sgadDocumento){
        try {
            DocumentoDTO documentoDTO = DocumentoDTO.toDTO(sgadDocumento);
            TipoDocumentalDTO tipoDocumentalDTO = TipoDocumentalDTO.toDTO(iTipoDocumentalDao.findById(2L).get());
            DocumentoTipoDTO documentoTipoDTO = new DocumentoTipoDTO(
                    null,
                    documentoDTO,
                    tipoDocumentalDTO,
                    documentoDTO.audAdicionado()
                    );
            SgadDocumentoTipo sgadDocumentoTipo = new SgadDocumentoTipo(documentoTipoDTO);
            iDocumentoTipoDao.save(sgadDocumentoTipo);
        }catch (PersistenceException e){
            e.printStackTrace();
        }

    }

    @Override
    public void updateTransferenciaDocumental(TransDocumentalDTO transDocumentalDTO, Long codigo) {

    }

    @Override
    public void deleteTransferenciaDocumentalByCodigo(Long codigo) {

    }
}
