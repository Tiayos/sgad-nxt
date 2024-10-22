package ec.edu.ups.sgadnuxt.repository;

import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumentosExternosModal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface IDocumentosExternosDao extends JpaRepository<SgadDocumentosExternosModal, Long> {

    @Query("select doe from SgadDocumentosExternosModal doe order by doe.doeCodigo desc")
    List<SgadDocumentosExternosModal> findAllDocumentosExternos();

    @Query("select doe from SgadDocumentosExternosModal doe where doe.bitacoraDocumentosExternosModel.codigo =?1 order by doe.doeCodigo desc")
    List<SgadDocumentosExternosModal> findAllDocumentosExternosByBidCodigo(Long bidCodigo);

    @Query("select doe from SgadDocumentosExternosModal doe where doe.bitacoraDocumentosExternosModel.codigo =?1 and doe.estadoDocumentoElectronico = 'E' order by doe.doeCodigo desc")
    List<SgadDocumentosExternosModal> findAllDocumentosExternosByBidCodigoRecibido(Long bidCodigo);

    @Query("select doe from SgadDocumentosExternosModal doe where doe.bitacoraDocumentosExternosModel.codigo =?1 and doe.estadoDocumentoElectronico = 'R' order by doe.doeCodigo desc")
    List<SgadDocumentosExternosModal> findAllDocumentosExternosByBidCodigoRespuesta(Long bidCodigo);

    @Query("select doe from SgadDocumentosExternosModal doe where doe.bitacoraDocumentosExternosModel.sumilla.numeroSumilla=?1 and doe.bitacoraDocumentosExternosModel.codigoConsulta=?2 and doe.estadoDocumentoElectronico='R' order by doe.doeCodigo desc")
    List<SgadDocumentosExternosModal> finDocumentosByNumSumillaAndCodigoAleatorio(String numSumilla, String codDocumento);
}
