package ec.edu.ups.sgadnuxt.repository;

import ec.edu.ups.sgadnuxt.entity.model.BitacoraDocumentosExternosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface IBitacoraDocumentosExternosDao extends JpaRepository<BitacoraDocumentosExternosModel, Long> {

    @Query("select bid from BitacoraDocumentosExternosModel bid order by bid.codigo desc")
    List<BitacoraDocumentosExternosModel> findAllBitacorasDocExternos();

    @Query("select bid from BitacoraDocumentosExternosModel bid where bid.sumilla.sumSede =?1 order by bid.codigo desc, bid.sumilla.numeroSumilla asc")
    List<BitacoraDocumentosExternosModel> findAllBitacorasDocExternosBySede(Long sede);

    @Query("select bid from BitacoraDocumentosExternosModel bid where bid.destinatario.perCodigo=?1 order by bid.codigo desc")
    List<BitacoraDocumentosExternosModel> findAllBitacorasExternasByPerCodigoDestinatario(Long perCodigoDestinatario);

    @Query("select bid from BitacoraDocumentosExternosModel bid where bid.sumilla.numeroSumilla=?1 and bid.codigoConsulta =?2 order by bid.codigo desc")
    BitacoraDocumentosExternosModel findBitacorasElectronicasBySumilla(String numSumilla, String codigo);

}
