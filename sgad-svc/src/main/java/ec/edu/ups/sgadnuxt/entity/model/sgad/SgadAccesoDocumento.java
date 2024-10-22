
package ec.edu.ups.sgadnuxt.entity.model.sgad;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "SGAD_ACCESO_DOCUMENTO", schema = "SGAD")
public class SgadAccesoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ACD_CODIGO")
    private BigDecimal acdCodigo;
    @Column(name = "ACD_ABREVIATURA")
    private String acdAbreviatura;
    @Column(name = "ACD_DESCRIPCION")
    private String acdDescripcion;
    @Basic(optional = false)
    @Column(name = "AUD_ADICIONADO")
    private String audAdicionado;
    @Basic(optional = false)
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
    @OneToMany(mappedBy = "acdCodigo")
    private Collection<SgadClasificacionDocumental> sgadClasificacionDocumentalCollection;

    public SgadAccesoDocumento() {
    }

    public SgadAccesoDocumento(BigDecimal acdCodigo) {
        this.acdCodigo = acdCodigo;
    }

    public SgadAccesoDocumento(BigDecimal acdCodigo, String audAdicionado, String audEliminado) {
        this.acdCodigo = acdCodigo;
        this.audAdicionado = audAdicionado;
        this.audEliminado = audEliminado;
    }

    public BigDecimal getAcdCodigo() {
        return acdCodigo;
    }

    public void setAcdCodigo(BigDecimal acdCodigo) {
        this.acdCodigo = acdCodigo;
    }

    public String getAcdAbreviatura() {
        return acdAbreviatura;
    }

    public void setAcdAbreviatura(String acdAbreviatura) {
        this.acdAbreviatura = acdAbreviatura;
    }

    public String getAcdDescripcion() {
        return acdDescripcion;
    }

    public void setAcdDescripcion(String acdDescripcion) {
        this.acdDescripcion = acdDescripcion;
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

    public Collection<SgadClasificacionDocumental> getSgadClasificacionDocumentalCollection() {
        return sgadClasificacionDocumentalCollection;
    }

    public void setSgadClasificacionDocumentalCollection(Collection<SgadClasificacionDocumental> sgadClasificacionDocumentalCollection) {
        this.sgadClasificacionDocumentalCollection = sgadClasificacionDocumentalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (acdCodigo != null ? acdCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadAccesoDocumento)) {
            return false;
        }
        SgadAccesoDocumento other = (SgadAccesoDocumento) object;
        if ((this.acdCodigo == null && other.acdCodigo != null) || (this.acdCodigo != null && !this.acdCodigo.equals(other.acdCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadAccesoDocumento[ acdCodigo=" + acdCodigo + " ]";
    }
    
}
