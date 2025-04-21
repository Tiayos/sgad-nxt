package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.PersonaDTO;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import ec.edu.ups.sgadnuxt.repository.IPersonaDao;
import ec.edu.ups.sgadnuxt.service.IPersonaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaImpl implements IPersonaService {

    private static final Logger log = LoggerFactory.getLogger(PersonaImpl.class);

    private IPersonaDao personaDao;

    public PersonaImpl(IPersonaDao personaDao) {
        this.personaDao = personaDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaDTO> findAllPersona() {
        return personaDao.findAll()
                .stream()
                .map(PersonaDTO::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaDTO> findAllPersonasByName(String name) {
        try {
            Pageable pageable = PageRequest.of(0, 50);
            Page<GthPersona> personaPage = personaDao.getPersonasByName(name, pageable);
            return personaPage.getContent()
                    .stream()
                    .map(PersonaDTO::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error al buscar personas por nombre: {}", name, e);
            throw new RuntimeException("No se pudo buscar personas por nombre", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaDTO> findAllPersonasGestionDocumentalByAmbitos() {
        return personaDao.obtenerPersonasGestionDocumentalByAmbitos()
                .stream()
                .map(PersonaDTO::toDTO)
                .toList();
    }

    public void setPersonaDao(IPersonaDao personaDao) {
        this.personaDao = personaDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonaDTO> findPersonaByEmail(String email) {
        try {
            return personaDao.findPersonaByEmail(email)
                    .stream()
                    .map(PersonaDTO::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error al buscar persona por email: {}", email, e);
            throw new RuntimeException("No se pudo buscar persona por email", e);
        }
    }
}