package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDocumentosExternosDTO;
import ec.edu.ups.sgadnuxt.entity.dto.DocumentosExternosDTO;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumentosExternosModal;
import ec.edu.ups.sgadnuxt.repository.IDocumentosExternosDao;
import ec.edu.ups.sgadnuxt.service.IDocumentosExternosService;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentosExternoslmpl implements IDocumentosExternosService {

    private IDocumentosExternosDao iDocumentosExternosDao;

    public DocumentosExternoslmpl(IDocumentosExternosDao documentosExternosDao) {
        this.iDocumentosExternosDao = documentosExternosDao;
    }

    @Override
    public List<DocumentosExternosDTO> findAllDocumentosExternos() {
        return iDocumentosExternosDao.findAllDocumentosExternos()
                .stream()
                .map(DocumentosExternosDTO::toDTO)
                .toList();
                }

    @Override
    public DocumentosExternosDTO findDocumentoExternoByCodigo(Long codigo) {
        return DocumentosExternosDTO.toDTO(iDocumentosExternosDao.findById(codigo).get());
    }

    @Override
    public List<DocumentosExternosDTO> findDocumentosExternosByBidCodigo(Long bidCodigo) {
        return iDocumentosExternosDao.findAllDocumentosExternosByBidCodigo(bidCodigo)
                .stream()
                .map(DocumentosExternosDTO::toDTO)
                .toList();
    }

    @Override
    public List<DocumentosExternosDTO> findDocumentosExternosByBidCodigoRecibido(Long bidCodigo) {
        return iDocumentosExternosDao.findAllDocumentosExternosByBidCodigoRecibido(bidCodigo)
                .stream()
                .map(DocumentosExternosDTO::toDTO)
                .toList();
    }

    @Override
    public List<DocumentosExternosDTO> findDocumentosExternosByBidCodigoRespuesta(Long bidCodigo) {
        return iDocumentosExternosDao.findAllDocumentosExternosByBidCodigoRespuesta(bidCodigo)
                .stream()
                .map(DocumentosExternosDTO::toDTO)
                .toList();
    }

    @Override
    public List<DocumentosExternosDTO> obtenerDocumentosElectronicosPorCodigoConsulta(String numSumilla, String codDocumento) {
        return iDocumentosExternosDao.finDocumentosByNumSumillaAndCodigoAleatorio(numSumilla, codDocumento)
                .stream()
                .map(DocumentosExternosDTO::toDTO)
                .toList();
    }

    @Override
    public DocumentosExternosDTO saveDocumentosExternos(DocumentosExternosDTO documentosExternosDTO) {
        try {
          SgadDocumentosExternosModal documentosExternosDTO1 =  iDocumentosExternosDao.save(new SgadDocumentosExternosModal(documentosExternosDTO));
          return DocumentosExternosDTO.toDTO(documentosExternosDTO1);
        }catch (PersistenceException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteDocumentosExternosByDoeCodigo(Long codigo) {
        iDocumentosExternosDao.deleteById(codigo);
    }

    //    @Override
//    public List<DocumentosBitacoraDTO> findAllDocumentosByBitCodigo(Long bitCodigo) {
//        return iDocumentoBitacoraDao.findAllDocumentosByBitCodigo(bitCodigo)
//                .stream()
//                .map(DocumentosBitacoraDTO::toDTO)
//                .toList();
//    }
//
//    @Override
//    public void saveDocumentoBitacora(DocumentosBitacoraDTO documentosBitacoraDTO) {
//        try {
//            iDocumentoBitacoraDao.save(new SgadDocumentoBitacora(documentosBitacoraDTO));
//        }catch (PersistenceException e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void deleteDocumentosByBitCodigo(Long bitCodigo) {
//        try {
//            List<SgadDocumentoBitacora> sgadDocumentoBitacoraList = iDocumentoBitacoraDao.validarDocumentosEliminar();
//            if(!sgadDocumentoBitacoraList.isEmpty()){
//                iDocumentoBitacoraDao.deleteEventoByBitCodigo(bitCodigo);
//            }
//        }catch (PersistenceException ex){
//            ex.printStackTrace();
//        }
//    }
}
