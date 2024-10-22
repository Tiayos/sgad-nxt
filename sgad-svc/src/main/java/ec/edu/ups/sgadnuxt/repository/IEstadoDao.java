package ec.edu.ups.sgadnuxt.repository;

import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadEstado;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IEstadoDao extends JpaRepository<SgadEstado, Long> {

}
