package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import ec.edu.ups.sgadnuxt.entity.model.SumillaModel;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import ec.edu.ups.sgadnuxt.exception.NotFoundException;
import ec.edu.ups.sgadnuxt.projection.SedeProjection;
import ec.edu.ups.sgadnuxt.repository.ISumillaDao;
import ec.edu.ups.sgadnuxt.service.ISumillaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SumillaImpl implements ISumillaService {

    private ISumillaDao sumillaDao;

    public SumillaImpl(ISumillaDao sumillaDao) {
        this.sumillaDao = sumillaDao;
    }

    @Override
    public List<SumillaDTO> findAllSumilla() {
        return sumillaDao.getSumillas()
                .stream()
                .map(SumillaDTO::toDTO)
                .toList();
    }

    @Override
    public List<SumillaDTO> findAllSumillaBySede(Long sede) {
        return sumillaDao.getSumillasBySede(sede)
                .stream()
                .map(SumillaDTO::toDTO)
                .toList();
    }

    @Override
    public SedeProjection getSedeByEmail(String email) {
       return sumillaDao.ObtenerSedeByEmail(email);
    }

    @Override
    public SumillaDTO saveSumilla(SumillaDTO sumillaDTO, String email) {
        SedeProjection sedeProjection = sumillaDao.ObtenerSedeByEmail(email);
        String numSumilla = "";
        Long ultimoIdSumilla = sumillaDao.obtenerUltimoIdSumilla(sedeProjection.getDeeCodigo()) + 1;

        Long sumCodSede = sumillaDao.secuencialEstructura(sedeProjection.getDeeCodigo());

        if(sedeProjection.getDeeCodigo()== 2){ //cuenca
            numSumilla = "00"+ sumCodSede + "-00"+ 1 + "-UPSCUE";
        } else if (sedeProjection.getDeeCodigo() == 3) { //QUITO
            numSumilla = "00"+ sumCodSede + "-00"+ 1 + "-UPSUIO";
        }else if (sedeProjection.getDeeCodigo() == 4) { //GUAYAQUIL
            numSumilla = "00"+ sumCodSede + "-00"+ 1 + "-UPSGYE";
        }

        SumillaDTO sumillaDTO1 = new SumillaDTO(
                sumillaDTO.codigo(),
                sumillaDTO.fechaSumilla(),
                sumillaDTO.horaSumilla(),
                sumillaDTO.responsable(),
                sumillaDTO.numeroHojas(),
                numSumilla,
                ultimoIdSumilla,
                sedeProjection.getDeeCodigo(),
                sumCodSede
        );
        return SumillaDTO.toDTO(sumillaDao.save(new SumillaModel(sumillaDTO1)));
    }

    @Override
    public SumillaDTO saveSumillaExterna(SumillaDTO sumillaDTO) {
        String numSumilla = "";
        Long ultimoIdSumilla = sumillaDao.obtenerUltimoIdSumillaExterna(sumillaDTO.sumSede()) + 1;

        Long sumCodSedeExt = sumillaDao.secuencialEstructuraExt(sumillaDTO.sumSede());

        if(sumillaDTO.sumSede() == 2){ //cuenca
            numSumilla = "00"+ sumCodSedeExt + "-00"+ ultimoIdSumilla + "-EXT-UPSCUE";
        } else if (sumillaDTO.sumSede() == 3) { //QUITO
            numSumilla = "00"+ sumCodSedeExt + "-00"+ ultimoIdSumilla + "-EXT-UPSUIO";
        }else if (sumillaDTO.sumSede() == 4) { //GUAYAQUIL
            numSumilla = "00"+ sumCodSedeExt + "-00"+ ultimoIdSumilla + "-EXT-UPSGYE";
        }

        SumillaDTO sumillaDTO1 = new SumillaDTO(
                sumillaDTO.codigo(),
                sumillaDTO.fechaSumilla(),
                sumillaDTO.horaSumilla(),
                sumillaDTO.responsable(),
                sumillaDTO.numeroHojas(),
                numSumilla,
                ultimoIdSumilla,
                sumillaDTO.sumSede(),
                sumCodSedeExt
        );
        return SumillaDTO.toDTO(sumillaDao.save(new SumillaModel(sumillaDTO1)));
    }

    @Override
    public SumillaDTO updateSumilla(SumillaDTO sumilla, Long codigo) {
        return sumillaDao.findById(codigo)
                .map(
                        sumillaMap -> {
                            sumillaMap.setFechaSumilla(sumilla.fechaSumilla());
                            sumillaMap.setHoraSumilla(sumilla.horaSumilla());
                            sumillaMap.setResponsable(new GthPersona(sumilla.responsable().codigo()));
                            sumillaMap.setNumeroHojas(sumilla.numeroHojas());
                            return SumillaDTO.toDTO(sumillaDao.save(sumillaMap));
                        }) .orElseThrow(
                        () -> new NotFoundException("No se encontró el caso con número: ".concat(codigo.toString()))
                );
    }

    @Override
    public SumillaDTO findSumillaByNumeroSumilla(String numeroSumilla) {
        SumillaModel sumillaModel = sumillaDao.getSumillaModelByNumeroSumilla(numeroSumilla);
        if(sumillaModel!=null){
            return SumillaDTO.toDTO(sumillaDao.getSumillaModelByNumeroSumilla(numeroSumilla));
        }else{
            return null;
        }
    }
    @Override
    public void deleteSumilla(Long id) {
        sumillaDao.deleteById(id);
    }
}
