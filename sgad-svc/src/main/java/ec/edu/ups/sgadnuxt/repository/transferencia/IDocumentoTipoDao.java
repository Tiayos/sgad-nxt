package ec.edu.ups.sgadnuxt.repository.transferencia;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumentoTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDocumentoTipoDao extends JpaRepository<SgadDocumentoTipo, Long> {
    @Query("select nvl(max(dot.dotCodigo),0) from SgadDocumentoTipo dot")
    Long lastIdDocumentoTipo();
}
