package ec.edu.ups.sgadnuxt.service;

import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import java.util.List;

public interface ISumillaService {
    List<SumillaDTO> findAllSumilla();
    SumillaDTO saveSumilla(SumillaDTO casoFormulario);
    SumillaDTO updateSumilla(SumillaDTO sumilla, Long codigo);
    List<SumillaDTO> findSumillaById(Long codigo);

}

