package ec.edu.ups.sgadnuxt.service;

import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDTO;

import java.util.List;

public interface IBitacoraService {
    List<BitacoraDTO> findAllBitacora();
    List<BitacoraDTO> findAllBitacorasBySede(Long sede);
    List<BitacoraDTO> findAllBitacorasByFechasAndEstado(String fechaInicio, String fechaFin, Long resPerCodigo);
    BitacoraDTO findBitacoraByNumSumilla(String numSumilla);
    List<BitacoraDTO> getBitacorasByPerCodigoDestinatario(Long perCodigoDestinatario);
    BitacoraDTO saveBitacora(BitacoraDTO bitacoraDTO);
    void updateBitacora(BitacoraDTO bitacoraDTO, Long codigo);
    void updateEstadoEnvioBitacora(BitacoraDTO bitacoraDTO);
    List<BitacoraDTO> findBitacoraById(Long codigo);
    void deleteBitacora(Long codigo);
    void deleteByCodigoSumilla(Long sumCodigo);
}

