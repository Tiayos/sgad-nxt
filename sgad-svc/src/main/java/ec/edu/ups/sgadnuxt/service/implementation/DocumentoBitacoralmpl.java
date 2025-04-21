package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.sgad.DocumentosBitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumentoBitacora;
import ec.edu.ups.sgadnuxt.repository.IDocumentoBitacoraDao;
import ec.edu.ups.sgadnuxt.service.IDocumentoBitacoraService;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentoBitacoralmpl implements IDocumentoBitacoraService {

    private static final Logger log = LoggerFactory.getLogger(DocumentoBitacoralmpl.class);

    @Autowired
    private IDocumentoBitacoraDao iDocumentoBitacoraDao;

    @Override
    @Transactional(readOnly = true)
    public List<DocumentosBitacoraDTO> findAllDocumentosByBitCodigo(Long bitCodigo) {
        return iDocumentoBitacoraDao.findAllDocumentosByBitCodigo(bitCodigo)
                .stream()
                .map(DocumentosBitacoraDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentosBitacoraDTO> findAllDocumentosByTramiteAndSede(Long tramite, Long sede) {
        return iDocumentoBitacoraDao.findAllDocumentosByNumeroTramiteAndSede(tramite, sede)
                .stream()
                .map(DocumentosBitacoraDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentosBitacoraDTO> findAllDocumentosRespuestaBitacoraByBitCodigo(Long bitCodigo) {
        return iDocumentoBitacoraDao.findAllDocsRespuestaTramiteByBitCodigo(bitCodigo)
                .stream()
                .map(DocumentosBitacoraDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public void saveDocumentoBitacora(DocumentosBitacoraDTO documentosBitacoraDTO) {
        try {
            iDocumentoBitacoraDao.save(new SgadDocumentoBitacora(documentosBitacoraDTO));
        } catch (PersistenceException e) {
            log.error("Error al guardar documento de bitácora: {}", documentosBitacoraDTO, e);
            throw new RuntimeException("No se pudo guardar el documento de bitácora", e);
        }
    }

    @Override
    @Transactional
    public void deleteDocumentosByBitCodigo(Long bitCodigo) {
        try {
            List<SgadDocumentoBitacora> sgadDocumentoBitacoraList = iDocumentoBitacoraDao.validarDocumentosEliminar();
            if (!sgadDocumentoBitacoraList.isEmpty()) {
                iDocumentoBitacoraDao.deleteEventoByBitCodigo(bitCodigo);
            }
        } catch (PersistenceException e) {
            log.error("Error al eliminar documentos por código de bitácora: {}", bitCodigo, e);
            throw new RuntimeException("No se pudo eliminar los documentos de bitácora", e);
        }
    }
}