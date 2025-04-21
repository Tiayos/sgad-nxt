package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDocumentosExternosDTO;
import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import ec.edu.ups.sgadnuxt.entity.model.BitacoraDocumentosExternosModel;
import ec.edu.ups.sgadnuxt.entity.model.SumillaModel;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthMail;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import ec.edu.ups.sgadnuxt.exception.NotFoundException;
import ec.edu.ups.sgadnuxt.repository.IBitacoraDocumentosExternosDao;
import ec.edu.ups.sgadnuxt.repository.IGthMailDao;
import ec.edu.ups.sgadnuxt.repository.ISumillaDao;
import ec.edu.ups.sgadnuxt.service.IBitacorasDocumentosExternosService;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BitacoraDocumentosExternosImpl implements IBitacorasDocumentosExternosService {

    private static final Logger log = LoggerFactory.getLogger(BitacoraDocumentosExternosImpl.class);

    private IBitacoraDocumentosExternosDao iBitacoraDocumentosExternosDao;
    @Autowired
    private ISumillaDao iSumillaDao;
    @Autowired
    private IGthMailDao iGthMailDao;

    public BitacoraDocumentosExternosImpl(IBitacoraDocumentosExternosDao iBitacoraDocumentosExternosDao) {
        this.iBitacoraDocumentosExternosDao = iBitacoraDocumentosExternosDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitacoraDocumentosExternosDTO> findAllBitacorasExternos() {
        return iBitacoraDocumentosExternosDao.findAllBitacorasDocExternos()
                .stream()
                .map(BitacoraDocumentosExternosDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitacoraDocumentosExternosDTO> findAllBitacorasExternosBySede(Long sede) {
        return iBitacoraDocumentosExternosDao.findAllBitacorasDocExternosBySede(sede)
                .stream()
                .map(BitacoraDocumentosExternosDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitacoraDocumentosExternosDTO> findAllBitacorasExternosByPerCodigoDestinatario(Long perCodigoDestinatario) {
        return iBitacoraDocumentosExternosDao.findAllBitacorasExternasByPerCodigoDestinatario(perCodigoDestinatario)
                .stream()
                .map(BitacoraDocumentosExternosDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BitacoraDocumentosExternosDTO findBitacorasExterna(Long codigo) {
        try {
            return BitacoraDocumentosExternosDTO.toDTO(iBitacoraDocumentosExternosDao.findById(codigo).get());
        } catch (Exception e) {
            log.error("Error al buscar bitácora externa con código: {}", codigo, e);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BitacoraDocumentosExternosDTO findBitacoraBySumilla(String numSumilla, String codigo) {
        try {
            return BitacoraDocumentosExternosDTO.toDTO(iBitacoraDocumentosExternosDao.findBitacorasElectronicasBySumilla(numSumilla, codigo));
        } catch (Exception e) {
            log.error("Error al buscar bitácora por sumilla: {} y código: {}", numSumilla, codigo, e);
            return null;
        }
    }

    @Override
    @Transactional
    public BitacoraDocumentosExternosDTO saveBitacorasExternos(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
        try {
            BitacoraDocumentosExternosModel bitacoraDocumentosExternosModel = iBitacoraDocumentosExternosDao.save(new BitacoraDocumentosExternosModel(bitacoraDocumentosExternosDTO));
            return BitacoraDocumentosExternosDTO.toDTO(bitacoraDocumentosExternosModel);
        } catch (PersistenceException e) {
            log.error("Error al guardar bitácora externa: {}", bitacoraDocumentosExternosDTO, e);
            throw new RuntimeException("No se pudo guardar la bitácora externa", e);
        }
    }

    @Override
    @Transactional
    public void updateDocumentosExternos(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO, Long codigo, Long accion, Long sede) {
        try {
            switch (accion.intValue()) {
                case 1:
                    iSumillaDao.findById(bitacoraDocumentosExternosDTO.sumilla().codigo())
                            .map(sumillaMap -> {
                                sumillaMap.setResponsable(new GthPersona(bitacoraDocumentosExternosDTO.responsable().codigo()));
                                return SumillaDTO.toDTO(iSumillaDao.save(sumillaMap));
                            }).orElseThrow(
                                    () -> new NotFoundException("No se encontró la persona con codigo: ".concat(codigo.toString()))
                            );

                    GthMail gthMail = iGthMailDao.getMailByPerCodigo(bitacoraDocumentosExternosDTO.responsable().codigo());

                    iBitacoraDocumentosExternosDao.findById(codigo)
                            .map(bitacoraExternaMap -> {
                                bitacoraExternaMap.setEstado(bitacoraDocumentosExternosDTO.estado());
                                bitacoraExternaMap.setModificado(gthMail.getMaiDireccion());
                                bitacoraExternaMap.setDestinatario(bitacoraDocumentosExternosDTO.destinatario() != null ? new GthPersona(bitacoraDocumentosExternosDTO.destinatario().codigo()) : null);
                                return BitacoraDocumentosExternosDTO.toDTO(iBitacoraDocumentosExternosDao.save(bitacoraExternaMap));
                            }).orElseThrow(
                                    () -> new NotFoundException("No se encontró la bitácora externa con número: ".concat(codigo.toString()))
                            );
                    break;
                case 2:
                    iSumillaDao.findById(bitacoraDocumentosExternosDTO.sumilla().codigo())
                            .map(sumillaMap -> {
                                sumillaMap.setResponsable(new GthPersona(bitacoraDocumentosExternosDTO.responsable().codigo()));
                                sumillaMap.setSumSede(sede);
                                return SumillaDTO.toDTO(iSumillaDao.save(sumillaMap));
                            }).orElseThrow(
                                    () -> new NotFoundException("No se encontró la persona con codigo: ".concat(codigo.toString()))
                            );

                    gthMail = iGthMailDao.getMailByPerCodigo(bitacoraDocumentosExternosDTO.responsable().codigo());
                    iBitacoraDocumentosExternosDao.findById(codigo)
                            .map(bitacoraExternaMap -> {
                                bitacoraExternaMap.setEstado(bitacoraDocumentosExternosDTO.estado());
                                bitacoraExternaMap.setModificado(gthMail.getMaiDireccion());
                                return BitacoraDocumentosExternosDTO.toDTO(iBitacoraDocumentosExternosDao.save(bitacoraExternaMap));
                            }).orElseThrow(
                                    () -> new NotFoundException("No se encontró la bitácora externa con número: ".concat(codigo.toString()))
                            );
                    break;
                case 3:
                    gthMail = iGthMailDao.getMailByPerCodigo(bitacoraDocumentosExternosDTO.responsable().codigo());
                    iBitacoraDocumentosExternosDao.findById(codigo)
                            .map(bitacoraExternaMap -> {
                                bitacoraExternaMap.setEstado(bitacoraDocumentosExternosDTO.estado());
                                bitacoraExternaMap.setModificado(gthMail.getMaiDireccion());
                                bitacoraExternaMap.setObservacion(bitacoraDocumentosExternosDTO.observacion());
                                return BitacoraDocumentosExternosDTO.toDTO(iBitacoraDocumentosExternosDao.save(bitacoraExternaMap));
                            }).orElseThrow(
                                    () -> new NotFoundException("No se encontró la bitácora externa con número: ".concat(codigo.toString()))
                            );
                    break;
            }
        } catch (Exception e) {
            log.error("Error al actualizar documentos externos con código: {}, acción: {}", codigo, accion, e);
            throw new RuntimeException("No se pudo actualizar la bitácora externa", e);
        }
    }

    @Override
    @Transactional
    public void updateBitacoraElectronica(BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
        try {
            GthMail gthMail = iGthMailDao.getMailByPerCodigo(bitacoraDocumentosExternosDTO.destinatario().codigo());
            iBitacoraDocumentosExternosDao.findById(bitacoraDocumentosExternosDTO.codigo())
                    .map(bitacoraExternaMap -> {
                        bitacoraExternaMap.setEstado(bitacoraDocumentosExternosDTO.estado());
                        bitacoraExternaMap.setModificado(gthMail.getMaiDireccion());
                        return BitacoraDocumentosExternosDTO.toDTO(iBitacoraDocumentosExternosDao.save(bitacoraExternaMap));
                    }).orElseThrow(
                            () -> new NotFoundException("No se encontró la bitácora electrónica con número: ".concat(bitacoraDocumentosExternosDTO.codigo().toString()))
                    );
        } catch (Exception e) {
            log.error("Error al actualizar bitácora electrónica con código: {}", bitacoraDocumentosExternosDTO.codigo(), e);
            throw new RuntimeException("No se pudo actualizar la bitácora electrónica", e);
        }
    }

    @Override
    @Transactional
    public void deleteDocumentosExternos(Long codigo) {
        try {
            iBitacoraDocumentosExternosDao.deleteById(codigo);
        } catch (Exception e) {
            log.error("Error al eliminar bitácora externa con código: {}", codigo, e);
            throw new RuntimeException("No se pudo eliminar la bitácora externa", e);
        }
    }
}