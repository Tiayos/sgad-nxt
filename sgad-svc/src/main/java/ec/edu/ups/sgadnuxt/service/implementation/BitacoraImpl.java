package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EstadoDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EventoBitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.TransDocumentalDTO;
import ec.edu.ups.sgadnuxt.entity.model.BitacoraModel;
import ec.edu.ups.sgadnuxt.entity.model.SumillaModel;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadEstado;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadEventoBitacora;
import ec.edu.ups.sgadnuxt.exception.NotFoundException;
import ec.edu.ups.sgadnuxt.repository.IBitacoraDao;
import ec.edu.ups.sgadnuxt.repository.IEventoBitacoraDao;
import ec.edu.ups.sgadnuxt.repository.ISumillaDao;
import ec.edu.ups.sgadnuxt.service.IBitacoraService;
import ec.edu.ups.sgadnuxt.service.ISumillaService;
import ec.edu.ups.sgadnuxt.utils.Empresa;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BitacoraImpl implements IBitacoraService {

    private IBitacoraDao bitacoraDao;
    @Autowired
    private ISumillaDao sumillaDao;
    @Autowired
    private IEventoBitacoraDao iEventoBitacoraDao;

    public BitacoraImpl(IBitacoraDao bitacoraDao) {
        this.bitacoraDao = bitacoraDao;
    }

    @Override
    public List<BitacoraDTO> findAllBitacora() {
        return bitacoraDao.getBitacoras()
                .stream()
                .map(BitacoraDTO::toDTO)
                .toList();
    }

    @Override
    public List<BitacoraDTO> findAllBitacorasBySede(Long sede) {
        return bitacoraDao.getBitacorasBySede(sede)
                .stream()
                .map(BitacoraDTO::toDTO)
                .toList();
    }

    @Override
    public List<BitacoraDTO> findAllBitacorasByFechasAndEstado(String fechaInicio, String fechaFin, Long resPerCodigo) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate initialDate = LocalDate.parse(fechaInicio,formatoFecha);
        LocalDate lastDate = LocalDate.parse(fechaFin,formatoFecha);
        try{
         return bitacoraDao.getBitacorasByFechaAndEstado(initialDate,lastDate, resPerCodigo)
                         .stream()
                         .map(BitacoraDTO::toDTO)
                         .toList();
        }catch (PersistenceException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BitacoraDTO findBitacoraByNumSumilla(String numSumilla) {
        return BitacoraDTO.toDTO(bitacoraDao.getBitacorasByNumSumilla(numSumilla));
    }
    @Override
    public List<BitacoraDTO> getBitacorasByPerCodigoDestinatario(Long perCodigoDestinatario) {
        return bitacoraDao.getBitacoraByPerCodigoDestinatario(perCodigoDestinatario)
                .stream()
                .map(BitacoraDTO::toDTO)
                .toList();
    }

    @Override
    public BitacoraDTO saveBitacora(BitacoraDTO bitacoraDTO) {
        try {
            BitacoraModel bitacoraModel;
            if (bitacoraDTO.documentoReasignado()) {
                Long sedeByEmp = bitacoraDao.getEmpCodigoByPerCodigo(bitacoraDTO.destinatario().codigo());
                Long perCodigo = Empresa.getPerCodigoBySedeCode(sedeByEmp.intValue());

                BitacoraDTO bitacoraDTO2 = new BitacoraDTO(
                        null,
                        bitacoraDTO.nombresRemitente(),
                        bitacoraDTO.apellidosRemitente(),
                        bitacoraDTO.receptorDocumento(),
                        bitacoraDTO.destinatario(),
                        bitacoraDTO.asunto(),
                        bitacoraDTO.lugarDestino(),
                        bitacoraDTO.mensajero(),
                        bitacoraDTO.numeroGuia(),
                        bitacoraDTO.observaciones(),
                        bitacoraDTO.usrEmisor(),
                        bitacoraDTO.usrReceptor(),
                        bitacoraDTO.fechaEntrega(),
                        bitacoraDTO.horaEntrega(),
                        bitacoraDTO.fechaRecepcion(),
                        bitacoraDTO.horaRecepcion(),
                        bitacoraDTO.sumilla(),
                        bitacoraDTO.docArchivo(),
                        bitacoraDTO.nombreArchivo(),
                        bitacoraDTO.estadoTransferencia(),
                        bitacoraDTO.adicionado(),
                        bitacoraDTO.mensajeroExterno(),
                        true,
                        bitacoraDTO.secuencialSede(),
                        bitacoraDTO.secuencialDocumento(),
                        perCodigo,
                        bitacoraDTO.perCodigoEntregaDocumentacion(),
                        bitacoraDTO.perCodigoRecibeDocumentacion(),
                        bitacoraDTO.fechaEntregaDocumentacion(),
                        bitacoraDTO.horaEntreagaDocumentacion()
                );
                 bitacoraModel = bitacoraDao.save(new BitacoraModel(bitacoraDTO2));
            } else {
                 bitacoraModel = bitacoraDao.save(new BitacoraModel(bitacoraDTO));
            }

            BitacoraDTO bitacoraDTO1 = BitacoraDTO.toDTO(bitacoraModel);

            EventoBitacoraDTO eventoBitacoraDTO = new EventoBitacoraDTO(
                    null,
                    LocalDate.now(),
                    'S',
                    bitacoraDTO1,
                    EstadoDTO.toDTO(new SgadEstado(2L)),
                    bitacoraDTO1.adicionado(),
                    bitacoraDTO1.receptorDocumento(),
                    null
            );
            iEventoBitacoraDao.save(new SgadEventoBitacora(eventoBitacoraDTO));
            return bitacoraDTO1;

        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateBitacora(BitacoraDTO bitacoraDTO, Long codigo) {
         bitacoraDao.findById(codigo)
                .map(
                        bitacoraMap -> {
                            bitacoraMap.setNombresRemitente(bitacoraDTO.nombresRemitente());
                            bitacoraMap.setApellidosRemitente(bitacoraDTO.apellidosRemitente());
                            bitacoraMap.setReceptor(bitacoraDTO.usrReceptor() != null ? new GthPersona(bitacoraDTO.usrReceptor().codigo()) : null);
                            bitacoraMap.setDestinatario(new GthPersona(bitacoraDTO.destinatario().codigo()));
                            bitacoraMap.setAsunto(bitacoraDTO.asunto());
                            bitacoraMap.setLugarDestino(bitacoraDTO.lugarDestino());
                            bitacoraMap.setMensajero(bitacoraDTO.mensajero().codigo() != null ? new GthPersona(bitacoraDTO.mensajero().codigo()) : null);
                            bitacoraMap.setNumeroGuia(bitacoraDTO.numeroGuia());
                            bitacoraMap.setObservaciones(bitacoraDTO.observaciones());
                            bitacoraMap.setUsrEmisor(bitacoraDTO.usrEmisor() != null ? new GthPersona(bitacoraDTO.usrEmisor().codigo()) : null);
                            bitacoraMap.setFechaEntrega(bitacoraDTO.fechaEntrega());
                            bitacoraMap.setHoraEntrega(bitacoraDTO.horaEntrega());
                            bitacoraMap.setDocArchivo(bitacoraDTO.docArchivo());
                            bitacoraMap.setNombreArchivo(bitacoraDTO.nombreArchivo());
                            bitacoraMap.setMensajeroExterno(bitacoraDTO.mensajeroExterno());
                            return BitacoraDTO.toDTO(bitacoraDao.save(bitacoraMap));
                        }) .orElseThrow(
                        () -> new NotFoundException("No se encontró la bitácora con número: ".concat(codigo.toString()))
                );
    }

    @Override
    public void updateEstadoEnvioBitacora(BitacoraDTO bitacoraDTO) {
       bitacoraDao.save(new BitacoraModel(bitacoraDTO));
    }

    @Override
    public List<BitacoraDTO> findBitacoraById(Long codigo) {
        return null;
    }

    @Override
    public void deleteBitacora(Long codigo) {
        bitacoraDao.deleteById(codigo);
    }

    @Transactional
    @Override
    public void deleteByCodigoSumilla(Long sumCodigo) {
        BitacoraModel bitacoraModel = bitacoraDao.getBitacoraByCodigoSumilla(sumCodigo);
        bitacoraDao.deleteById(bitacoraModel.getCodigo());
        sumillaDao.deleteById(sumCodigo);
    }
}
