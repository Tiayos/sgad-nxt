package ec.edu.ups.sgadnuxt.repository;

import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import ec.edu.ups.sgadnuxt.projection.GTHPersonaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface IPersonaDao extends JpaRepository<GthPersona, Long> {

    @Query("select per from GthPersona per, GthMail mai where per.perCodigo = mai.perCodigo and mai.maiDireccion = ?1")
    List<GthPersona> findPersonaByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT p.per_codigo as PERCODIGO, gmai.mai_direccion as MAIL, p.per_nro_identificacion as PERNUMIDENTIFICACION, p.per_apellidos as PERAPELLIDOS, p.per_nombres as PERNOMBRES, ca.car_codigo as CARGOCODIGO, ca.car_descripcion as CARGODESCRIPCION, am.amb_codigo as CODIGOAMBITO, a.amb_descripcion as DESCAMBITO, e.emp_codigo as EMPCODIGO, e.emp_nombre as EMPNOMBRE FROM gth.gth_contrato c, gth.gth_persona p, gth.gth_cargo ca, gth.gth_ambito_servicio am, gth.gth_ambito a, gth.gth_empresa e, gth.gth_empleado_empresa ep, gth.gth_cargo_empresa ce, gth.gth_mail gmai WHERE c.per_codigo = p.per_codigo AND p.per_codigo = gmai.per_codigo AND c.car_codigo = ca.car_codigo AND c.ams_codigo = am.ams_codigo AND am.amb_codigo = a.amb_codigo AND c.con_estado = 'A' AND p.per_eliminado = 'N' AND p.per_codigo = ep.per_codigo AND ca.car_codigo = ce.car_codigo AND gmai.mai_direccion LIKE '%ups.edu.ec%' AND e.emp_codigo = c.emp_codigo_ubicacion AND c.per_codigo = ?1")
    GTHPersonaProjection getGthPersonaAmbitoByPerCodigo(Long perCodigo);
    @Query(nativeQuery = true, value = "SELECT p.*\n" +
            "                         FROM   \n" +
            "                         gth.gth_contrato c,    \n" +
            "                         gth.gth_persona p,   \n" +
            "                         gth.gth_cargo ca,       \n" +
            "                         gth.gth_ambito_servicio am,    \n" +
            "                         gth.gth_ambito a,\n" +
            "                         gth.gth_empresa e,\n" +
            "                         gth.gth_empleado_empresa ep,\n" +
            "                         gth.gth_cargo_empresa ce\n" +
            "                         WHERE  c.per_codigo = p.per_codigo \n" +
            "                         AND    c.car_codigo = ca.car_codigo\n" +
            "                         AND    c.ams_codigo = am.ams_codigo\n" +
            "                         AND   am.amb_codigo = a.amb_codigo \n" +
            "                         AND    c.con_estado = 'A' \n" +
            "                         and   p. per_eliminado='N' \n" +
            "                         and p.per_codigo = ep.per_codigo\n" +
            "                         and ca.car_codigo=ce.car_codigo \n" +
            "                         and e.emp_codigo =  c.emp_codigo_ubicacion \n" +
            "                         and a.amb_codigo in (179,159,157) ")
    List<GthPersona> obtenerPersonasGestionDocumentalByAmbitos();

    @Query("SELECT per FROM GthPersona per WHERE LOWER(per.perNombres) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(per.perApellidos) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<GthPersona> getPersonasByName(String query, Pageable pageable);
}
