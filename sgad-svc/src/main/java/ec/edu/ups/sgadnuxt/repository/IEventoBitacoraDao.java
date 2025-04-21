package ec.edu.ups.sgadnuxt.repository;

import ec.edu.ups.sgadnuxt.entity.dto.sgad.EventoBitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadEventoBitacora;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEventoBitacoraDao extends JpaRepository<SgadEventoBitacora, Long> {

    @Query("SELECT ebi FROM BitacoraModel bit, SgadEventoBitacora ebi WHERE bit.codigo = ebi.bitacoraModel.codigo and bit.codigo=?1 and ebi.ebiVigencia='S'")
    SgadEventoBitacora getEventosBitacora(Long bitCodigo);

        @Query(nativeQuery = true, value = "SELECT ebi.* \n" +
                "                                FROM sgad.sgad_bitacora bitt \n" +
                "                                JOIN sgad.sgad_evento_bitacora ebi ON bitt.bit_codigo = ebi.bit_codigo\n" +
                "                                WHERE (\n" +
                "                                    (ebi.per_codigo_reasignado = ?1 \n" +
                "                                     AND ebi.est_codigo not in (2) \n" +
                "                                     AND ebi.ebi_vigencia = 'S')\n" +
                "                                    OR\n" +
                "                                    (bitt.PER_CODIGO_DESTINATARIO = ?1 \n" +
                "                                     AND ebi.est_codigo not in (2) \n" +
                "                                     AND ebi.ebi_vigencia = 'S')\n" +
                "                                )\n" +
                "                                ORDER BY ebi.bit_codigo DESC")
    List<SgadEventoBitacora> getAllEventosByPerCodigo(Long perCodigo);

    @Query("SELECT ebi FROM BitacoraModel bitt, SgadEventoBitacora ebi WHERE bitt.codigo = ebi.bitacoraModel.codigo and bitt.codigoRecepcionReasignado = ?1 and ebi.ebiVigencia = 'S' AND ebi.sgadEstado.estCodigo not in (2) order by ebi.ebiCodigo desc")
    List<SgadEventoBitacora> getEventosByPerCodigoRecepcionReasignado(Long perCodigoRecepcionReasignado);

    @Query("select ebi from SgadEventoBitacora ebi where ebi.bitacoraModel.codigo = ?1 order by ebi.ebiCodigo asc")
    List<SgadEventoBitacora> getAllEventosByBitCodigo(Long bitCodigo);

    //* consultar ultimo evento vigente
    @Query("select ebi from SgadEventoBitacora ebi where ebi.bitacoraModel.codigo = ?1 and ebi.ebiVigencia='S'")
    SgadEventoBitacora getEventoVigente(Long bitCodigo);

    @Transactional
    @Modifying
    @Query("DELETE FROM SgadEventoBitacora ebi WHERE ebi.bitacoraModel.codigo = ?1")
    void deleteEventoByBitCodigo(Long bitCodigo);
}
