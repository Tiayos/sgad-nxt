package ec.edu.ups.sgadnuxt.controller;
import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDocumentosExternosDTO;
import ec.edu.ups.sgadnuxt.service.IBitacorasDocumentosExternosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
@RestController
@RequestMapping("/api/v1/bitacoraExternos")
public class BitacoraDocumentosExternosController {

    private IBitacorasDocumentosExternosService iBitacorasDocumentosExternosService;

    public BitacoraDocumentosExternosController(IBitacorasDocumentosExternosService bitacorasDocumentosExternosService) {
        this.iBitacorasDocumentosExternosService = bitacorasDocumentosExternosService;
    }

    @GetMapping
    public ResponseEntity<?> findAllDocumentosExternos(){
        return ResponseEntity.ok(this.iBitacorasDocumentosExternosService.findAllBitacorasExternos());
    }

    @GetMapping("/getDocumentosExternosBySede")
    public ResponseEntity<?> findAllDocumentosExternosBySede(@RequestParam Long sede){
        return ResponseEntity.ok(this.iBitacorasDocumentosExternosService.findAllBitacorasExternosBySede(sede));
    }

    @GetMapping("/getDocumentosElectronicosByPerCodigo")
    public ResponseEntity<?> findAllBitacorasExternasByPerCodigoDestinatario(@RequestParam Long perCodigoDestinatario){
        return ResponseEntity.ok(this.iBitacorasDocumentosExternosService.findAllBitacorasExternosByPerCodigoDestinatario(perCodigoDestinatario));
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findDocumentoById(@RequestParam Long codigo){
        return ResponseEntity.ok(this.iBitacorasDocumentosExternosService.findBitacorasExterna(codigo));
    }

    @GetMapping("/findBitacoraElectronicaBySumilla")
    public ResponseEntity<?> findDocumentoById(@RequestParam String numSumilla, @RequestParam String codigo){
        return ResponseEntity.ok(this.iBitacorasDocumentosExternosService.findBitacoraBySumilla(numSumilla, codigo));
    }

    @PostMapping
    public ResponseEntity<?> saveDocumentosExternos(@RequestBody BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO){
       return ResponseEntity.ok(this.iBitacorasDocumentosExternosService.saveBitacorasExternos(bitacoraDocumentosExternosDTO));
    }

    @PutMapping
    public void updateDocumentosExternos(@RequestBody BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO, @RequestParam Long codigo, @RequestParam Long accion, @RequestParam Long sede) {
         this.iBitacorasDocumentosExternosService.updateDocumentosExternos(bitacoraDocumentosExternosDTO, codigo, accion, sede);
    }

    @PutMapping("/updateBitElectronica")
    public void updateDocumentosExternos(@RequestBody BitacoraDocumentosExternosDTO bitacoraDocumentosExternosDTO) {
        this.iBitacorasDocumentosExternosService.updateBitacoraElectronica(bitacoraDocumentosExternosDTO);
    }

    @DeleteMapping
    public void deleteDocumentosExternos(@RequestParam Long codigo){
        this.iBitacorasDocumentosExternosService.deleteDocumentosExternos(codigo);
    }

}
