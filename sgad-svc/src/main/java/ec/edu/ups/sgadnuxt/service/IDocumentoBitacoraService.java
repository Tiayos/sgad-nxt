package ec.edu.ups.sgadnuxt.service;

import ec.edu.ups.sgadnuxt.entity.dto.sgad.DocumentosBitacoraDTO;
import java.util.List;

public interface IDocumentoBitacoraService {
    List<DocumentosBitacoraDTO> findAllDocumentosByBitCodigo(Long bitCodigo);
    List<DocumentosBitacoraDTO> findAllDocumentosByTramiteAndSede(Long tramite, Long sede);

    List<DocumentosBitacoraDTO> findAllDocumentosRespuestaBitacoraByBitCodigo(Long bitCodigo);
    void saveDocumentoBitacora(DocumentosBitacoraDTO documentosBitacoraDTO);
    void deleteDocumentosByBitCodigo(Long bitCodigo);
}

