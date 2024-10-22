package ec.edu.ups.sgadnuxt.repository.transferencia;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDetalleTransferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDetalleTransferenciaDao extends JpaRepository<SgadDetalleTransferencia, Long> {
    @Query("select nvl(max(tra.detCodigo),0) from SgadDetalleTransferencia tra")
    Long lastIdDetTransferenciaDocumental();
}
