package ec.edu.ups.sgadnuxt.repository;

import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadDocumentoBitacora;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDocumentoBitacoraDao extends JpaRepository<SgadDocumentoBitacora, Long> {

    @Query("select doc from SgadDocumentoBitacora doc where doc.bitacoraModel.codigo = ?1 and (doc.estadoTramite = 'C' or doc.estadoTramite is null )")
    List<SgadDocumentoBitacora> findAllDocumentosByBitCodigo(Long bitCodigo);

    @Query("select doc from SgadDocumentoBitacora doc where doc.bitacoraModel.codigo = ?1 and doc.estadoTramite = 'R'")
    List<SgadDocumentoBitacora> findAllDocsRespuestaTramiteByBitCodigo(Long bitCodigo);
    @Query("select doc from BitacoraModel bit, " +
            "SgadDocumentoBitacora doc, " +
            "SgadEventoBitacora eve " +
            "where bit.codigo = eve.bitacoraModel.codigo " +
            "and bit.codigo = doc.bitacoraModel.codigo " +
            "and eve.sgadEstado.estCodigo = 2 " +
            "and eve.ebiVigencia='S' ")
    List<SgadDocumentoBitacora> validarDocumentosEliminar();

    @Transactional
    @Modifying
    @Query("DELETE FROM SgadDocumentoBitacora doc WHERE doc.bitacoraModel.codigo = ?1 ")
    void deleteEventoByBitCodigo(Long bitCodigo);
}
