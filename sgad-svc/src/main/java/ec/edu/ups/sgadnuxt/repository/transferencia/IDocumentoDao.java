package ec.edu.ups.sgadnuxt.repository.transferencia;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDocumentoDao extends JpaRepository<SgadDocumento, Long> {
    @Query("select nvl(max(doc.docCodigo),0) from SgadDocumento doc")
    Long lastIdDocumento();
}
