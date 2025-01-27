package ec.edu.ups.sgadnuxt.controller;
import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.DocumentosBitacoraDTO;
import ec.edu.ups.sgadnuxt.service.IBitacoraService;
import ec.edu.ups.sgadnuxt.service.IDocumentoBitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
@RestController
@RequestMapping("/api/v1/bitacora")
public class BitacoraController {

    private IBitacoraService bitacoraService;

    @Autowired
    private IDocumentoBitacoraService iDocumentoBitacoraService;

    public BitacoraController(IBitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    @GetMapping
    public ResponseEntity<?> findAllBitacoras(){
        return ResponseEntity.ok(this.bitacoraService.findAllBitacora());
    }

    @GetMapping("/getBitacorasBySede")
//    @PreAuthorize("hasRole('recepcionist')")
    public ResponseEntity<?> findAllBitacorasBySede(@RequestParam Long sede){
        System.out.println("hola");
        return ResponseEntity.ok(this.bitacoraService.findAllBitacorasBySede(sede));
    }

    @GetMapping("/getBitacorasByFechasAndEstado")
    public ResponseEntity<?> findAllBitacorasByFechaAndEstado(@RequestParam String fechaInicio, @RequestParam String fechaFin, @RequestParam Long resPerCodigo){
        return ResponseEntity.ok(this.bitacoraService.findAllBitacorasByFechasAndEstado(fechaInicio, fechaFin, resPerCodigo));
    }

    @GetMapping("/getBitacoraByNumSumilla")
    public ResponseEntity<?> findBitacoraByNumSumilla(@RequestParam String numSumilla){
        return ResponseEntity.ok(this.bitacoraService.findBitacoraByNumSumilla(numSumilla));
    }

    @GetMapping("/getBitacorasByDestinatario")
    public ResponseEntity<?> findBitacoraByNumSumilla(@RequestParam Long perCodigoDestinatario){
        return ResponseEntity.ok(this.bitacoraService.getBitacorasByPerCodigoDestinatario(perCodigoDestinatario));
    }

    @PostMapping
    public ResponseEntity<?> saveBitacoras(@RequestBody BitacoraDTO bitacoraDTO){
       return ResponseEntity.ok(this.bitacoraService.saveBitacora(bitacoraDTO));
    }

    @PutMapping
    public void updateBitacoras(@RequestBody BitacoraDTO bitacoraDTO, @RequestParam Long codigo) {
         this.bitacoraService.updateBitacora(bitacoraDTO, codigo);
    }

    @PutMapping("/updateEstadoEnvioDestinatario")
    public void updateBitacora(@RequestBody BitacoraDTO bitacoraDTO) {
        this.bitacoraService.updateEstadoEnvioBitacora(bitacoraDTO);
    }

    @DeleteMapping
    public void deleteBitacora(@RequestParam Long codigo){
        this.bitacoraService.deleteBitacora(codigo);
    }

    @DeleteMapping("/deleteBitacoraBySumCodigo")
    public void deleteByCodigoSumilla(@RequestParam Long sumCodigo){
        this.bitacoraService.deleteByCodigoSumilla(sumCodigo);
    }

    @PostMapping("/saveDocumentos")
    public void saveDocumentos(@RequestBody DocumentosBitacoraDTO documentosBitacoraDTO){
        this.iDocumentoBitacoraService.saveDocumentoBitacora(documentosBitacoraDTO);
    }
    @GetMapping("/getDocumentosByBitCodigo")
    public ResponseEntity<?> findDocumentosByBitCodigo(@RequestParam Long bitCodigo){
        return ResponseEntity.ok(this.iDocumentoBitacoraService.findAllDocumentosByBitCodigo(bitCodigo));
    }

    @GetMapping("/getDocsRespuestaByBitCodigo")
    public ResponseEntity<?> findDocsRespuestaTramiteByBitCodigo(@RequestParam Long bitCodigo){
        return ResponseEntity.ok(this.iDocumentoBitacoraService.findAllDocumentosRespuestaBitacoraByBitCodigo(bitCodigo));
    }

    @DeleteMapping("/deleteDocumentos")
    public void deleteDocumentosByBitCodigo(@RequestParam Long bitCodigo){
        this.iDocumentoBitacoraService.deleteDocumentosByBitCodigo(bitCodigo);
    }
}
