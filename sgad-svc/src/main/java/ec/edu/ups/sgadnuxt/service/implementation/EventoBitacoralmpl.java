package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EstadoDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EventoBitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadEventoBitacora;
import ec.edu.ups.sgadnuxt.exception.NotFoundException;
import ec.edu.ups.sgadnuxt.repository.IEstadoDao;
import ec.edu.ups.sgadnuxt.repository.IEventoBitacoraDao;
import ec.edu.ups.sgadnuxt.repository.ISumillaDao;
import ec.edu.ups.sgadnuxt.service.IEventoBitacoraService;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventoBitacoralmpl implements IEventoBitacoraService {

    private static final Logger log = LoggerFactory.getLogger(EventoBitacoralmpl.class);

    @Autowired
    private ISumillaDao iSumillaDao;
    @Autowired
    private IEventoBitacoraDao iEventoBitacoraDao;
    @Autowired
    private IEstadoDao iEstadoDao;

    @Override
    @Transactional(readOnly = true)
    public List<EventoBitacoraDTO> findAllEventosBitacoras() {
        return iEventoBitacoraDao.findAll()
                .stream()
                .map(EventoBitacoraDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EventoBitacoraDTO findEventosByBitCodigo(Long bitCodigo) {
        try {
            return EventoBitacoraDTO.toDTO(iEventoBitacoraDao.getEventosBitacora(bitCodigo));
        } catch (Exception e) {
            log.error("Error al buscar eventos por código de bitácora: {}", bitCodigo, e);
            throw new RuntimeException("No se pudo encontrar eventos para la bitácora", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoBitacoraDTO> findAllEventosByBitCodigo(Long bitCodigo) {
        return iEventoBitacoraDao.getAllEventosByBitCodigo(bitCodigo)
                .stream()
                .map(EventoBitacoraDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDTO> findAllEstados() {
        return iEstadoDao.findAll()
                .stream()
                .map(EstadoDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoBitacoraDTO> findEventosByPerCodigo(Long perCodigo) {
        return iEventoBitacoraDao.getAllEventosByPerCodigo(perCodigo)
                .stream()
                .map(EventoBitacoraDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoBitacoraDTO> findAllEventosByPerCodigoRecepcionReasignado(Long perCodigoRecepcionReasignado) {
        return iEventoBitacoraDao.getEventosByPerCodigoRecepcionReasignado(perCodigoRecepcionReasignado)
                .stream()
                .map(EventoBitacoraDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public void saveEventoBitacora(EventoBitacoraDTO eventoBitacoraDTO) {
        try {
            SgadEventoBitacora sgadEventoBitacora = iEventoBitacoraDao.getEventoVigente(eventoBitacoraDTO.bitacora().codigo());
            sgadEventoBitacora.setEbiVigencia('N');
            iEventoBitacoraDao.save(sgadEventoBitacora);

            SgadEventoBitacora sgadEventoBitacora1 = new SgadEventoBitacora(eventoBitacoraDTO);
            sgadEventoBitacora1.setAudFechaAdicion(LocalDateTime.now());
            iEventoBitacoraDao.save(sgadEventoBitacora1);
        } catch (PersistenceException e) {
            log.error("Error al guardar evento de bitácora: {}", eventoBitacoraDTO, e);
            throw new RuntimeException("No se pudo guardar el evento de bitácora", e);
        }
    }

    @Override
    @Transactional
    public void updateEventoBitacora(EventoBitacoraDTO eventoBitacoraDTO) {
        // Implementación pendiente según requerimientos
    }

    @Override
    @Transactional
    public void deleteEventoByBitCodigo(Long bitCodigo) {
        try {
            iEventoBitacoraDao.deleteEventoByBitCodigo(bitCodigo);
        } catch (PersistenceException e) {
            log.error("Error al eliminar eventos por código de bitácora: {}", bitCodigo, e);
            throw new RuntimeException("No se pudo eliminar los eventos de bitácora", e);
        }
    }
}