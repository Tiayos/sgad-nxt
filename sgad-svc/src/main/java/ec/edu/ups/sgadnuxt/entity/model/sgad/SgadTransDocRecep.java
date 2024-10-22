package ec.edu.ups.sgadnuxt.entity.model.sgad;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "SGAD_TRANS_DOC_RECEP", schema = "SGAD")
public class SgadTransDocRecep implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "TDR_CODIGO")
    private BigDecimal tdrCodigo;
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
    @JoinColumn(name = "TRD_CODIGO", referencedColumnName = "TRD_CODIGO")
    @ManyToOne
    private SgadTransDocumentales trdCodigo;

    public SgadTransDocRecep() {
    }

    public SgadTransDocRecep(BigDecimal tdrCodigo) {
        this.tdrCodigo = tdrCodigo;
    }

    public SgadTransDocRecep(BigDecimal tdrCodigo, String audAdicionado, String audEliminado) {
        this.tdrCodigo = tdrCodigo;
        this.audAdicionado = audAdicionado;
        this.audEliminado = audEliminado;
    }

    public BigDecimal getTdrCodigo() {
        return tdrCodigo;
    }

    public void setTdrCodigo(BigDecimal tdrCodigo) {
        this.tdrCodigo = tdrCodigo;
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

    public SgadTransDocumentales getTrdCodigo() {
        return trdCodigo;
    }

    public void setTrdCodigo(SgadTransDocumentales trdCodigo) {
        this.trdCodigo = trdCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdrCodigo != null ? tdrCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadTransDocRecep)) {
            return false;
        }
        SgadTransDocRecep other = (SgadTransDocRecep) object;
        if ((this.tdrCodigo == null && other.tdrCodigo != null) || (this.tdrCodigo != null && !this.tdrCodigo.equals(other.tdrCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadTransDocRecep[ tdrCodigo=" + tdrCodigo + " ]";
    }
    
}
