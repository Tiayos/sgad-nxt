package ec.edu.ups.sgadnuxt.controller;
import ec.edu.ups.sgadnuxt.service.IPersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
@RestController
@RequestMapping("/api/v1/persona")
public class PersonaController {

    private IPersonaService personaService;

    public PersonaController(IPersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public ResponseEntity<?> findAllPersonas(){
        return ResponseEntity.ok(this.personaService.findAllPersona());
    }

    @GetMapping("/getFilterName")
    public ResponseEntity<?> findAllPersonasByName(@RequestParam String name){
        return ResponseEntity.ok(this.personaService.findAllPersonasByName(name));
    }

    @GetMapping("/findPersonasGestionDocumental")
    public ResponseEntity<?> findAllPersonasGestionDocumentalByAmbitos(){
        return ResponseEntity.ok(this.personaService.findAllPersonasGestionDocumentalByAmbitos());
    }

    @GetMapping("/findEmail")
        public ResponseEntity<?> findAllPersonasByEmail(@RequestParam String email){
        return ResponseEntity.ok(this.personaService.findPersonaByEmail(email).get(0));
    }

}
