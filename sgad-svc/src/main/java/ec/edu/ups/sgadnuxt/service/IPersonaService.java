package ec.edu.ups.sgadnuxt.service;


import ec.edu.ups.sgadnuxt.entity.dto.PersonaDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPersonaService {
    List<PersonaDTO> findAllPersona();
    List<PersonaDTO> findAllPersonasByName(String name);
    List<PersonaDTO> findAllPersonasGestionDocumentalByAmbitos();
    List<PersonaDTO> findPersonaByEmail(String email);

}

