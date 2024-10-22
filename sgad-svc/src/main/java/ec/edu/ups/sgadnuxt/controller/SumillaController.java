package ec.edu.ups.sgadnuxt.controller;
import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
import ec.edu.ups.sgadnuxt.service.ISumillaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
@RestController
@RequestMapping("/api/v1/sumilla")
public class SumillaController {

    private ISumillaService sumillaService;

    public SumillaController(ISumillaService sumillaService) {
        this.sumillaService = sumillaService;
    }

    @GetMapping
    public ResponseEntity<?> findAllSumillas(){
        return ResponseEntity.ok(this.sumillaService.findAllSumilla());
    }

    @GetMapping("/validateUser")
    public ResponseEntity<?> validateUser(){
        return ResponseEntity.ok("Auth");
    }

    @GetMapping("/findSumillasBySede")
    public ResponseEntity<?> findAllSumillasBySede(@RequestParam Long sede){
        return ResponseEntity.ok(this.sumillaService.findAllSumillaBySede(sede));
    }

    @GetMapping("/getSedeByEmail")
    public ResponseEntity<?> getSedeByEmail(@RequestParam String email){
        return ResponseEntity.ok(this.sumillaService.getSedeByEmail(email));
    }

    @GetMapping("/findSumillaByNumSumilla")
    public ResponseEntity<?> findSumillaByNumeroSumilla(@RequestParam String numeroSumilla){
        SumillaDTO sumillaDTO = sumillaService.findSumillaByNumeroSumilla(numeroSumilla);
        return sumillaDTO != null ? ResponseEntity.ok(sumillaDTO) : null;
    }

    @PostMapping
    public ResponseEntity<?> saveSumillas(@RequestBody SumillaDTO sumillaDTO, @RequestParam String email){
        return ResponseEntity.ok(this.sumillaService.saveSumilla(sumillaDTO, email));
    }

    @PostMapping("/saveSumillaExt")
    public ResponseEntity<?> saveSumillasExternas(@RequestBody SumillaDTO sumillaDTO){
        return ResponseEntity.ok(this.sumillaService.saveSumillaExterna(sumillaDTO));
    }

    @PutMapping
    public ResponseEntity<?> updateSumillas(@RequestBody SumillaDTO sumillaDTO, @RequestParam Long codigo) {
        return ResponseEntity.ok(this.sumillaService.updateSumilla(sumillaDTO, codigo));
    }

    @DeleteMapping
    public void deleteSumilla(@RequestParam Long codigo){
        sumillaService.deleteSumilla(codigo);
    }

}
