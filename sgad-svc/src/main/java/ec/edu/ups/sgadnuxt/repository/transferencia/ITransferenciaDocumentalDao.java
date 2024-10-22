package ec.edu.ups.sgadnuxt.repository.transferencia;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadTransDocumentales;
import ec.edu.ups.sgadnuxt.projection.GTHPersonaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITransferenciaDocumentalDao extends JpaRepository<SgadTransDocumentales, Long> {

    @Query("select nvl(max(trd.trdCodigo),0) from SgadTransDocumentales trd")
    Long lastIdTransferenciaDocumental();

}
