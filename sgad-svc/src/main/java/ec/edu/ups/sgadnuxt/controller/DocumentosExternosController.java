package ec.edu.ups.sgadnuxt.controller;
import ec.edu.ups.sgadnuxt.entity.dto.DocumentosExternosDTO;
import ec.edu.ups.sgadnuxt.service.IDocumentosExternosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
@RestController
@RequestMapping("/api/v1/documentosExternos")
public class DocumentosExternosController {

    private IDocumentosExternosService iDocumentosExternosService;

    public DocumentosExternosController(IDocumentosExternosService iDocumentosExternosService) {
        this.iDocumentosExternosService = iDocumentosExternosService;
    }

    @GetMapping
    public ResponseEntity<?> findAllDocumentosExternos(){
        return ResponseEntity.ok(this.iDocumentosExternosService.findAllDocumentosExternos());
    }

    @GetMapping("/getDocumentById")
    public ResponseEntity<?> findDocumentById(@RequestParam Long codigo){
        return ResponseEntity.ok(this.iDocumentosExternosService.findDocumentoExternoByCodigo(codigo));
    }

    @GetMapping("/getDocumentsByBidCodigo")
    public ResponseEntity<?> findDocumentByBidCodigo(@RequestParam Long bidCodigo){
        return ResponseEntity.ok(this.iDocumentosExternosService.findDocumentosExternosByBidCodigo(bidCodigo));
    }

    @GetMapping("/getDocumentsByBidCodigoRecibidos")
    public ResponseEntity<?> findDocumentByBidCodigoRecibidos(@RequestParam Long bidCodigo){
        return ResponseEntity.ok(this.iDocumentosExternosService.findDocumentosExternosByBidCodigoRecibido(bidCodigo));
    }

    @GetMapping("/getDocumentsByBidCodigoRespuesta")
    public ResponseEntity<?> findDocumentByBidCodigoRespuesta(@RequestParam Long bidCodigo){
        return ResponseEntity.ok(this.iDocumentosExternosService.findDocumentosExternosByBidCodigoRespuesta(bidCodigo));
    }

    @GetMapping("/obtenerDocumentosElectronicosPorCodigoConsulta")
    public ResponseEntity<?> findDocumentByNumSumilla(@RequestParam String numSumilla, @RequestParam String codDocumento){
        return ResponseEntity.ok(this.iDocumentosExternosService.obtenerDocumentosElectronicosPorCodigoConsulta(numSumilla, codDocumento));
    }

    @PostMapping
    public ResponseEntity<?> saveDocumentosExternos(@RequestBody DocumentosExternosDTO documentosExternosDTO){
       return ResponseEntity.ok(this.iDocumentosExternosService.saveDocumentosExternos(documentosExternosDTO));
    }

    @DeleteMapping
    public void deleteDocumentosExternos(@RequestParam Long codigo){
        this.iDocumentosExternosService.deleteDocumentosExternosByDoeCodigo(codigo);
    }

}
