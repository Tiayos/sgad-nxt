package ec.edu.ups.sgadnuxt.entity.model.sgad;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "SGAD_ESTADO_TRANSFERENCIA", schema = "SGAD")
public class SgadEstadoTransferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ETR_CODIGO")
    private BigDecimal etrCodigo;
    @Column(name = "AUD_ADICIONADO")
    private String audAdicionado;
    @Column(name = "ETR_DESCRIPCION")
    private String etrDescripcion;
    @Column(name = "AUD_ELIMINADO")
    private String audEliminado;
    @Basic(optional = false)
    @Column(name = "ETR_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date etrFecha;
    @Column(name = "AUD_FECHA_ADICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFechaAdicion;
    @Column(name = "AUD_FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date audFechaModificacion;
    @Column(name = "AUD_MODIFICADO")
    private String audModificado;
    @Column(name = "ETR_VIGENTE")
    private String etrVigente;
    @JoinColumn(name = "EST_CODIGO", referencedColumnName = "EST_CODIGO")
    @ManyToOne(optional = false)
    private SgadEstado estCodigo;
    @JoinColumn(name = "TRD_CODIGO", referencedColumnName = "TRD_CODIGO")
    @ManyToOne(optional = false)
    private SgadTransDocumentales trdCodigo;

    public SgadEstadoTransferencia() {
    }

    public SgadEstadoTransferencia(BigDecimal etrCodigo) {
        this.etrCodigo = etrCodigo;
    }

    public SgadEstadoTransferencia(BigDecimal etrCodigo, Date etrFecha) {
        this.etrCodigo = etrCodigo;
        this.etrFecha = etrFecha;
    }

    public BigDecimal getEtrCodigo() {
        return etrCodigo;
    }

    public void setEtrCodigo(BigDecimal etrCodigo) {
        this.etrCodigo = etrCodigo;
    }

    public String getAudAdicionado() {
        return audAdicionado;
    }

    public void setAudAdicionado(String audAdicionado) {
        this.audAdicionado = audAdicionado;
    }

    public String getEtrDescripcion() {
        return etrDescripcion;
    }

    public void setEtrDescripcion(String etrDescripcion) {
        this.etrDescripcion = etrDescripcion;
    }

    public String getAudEliminado() {
        return audEliminado;
    }

    public void setAudEliminado(String audEliminado) {
        this.audEliminado = audEliminado;
    }

    public Date getEtrFecha() {
        return etrFecha;
    }

    public void setEtrFecha(Date etrFecha) {
        this.etrFecha = etrFecha;
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

    public String getEtrVigente() {
        return etrVigente;
    }

    public void setEtrVigente(String etrVigente) {
        this.etrVigente = etrVigente;
    }

    public SgadEstado getEstCodigo() {
        return estCodigo;
    }

    public void setEstCodigo(SgadEstado estCodigo) {
        this.estCodigo = estCodigo;
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
        hash += (etrCodigo != null ? etrCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadEstadoTransferencia)) {
            return false;
        }
        SgadEstadoTransferencia other = (SgadEstadoTransferencia) object;
        if ((this.etrCodigo == null && other.etrCodigo != null) || (this.etrCodigo != null && !this.etrCodigo.equals(other.etrCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadEstadoTransferencia[ etrCodigo=" + etrCodigo + " ]";
    }
    
}
