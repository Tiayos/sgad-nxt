package ec.edu.ups.sgadnuxt.service;

import ec.edu.ups.sgadnuxt.entity.dto.DocumentosExternosDTO;
import java.util.List;

public interface IDocumentosExternosService {
    List<DocumentosExternosDTO> findAllDocumentosExternos();
    DocumentosExternosDTO findDocumentoExternoByCodigo(Long codigo);
    List<DocumentosExternosDTO> findDocumentosExternosByBidCodigo(Long bidCodigo);
    List<DocumentosExternosDTO> findDocumentosExternosByBidCodigoRecibido(Long bidCodigo);
    List<DocumentosExternosDTO> findDocumentosExternosByBidCodigoRespuesta(Long bidCodigo);
    List<DocumentosExternosDTO> obtenerDocumentosElectronicosPorCodigoConsulta(String numSumilla, String codDocumento);
    DocumentosExternosDTO saveDocumentosExternos(DocumentosExternosDTO documentosExternosDTO);
    void deleteDocumentosExternosByDoeCodigo(Long codigo);
}

