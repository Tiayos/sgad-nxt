package ec.edu.ups.sgadnuxt.service;

import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.TransDocumentalDTO;

import java.util.Date;
import java.util.List;

public interface ITransferenciaDocumentalService {
    List<TransDocumentalDTO> findAllTransferenciasDocumentales();
    TransDocumentalDTO findTransferenciaDocumentalByCodigo(Long codigo);
    void saveTransferenciaDocumental(String fechaInicio, String fechaFin, Long perCodigoDestinatarioGW, Long perCodigoResponsable);
    void updateTransferenciaDocumental(TransDocumentalDTO transDocumentalDTO, Long codigo);
    void deleteTransferenciaDocumentalByCodigo(Long codigo);
}

