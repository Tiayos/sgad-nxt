package ec.edu.ups.sgadnuxt.entity.model.sgad;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "SGAD_BASE_LEGAL",schema = "SGAD")
public class SgadBaseLegal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "BAL_CODIGO")
    private Long balCodigo;
    @Column(name = "BAL_BASE")
    private String balBase;
    @Column(name = "AUD_ADICIONADO")
    private String audAdicionado;
    @Column(name = "AUD_ELIMINADO")
    private String audEliminado;
    @Column(name = "AUD_FECHA_ADICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFechaAdicion;
    @Column(name = "AUD_FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFechaModificacion;
    @Column(name = "AUD_MODIFICADO")
    private String audModificado;
    @Lob
    @Column(name = "BAL_DESCRIPCION")
    private String balDescripcion;
    @OneToMany(mappedBy = "balCodigo")
    private Collection<SgadClasificacionDocumental> sgadClasificacionDocumentalCollection;

    public SgadBaseLegal() {
    }

    public SgadBaseLegal(Long balCodigo) {
        this.balCodigo = balCodigo;
    }

    public Long getBalCodigo() {
        return balCodigo;
    }

    public void setBalCodigo(Long balCodigo) {
        this.balCodigo = balCodigo;
    }

    public String getBalBase() {
        return balBase;
    }

    public void setBalBase(String balBase) {
        this.balBase = balBase;
    }

    public String getAudAdicionado() {
        return audAdicionado;
    }

    public void setAudAdicionado(String audAdicionado) {
        this.audAdicionado = audAdicionado;
    }

    public String getAudEliminado() {
        return audEliminado;
    }

    public void setAudEliminado(String audEliminado) {
        this.audEliminado = audEliminado;
    }

    public Date getAudFechaAdicion() {
        return audFechaAdicion;
    }

    public void setAudFechaAdicion(Date audFechaAdicion) {
        this.audFechaAdicion = audFechaAdicion;
    }

    public Date getAudFechaModificacion() {
        return audFechaModificacion;
    }

    public void setAudFechaModificacion(Date audFechaModificacion) {
        this.audFechaModificacion = audFechaModificacion;
    }

    public String getAudModificado() {
        return audModificado;
    }

    public void setAudModificado(String audModificado) {
        this.audModificado = audModificado;
    }

    public String getBalDescripcion() {
        return balDescripcion;
    }

    public void setBalDescripcion(String balDescripcion) {
        this.balDescripcion = balDescripcion;
    }

    public Collection<SgadClasificacionDocumental> getSgadClasificacionDocumentalCollection() {
        return sgadClasificacionDocumentalCollection;
    }

    public void setSgadClasificacionDocumentalCollection(Collection<SgadClasificacionDocumental> sgadClasificacionDocumentalCollection) {
        this.sgadClasificacionDocumentalCollection = sgadClasificacionDocumentalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (balCodigo != null ? balCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadBaseLegal)) {
            return false;
        }
        SgadBaseLegal other = (SgadBaseLegal) object;
        if ((this.balCodigo == null && other.balCodigo != null) || (this.balCodigo != null && !this.balCodigo.equals(other.balCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadBaseLegal[ balCodigo=" + balCodigo + " ]";
    }
    
}
