package ec.edu.ups.sgadnuxt.repository;

import ec.edu.ups.sgadnuxt.entity.model.BitacoraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface IBitacoraDao extends JpaRepository<BitacoraModel, Long> {

    @Query("SELECT bit FROM BitacoraModel bit order by bit.codigo desc")
    List<BitacoraModel> getBitacoras();

    @Query("SELECT bit FROM BitacoraModel bit where bit.sumilla.sumSede=?1 order by bit.codigo desc")
    List<BitacoraModel> getBitacorasBySede(Long sede);

    @Query("SELECT bit FROM BitacoraModel bit where bit.sumilla.numeroSumilla = ?1")
    BitacoraModel getBitacorasByNumSumilla(String numSumilla);

    @Query("SELECT bit FROM BitacoraModel bit where bit.sumilla.codigo = ?1")
    BitacoraModel getBitacoraByCodigoSumilla(Long codigoSumilla);

    @Query("SELECT bit FROM BitacoraModel bit, SgadEventoBitacora ebi where ebi.bitacoraModel.codigo = bit.codigo and bit.destinatario.perCodigo=?1 and ebi.ebiCodigo=5")
    List<BitacoraModel> getBitacoraByPerCodigoDestinatario(Long perCodigoDestinatario);

    @Query(nativeQuery = true, value = "select emp.emp_codigo \n" +
            "            from gth.gth_persona per, \n" +
            "                 gth.gth_contrato con, \n" +
            "                 gth.gth_ambito_servicio ams, \n" +
            "                 gth.gth_empresa emp \n" +
            "            where per.per_codigo=con.per_codigo \n" +
            "            and con.ams_codigo=ams.ams_codigo \n" +
            "            and ams.emp_codigo=emp.emp_codigo \n" +
            "            and per.per_codigo= ?1 \n" +
            "            FETCH FIRST 1 ROWS ONLY")
    Long getEmpCodigoByPerCodigo(Long perCodigo);


    @Query("DELETE FROM BitacoraModel bita where bita.sumilla.codigo= ?1")
    void deleteByCodigoSumilla(Long sumCodigo);

    @Query("SELECT bita from BitacoraModel bita where bita.sumilla.responsable.perCodigo =?3 and  bita.estadoTransferencia='N' and bita.fechaRecepcion BETWEEN ?1 and ?2")
    List<BitacoraModel> getBitacorasByFechasEstadoResCodigo(LocalDate fechaInicio, LocalDate fechaFin, Long resPerCodigo);

    @Query("SELECT bita from BitacoraModel bita where bita.estadoTransferencia='N' and bita.fechaRecepcion BETWEEN ?1 and ?2 and bita.sumilla.responsable.perCodigo = ?3 ")
    List<BitacoraModel> getBitacorasByFechaAndEstado(LocalDate fechaInicio, LocalDate fechaFin, Long resPerCodigo);
}
