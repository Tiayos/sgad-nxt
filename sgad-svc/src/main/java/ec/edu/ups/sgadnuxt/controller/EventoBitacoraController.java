package ec.edu.ups.sgadnuxt.controller;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EventoBitacoraDTO;
import ec.edu.ups.sgadnuxt.service.IEventoBitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
@RestController
@RequestMapping("/api/v1/eventoBitacora")
public class EventoBitacoraController {

    @Autowired
    private IEventoBitacoraService iEventoBitacoraService;

    @GetMapping
    public ResponseEntity<?> findAllEventos(){
        return ResponseEntity.ok(this.iEventoBitacoraService.findAllEventosBitacoras());
    }
//* metodo que devuelve TODOS los eventos segun percodigo y vigencia 'S'
    @GetMapping("/getEventosByPerCodigo")
    public ResponseEntity<?> findAllEventosVigentesByPerCodigo(@RequestParam Long perCodigo){
        return ResponseEntity.ok(this.iEventoBitacoraService.findEventosByPerCodigo(perCodigo));
    }

    //* método que devuelve los eventos segun el perCodigoRecepcionReasignado y vigencia 'S'
    @GetMapping("/getEventosByPerCodigoRecepcion")
    public ResponseEntity<?> findAllEventosByPerCodigoRecepcion(@RequestParam Long perCodigoRecepcion){
        return ResponseEntity.ok(this.iEventoBitacoraService.findAllEventosByPerCodigoRecepcionReasignado(perCodigoRecepcion));
    }

//* metodo que devuelve el evento que esté con estado en S segun el codigo de la bitacora
    @GetMapping("/getEventosCodBitacoraVigente")
    public ResponseEntity<?> findAllEventosByBitCodigo(@RequestParam Long bitCodigo){
        return ResponseEntity.ok(this.iEventoBitacoraService.findEventosByBitCodigo(bitCodigo));
    }
//* metodo que devuelve TODOS los eventos SIN IMPORTAR el estado_vigencia

    @GetMapping("/getAllEventos")
    public ResponseEntity<?> findAllEventos(@RequestParam Long bitCodigo){
        return ResponseEntity.ok(this.iEventoBitacoraService.findAllEventosByBitCodigo(bitCodigo));
    }

//* metodo que devuelve TODOS los eventos SIN IMPORTAR el estado_vigencia
    @GetMapping("/getAllEstados")
    public ResponseEntity<?> findAllEstados(){
        return ResponseEntity.ok(this.iEventoBitacoraService.findAllEstados());
    }

    @PostMapping
    public void saveEventoBitacora(@RequestBody EventoBitacoraDTO eventoBitacoraDTO){
        this.iEventoBitacoraService.saveEventoBitacora(eventoBitacoraDTO);
    }

    @PutMapping
    public void updateEventoBitacora(@RequestBody EventoBitacoraDTO eventoBitacoraDTO){
        this.iEventoBitacoraService.updateEventoBitacora(eventoBitacoraDTO);
    }

    @DeleteMapping
    public void deleteByCodigoSumilla(@RequestParam Long bitCodigo){
        this.iEventoBitacoraService.deleteEventoByBitCodigo(bitCodigo);
    }

}
