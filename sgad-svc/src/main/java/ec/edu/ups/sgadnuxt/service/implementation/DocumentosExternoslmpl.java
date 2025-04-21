package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.DocumentosExternosDTO;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumentosExternosModal;
import ec.edu.ups.sgadnuxt.repository.IDocumentosExternosDao;
import ec.edu.ups.sgadnuxt.service.IDocumentosExternosService;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentosExternoslmpl implements IDocumentosExternosService {

    private static final Logger log = LoggerFactory.getLogger(DocumentosExternoslmpl.class);

    private IDocumentosExternosDao iDocumentosExternosDao;

    public DocumentosExternoslmpl(IDocumentosExternosDao documentosExternosDao) {
        this.iDocumentosExternosDao = documentosExternosDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentosExternosDTO> findAllDocumentosExternos() {
        return iDocumentosExternosDao.findAllDocumentosExternos()
                .stream()
                .map(DocumentosExternosDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentosExternosDTO findDocumentoExternoByCodigo(Long codigo) {
        try {
            return DocumentosExternosDTO.toDTO(iDocumentosExternosDao.findById(codigo).get());
        } catch (Exception e) {
            log.error("Error al buscar documento externo con código: {}", codigo, e);
            throw new RuntimeException("No se pudo encontrar el documento externo", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentosExternosDTO> findDocumentosExternosByBidCodigo(Long bidCodigo) {
        return iDocumentosExternosDao.findAllDocumentosExternosByBidCodigo(bidCodigo)
                .stream()
                .map(DocumentosExternosDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentosExternosDTO> findDocumentosExternosByBidCodigoRecibido(Long bidCodigo) {
        return iDocumentosExternosDao.findAllDocumentosExternosByBidCodigoRecibido(bidCodigo)
                .stream()
                .map(DocumentosExternosDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentosExternosDTO> findDocumentosExternosByBidCodigoRespuesta(Long bidCodigo) {
        return iDocumentosExternosDao.findAllDocumentosExternosByBidCodigoRespuesta(bidCodigo)
                .stream()
                .map(DocumentosExternosDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentosExternosDTO> obtenerDocumentosElectronicosPorCodigoConsulta(String numSumilla, String codDocumento) {
        return iDocumentosExternosDao.finDocumentosByNumSumillaAndCodigoAleatorio(numSumilla, codDocumento)
                .stream()
                .map(DocumentosExternosDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public DocumentosExternosDTO saveDocumentosExternos(DocumentosExternosDTO documentosExternosDTO) {
        try {
            SgadDocumentosExternosModal documentosExternosDTO1 = iDocumentosExternosDao.save(new SgadDocumentosExternosModal(documentosExternosDTO));
            return DocumentosExternosDTO.toDTO(documentosExternosDTO1);
        } catch (PersistenceException e) {
            log.error("Error al guardar documento externo: {}", documentosExternosDTO, e);
            throw new RuntimeException("No se pudo guardar el documento externo", e);
        }
    }

    @Override
    @Transactional
    public void deleteDocumentosExternosByDoeCodigo(Long codigo) {
        try {
            iDocumentosExternosDao.deleteById(codigo);
        } catch (PersistenceException e) {
            log.error("Error al eliminar documento externo con código: {}", codigo, e);
            throw new RuntimeException("No se pudo eliminar el documento externo", e);
        }
    }
}