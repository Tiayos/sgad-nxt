package ec.edu.ups.sgadnuxt.repository;

import ec.edu.ups.sgadnuxt.entity.model.SumillaModel;
import ec.edu.ups.sgadnuxt.projection.SedeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ISumillaDao extends JpaRepository<SumillaModel, Long> {

@Query(nativeQuery = true, value = "SELECT dee.dee_codigo as deeCodigo, dee.dee_descripcion as deeDescripcion \n" +
        "FROM gth.gth_contrato gtc\n" +
        "JOIN gth.gth_int_centro_costo gtcc ON gtc.ams_codigo = gtcc.ams_codigo\n" +
        "JOIN gth.GTH_INT_OFICINA ino ON ino.OFTS_CDG = gtcc.ICC_NIVEL_1\n" +
        "JOIN gth.gth_empresa emp ON ino.emp_codigo = emp.emp_codigo\n" +
        "JOIN gth.gth_int_empresa iem ON iem.emp_codigo = emp.emp_codigo\n" +
        "JOIN org.org_estructura est ON est.est_sed_leg = iem.sed_codigo\n" +
        "JOIN org.org_descripcion_estructura dee ON est.dee_codigo = dee.dee_codigo\n" +
        "WHERE gtc.con_estado = 'A'\n" +
        "AND gtcc.ICC_ELIMINADO = 'N'\n" +
        "AND dee.tie_codigo = 2\n" +
        "AND ROWNUM = 1\n" +
        "AND gtc.per_codigo = (\n" +
        "    SELECT gper.per_codigo\n" +
        "    FROM gth.gth_mail gma\n" +
        "    JOIN gth.gth_persona gper ON gma.per_codigo = gper.per_codigo\n" +
        "    WHERE gma.mai_direccion LIKE ?1\n" +
        ")\n" +
        "ORDER BY gtc.CON_FECHA_ADICION DESC")
    SedeProjection ObtenerSedeByEmail(String email);

//    @Query("select nvl(max(sum.codigo),0) from SumillaModel sum where sum.sumSede = ?1 and sum.numeroSumilla not like '%EXT%' ")
//    Long obtenerUltimoIdSumilla(Long sede);

    @Query("select count(sumi) " +
            "from SumillaModel sumi " +
            "where sumi.sumSede = ?1 and " +
            "sumi.numeroSumilla not like '%EXT%'")
    Long obtenerUltimoIdSumilla(Long sede);

//    @Query("select COALESCE(max(sum.codigo),0) from SumillaModel sum where sum.sumSede = ?1 and sum.numeroSumilla like '%EXT%'")
@Query("select count(sumi) " +
        "from SumillaModel sumi " +
        "where sumi.sumSede = ?1 and " +
        "sumi.numeroSumilla like '%EXT%'")
    Long obtenerUltimoIdSumillaExterna(Long sede);


//SECUENCIAL SEDE PUEDE SER CONSIDERADO NUMERO TRAMITE segun la SEDE
@Query(nativeQuery = true, value = " SELECT  \n" +
        "    NVL(MAX(bitt.bit_numero_tramite),0)+1  \n" +
        "FROM \n" +
        "    sgad.sgad_bitacora bitt, \n" +
        "    sgad.sgad_sumilla sumi \n" +
        "WHERE \n" +
        "    bitt.sum_codigo = sumi.sum_codigo and \n" +
        "    sumi.sum_sede = ?1 and \n" +
        "    sumi.sum_numero_sumilla not like '%EXT%'")
    Long secuencialNumeroTramiteBySede(Long sumCodigo);

@Query(nativeQuery = true, value = "SELECT  \n" +
        "    NVL(MAX(bitt.BIT_SECUENCIAL_DOCUMENTO),0)+1  \n" +
        "FROM \n" +
        "    sgad.sgad_bitacora bitt, \n" +
        "    sgad.sgad_sumilla sumi \n" +
        "WHERE \n" +
        "    bitt.sum_codigo = sumi.sum_codigo and \n" +
        "    sumi.sum_sede = ?1 ")
    Long secuencialNumDocumentoBySedeAndTramite(Long sumCodigo);


    @Query(nativeQuery = true, value = "select count(*)\n" +
            "    from \n" +
            "        sgad.sgad_evento_bitacora ebi, \n" +
            "        sgad.sgad_bitacora bit, \n" +
            "        sgad.sgad_sumilla sumi\n" +
            "    where \n" +
            "        ebi.bit_codigo = bit.bit_codigo and\n" +
            "        bit.sum_codigo = sumi.sum_codigo and\n" +
            "        sumi.sum_codigo = ?1")
    Long completarNumSumilla(Long sumCodigo);

    @Query("SELECT sumilla FROM SumillaModel sumilla order by sumilla.codigo desc")
    List<SumillaModel> getSumillas();

    @Query("SELECT sumilla FROM SumillaModel sumilla where sumilla.sumSede=?1 order by sumilla.codigo desc")
    List<SumillaModel> getSumillasBySede(Long sede);

    @Query("SELECT sml FROM SumillaModel sml  where sml.numeroSumilla=?1 ")
    SumillaModel getSumillaModelByNumeroSumilla(String numeroSumilla);

    @Query(nativeQuery = true, value = "SELECT  \n" +
            "NVL(MAX(sumil.sum_cod_sede),0)+1  \n" +
            "FROM \n " +
            "    sgad.sgad_sumilla sumil  \n" +
            "where  \n" +
            "    sumil.sum_sede = ?1 \n" +
            "and sumil.sum_numero_sumilla not like '%EXT%'")
    Long secuencialEstructura(Long codigoConsultorio);

    @Query(nativeQuery = true, value = "SELECT \n" +
            "    NVL(MAX(sumil.sum_cod_sede),0)+1 \n" +
            "FROM \n" +
            "    sgad.sgad_sumilla sumil \n" +
            "where \n" +
            "    sumil.sum_sede = ?1 \n" +
            "and sumil.sum_numero_sumilla like '%EXT%'")
    Long secuencialEstructuraExt(Long codigoConsultorio);
}
