package ec.edu.ups.sgadnuxt.service.implementation;

import ec.edu.ups.sgadnuxt.entity.dto.PersonaDTO;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import ec.edu.ups.sgadnuxt.repository.IPersonaDao;
import ec.edu.ups.sgadnuxt.service.IPersonaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonaImpl implements IPersonaService {

    private IPersonaDao personaDao;

    public PersonaImpl(IPersonaDao personaDao) {
        this.personaDao = personaDao;
    }

    @Override
    public List<PersonaDTO> findAllPersona() {
        return personaDao.findAll()
                .stream()
                .map(PersonaDTO::toDTO)
                .toList();
    }

    @Override
    public List<PersonaDTO> findAllPersonasByName(String name) {
        Pageable pageable = PageRequest.of(0, 50);
        Page<GthPersona> personaPage = personaDao.getPersonasByName(name, pageable);
        return personaPage.getContent()
                .stream()
                .map(PersonaDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
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
    public List<PersonaDTO> findPersonaByEmail(String email) {
        List<PersonaDTO> personaDTOList = personaDao.findPersonaByEmail(email).stream().map(PersonaDTO::toDTO).toList();
        if (!personaDTOList.isEmpty()) {
            return personaDTOList;
        } else {
            return null;
        }
    }
}