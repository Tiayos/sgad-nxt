package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.sgad.DocumentosBitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumentoBitacora;
import ec.edu.ups.sgadnuxt.repository.IDocumentoBitacoraDao;
import ec.edu.ups.sgadnuxt.service.IDocumentoBitacoraService;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DocumentoBitacoralmpl implements IDocumentoBitacoraService {

    @Autowired
    private IDocumentoBitacoraDao iDocumentoBitacoraDao;

    @Override
    public List<DocumentosBitacoraDTO> findAllDocumentosByBitCodigo(Long bitCodigo) {
        return iDocumentoBitacoraDao.findAllDocumentosByBitCodigo(bitCodigo)
                .stream()
                .map(DocumentosBitacoraDTO::toDTO)
                .toList();
    }

    // Servicio que devuelve todos los documentos segun trámite y sede (expanded sumilla)
    @Override
    public List<DocumentosBitacoraDTO> findAllDocumentosByTramiteAndSede(Long tramite, Long sede) {
        return iDocumentoBitacoraDao.findAllDocumentosByNumeroTramiteAndSede(tramite, sede)
                .stream()
                .map(DocumentosBitacoraDTO::toDTO)
                .toList();
    }

    @Override
    public List<DocumentosBitacoraDTO> findAllDocumentosRespuestaBitacoraByBitCodigo(Long bitCodigo) {
        return iDocumentoBitacoraDao.findAllDocsRespuestaTramiteByBitCodigo(bitCodigo)
                .stream()
                .map(DocumentosBitacoraDTO::toDTO)
                .toList();
    }

    @Override
    public void saveDocumentoBitacora(DocumentosBitacoraDTO documentosBitacoraDTO) {
        try {
            iDocumentoBitacoraDao.save(new SgadDocumentoBitacora(documentosBitacoraDTO));
        }catch (PersistenceException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDocumentosByBitCodigo(Long bitCodigo) {
        try {
            List<SgadDocumentoBitacora> sgadDocumentoBitacoraList = iDocumentoBitacoraDao.validarDocumentosEliminar();
            if(!sgadDocumentoBitacoraList.isEmpty()){
                iDocumentoBitacoraDao.deleteEventoByBitCodigo(bitCodigo);
            }
        }catch (PersistenceException ex){
            ex.printStackTrace();
        }
    }
}
