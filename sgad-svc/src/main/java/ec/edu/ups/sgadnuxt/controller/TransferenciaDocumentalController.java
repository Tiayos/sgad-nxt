package ec.edu.ups.sgadnuxt.controller;
import ec.edu.ups.sgadnuxt.service.ITransferenciaDocumentalService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type"})
@RestController
@RequestMapping("/api/v1/transferencia")
public class TransferenciaDocumentalController {

    private ITransferenciaDocumentalService iTransferenciaDocumentalService;

    public TransferenciaDocumentalController(ITransferenciaDocumentalService iTransferenciaDocumentalService) {
        this.iTransferenciaDocumentalService = iTransferenciaDocumentalService;
    }

    @PostMapping
    public void saveTransferencia(@RequestParam String fechaInicio,
                                  @RequestParam String fechaFin,
                                  @RequestParam Long perCodigoDestinatarioGW,
                                  @RequestParam Long perCodigoResponsable){
        this.iTransferenciaDocumentalService.saveTransferenciaDocumental(fechaInicio, fechaFin, perCodigoDestinatarioGW, perCodigoResponsable);
    }

}
