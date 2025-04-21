package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import ec.edu.ups.sgadnuxt.entity.model.SumillaModel;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import ec.edu.ups.sgadnuxt.exception.NotFoundException;
import ec.edu.ups.sgadnuxt.projection.SedeProjection;
import ec.edu.ups.sgadnuxt.repository.ISumillaDao;
import ec.edu.ups.sgadnuxt.service.ISumillaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SumillaImpl implements ISumillaService {

    private static final Logger log = LoggerFactory.getLogger(SumillaImpl.class);

    private ISumillaDao sumillaDao;

    public SumillaImpl(ISumillaDao sumillaDao) {
        this.sumillaDao = sumillaDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SumillaDTO> findAllSumilla() {
        return sumillaDao.getSumillas()
                .stream()
                .map(SumillaDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SumillaDTO> findAllSumillaBySede(Long sede) {
        return sumillaDao.getSumillasBySede(sede)
                .stream()
                .map(SumillaDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SedeProjection getSedeByEmail(String email) {
        try {
            return sumillaDao.ObtenerSedeByEmail(email);
        } catch (Exception e) {
            log.error("Error al obtener sede por email: {}", email, e);
            throw new RuntimeException("No se pudo obtener la sede por email", e);
        }
    }

    @Override
    @Transactional
    public SumillaDTO saveSumilla(SumillaDTO sumillaDTO, String email) {
        try {
            SedeProjection sedeProjection = sumillaDao.ObtenerSedeByEmail(email);
            String numSumilla = "0";

            SumillaDTO sumillaDTO1 = new SumillaDTO(
                    sumillaDTO.codigo(),
                    sumillaDTO.fechaSumilla(),
                    sumillaDTO.horaSumilla(),
                    sumillaDTO.responsable(),
                    sumillaDTO.numeroHojas(),
                    numSumilla,
                    0L,
                    sedeProjection.getDeeCodigo(),
                    null
            );
            return SumillaDTO.toDTO(sumillaDao.save(new SumillaModel(sumillaDTO1)));
        } catch (Exception e) {
            log.error("Error al guardar sumilla: {}", sumillaDTO, e);
            throw new RuntimeException("No se pudo guardar la sumilla", e);
        }
    }

    @Override
    @Transactional
    public SumillaDTO saveSumillaExterna(SumillaDTO sumillaDTO) {
        try {
            String numSumilla = "";
            Long ultimoIdSumilla = sumillaDao.obtenerUltimoIdSumillaExterna(sumillaDTO.sumSede()) + 1;
            Long sumCodSedeExt = sumillaDao.secuencialEstructuraExt(sumillaDTO.sumSede());

            if (sumillaDTO.sumSede() == 2) { // Cuenca
                numSumilla = "00" + sumCodSedeExt + "-00" + ultimoIdSumilla + "-EXT-UPSCUE";
            } else if (sumillaDTO.sumSede() == 3) { // Quito
                numSumilla = "00" + sumCodSedeExt + "-00" + ultimoIdSumilla + "-EXT-UPSUIO";
            } else if (sumillaDTO.sumSede() == 4) { // Guayaquil
                numSumilla = "00" + sumCodSedeExt + "-00" + ultimoIdSumilla + "-EXT-UPSGYE";
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
        } catch (Exception e) {
            log.error("Error al guardar sumilla externa: {}", sumillaDTO, e);
            throw new RuntimeException("No se pudo guardar la sumilla externa", e);
        }
    }

    @Override
    @Transactional
    public SumillaDTO updateSumilla(SumillaDTO sumilla, Long codigo) {
        try {
            return sumillaDao.findById(codigo)
                    .map(sumillaMap -> {
                        sumillaMap.setFechaSumilla(sumilla.fechaSumilla());
                        sumillaMap.setHoraSumilla(sumilla.horaSumilla());
                        sumillaMap.setResponsable(new GthPersona(sumilla.responsable().codigo()));
                        sumillaMap.setNumeroHojas(sumilla.numeroHojas());
                        return SumillaDTO.toDTO(sumillaDao.save(sumillaMap));
                    })
                    .orElseThrow(() -> new NotFoundException("No se encontró el caso con número: " + codigo));
        } catch (Exception e) {
            log.error("Error al actualizar sumilla con código: {}", codigo, e);
            throw new RuntimeException("No se pudo actualizar la sumilla", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SumillaDTO findSumillaByNumeroSumilla(String numeroSumilla) {
        try {
            SumillaModel sumillaModel = sumillaDao.getSumillaModelByNumeroSumilla(numeroSumilla);
            return sumillaModel != null ? SumillaDTO.toDTO(sumillaModel) : null;
        } catch (Exception e) {
            log.error("Error al buscar sumilla por número: {}", numeroSumilla, e);
            throw new RuntimeException("No se pudo buscar la sumilla por número", e);
        }
    }

    @Override
    @Transactional
    public void deleteSumilla(Long id) {
        try {
            sumillaDao.deleteById(id);
        } catch (Exception e) {
            log.error("Error al eliminar sumilla con id: {}", id, e);
            throw new RuntimeException("No se pudo eliminar la sumilla", e);
        }
    }
}