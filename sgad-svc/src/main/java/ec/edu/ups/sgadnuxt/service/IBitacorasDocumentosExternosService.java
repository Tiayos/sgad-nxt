package ec.edu.ups.sgadnuxt.service;

import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDocumentosExternosDTO;
import java.util.List;

public interface IBitacorasDocumentosExternosService {
    List<BitacoraDocumentosExternosDTO> findAllBitacorasExternos();
    List<BitacoraDocumentosExternosDTO> findAllBitacorasExternosBySede(Long sede);
    List<BitacoraDocumentosExternosDTO> findAllBitacorasExternosByPerCodigoDestinatario(Long perCodigoDestinatario);
    BitacoraDocumentosExternosDTO findBitacorasExterna(Long codigo);
    BitacoraDocumentosExternosDTO findBitacoraBySumilla(String numSumilla, String codigo);
    BitacoraDocumentosExternosDTO saveBitacorasExternos(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO);
    void updateDocumentosExternos(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO, Long codigo, Long accion, Long sede);
    void updateBitacoraElectronica(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO);
    void deleteDocumentosExternos(Long codigo);
}

