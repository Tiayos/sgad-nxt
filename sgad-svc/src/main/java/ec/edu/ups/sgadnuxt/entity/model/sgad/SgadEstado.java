package ec.edu.ups.sgadnuxt.entity.model.sgad;

import ec.edu.ups.sgadnuxt.entity.dto.sgad.EstadoDTO;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "SGAD_ESTADO", schema = "SGAD")
public class SgadEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EST_CODIGO")
    private Long estCodigo;
    @Basic(optional = false)
    @Column(name = "EST_DESCRIPCION")
    private String estDescripcion;
    @Column(name = "AUD_ADICIONADO")
    private String audAdicionado;
    @Column(name = "AUD_ELIMINADO")
    private String audEliminado;
    @Column(name = "AUD_FECHA_ADICION")
    private LocalDateTime audFechaAdicion;
    @Column(name = "AUD_FECHA_MODIFICACION")
    private LocalDateTime audFechaModificacion;
    @Column(name = "AUD_MODIFICADO")
    private String audModificado;

    public SgadEstado() {
    }

    public SgadEstado(Long estCodigo) {
        this.estCodigo = estCodigo;
    }

    public SgadEstado(Long estCodigo, String estDescripcion) {
        this.estCodigo = estCodigo;
        this.estDescripcion = estDescripcion;
    }

    public SgadEstado(EstadoDTO dto){
        this.estCodigo = dto.codigo();
        this.estDescripcion = dto.estDescripcion();
    }

    public Long getEstCodigo() {
        return estCodigo;
    }

    public void setEstCodigo(Long estCodigo) {
        this.estCodigo = estCodigo;
    }

    public String getAudAdicionado() {
        return audAdicionado;
    }

    public void setAudAdicionado(String audAdicionado) {
        this.audAdicionado = audAdicionado;
    }

    public String getEstDescripcion() {
        return estDescripcion;
    }

    public void setEstDescripcion(String estDescripcion) {
        this.estDescripcion = estDescripcion;
    }

    public String getAudEliminado() {
        return audEliminado;
    }

    public void setAudEliminado(String audEliminado) {
        this.audEliminado = audEliminado;
    }

    public LocalDateTime getAudFechaAdicion() {
        return audFechaAdicion;
    }

    public void setAudFechaAdicion(LocalDateTime audFechaAdicion) {
        this.audFechaAdicion = audFechaAdicion;
    }

    public LocalDateTime getAudFechaModificacion() {
        return audFechaModificacion;
    }

    public void setAudFechaModificacion(LocalDateTime audFechaModificacion) {
        this.audFechaModificacion = audFechaModificacion;
    }

    public String getAudModificado() {
        return audModificado;
    }

    public void setAudModificado(String audModificado) {
        this.audModificado = audModificado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estCodigo != null ? estCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadEstado)) {
            return false;
        }
        SgadEstado other = (SgadEstado) object;
        if ((this.estCodigo == null && other.estCodigo != null) || (this.estCodigo != null && !this.estCodigo.equals(other.estCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadEstado[ estCodigo=" + estCodigo + " ]";
    }
    
}
