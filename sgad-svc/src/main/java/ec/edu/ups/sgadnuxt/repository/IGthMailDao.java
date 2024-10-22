package ec.edu.ups.sgadnuxt.repository;

import ec.edu.ups.sgadnuxt.entity.model.gth.GthMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IGthMailDao extends JpaRepository<GthMail, Long> {

    @Query("SELECT mail FROM GthMail mail where mail.perCodigo =?1 and mail.maiDireccion like '%ups.edu.ec%'")
    GthMail getMailByPerCodigo(Long perCodigo);

}
