package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import ec.edu.ups.sgadnuxt.repository.ISumillaDao;
import ec.edu.ups.sgadnuxt.service.ISumillaService;

import java.util.List;

public class SumillaImpl implements ISumillaService {

    private ISumillaDao sumillaDao;

    public SumillaImpl(ISumillaDao sumillaDao) {
        this.sumillaDao = sumillaDao;
    }

    @Override
    public List<SumillaDTO> findAllSumilla() {
        return sumillaDao.findAll()
                .stream()
                .map(SumillaDTO::toDTO)
                .toList();    }

    @Override
    public SumillaDTO saveSumilla(SumillaDTO casoFormulario) {
        return null;
    }

    @Override
    public SumillaDTO updateSumilla(SumillaDTO sumilla, Long codigo) {
        return null;
    }

    @Override
    public List<SumillaDTO> findSumillaById(Long codigo) {
        return null;
    }
}
