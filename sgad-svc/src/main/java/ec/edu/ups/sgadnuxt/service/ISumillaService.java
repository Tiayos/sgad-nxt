package ec.edu.ups.sgadnuxt.service;

import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import ec.edu.ups.sgadnuxt.projection.SedeProjection;

import java.util.List;

public interface ISumillaService {
    List<SumillaDTO> findAllSumilla();
    List<SumillaDTO> findAllSumillaBySede(Long sede);
    SedeProjection getSedeByEmail(String email);
    SumillaDTO saveSumilla(SumillaDTO casoFormulario, String email);
    SumillaDTO saveSumillaExterna(SumillaDTO casoFormulario);
    SumillaDTO updateSumilla(SumillaDTO sumilla, Long codigo);
    SumillaDTO findSumillaByNumeroSumilla(String numeroSumilla);
    void deleteSumilla(Long id);
}

