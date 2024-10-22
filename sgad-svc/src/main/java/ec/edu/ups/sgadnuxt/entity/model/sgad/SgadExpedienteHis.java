
package ec.edu.ups.sgadnuxt.entity.model.sgad;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SGAD_EXPEDIENTE_HIS", schema = "SGAD")
public class SgadExpedienteHis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EXH_CODIGO")
    private Long exhCodigo;
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
    @Column(name = "EXH_ESTADO")
    private String exhEstado;
    @JoinColumn(name = "COD_CODIGO", referencedColumnName = "COD_CODIGO")
    @OneToOne(optional = false)
    private SgadContenidoDocumental codCodigo;
    @OneToOne(mappedBy = "exhCodigo")
    private SgadDetalleTransferencia sgadDetalleTransferencia;

    public SgadExpedienteHis() {
    }

    public SgadExpedienteHis(Long exhCodigo) {
        this.exhCodigo = exhCodigo;
    }

    public SgadExpedienteHis(Long exhCodigo, String audAdicionado, String audEliminado) {
        this.exhCodigo = exhCodigo;
        this.audAdicionado = audAdicionado;
        this.audEliminado = audEliminado;
    }

    public Long getExhCodigo() {
        return exhCodigo;
    }

    public void setExhCodigo(Long exhCodigo) {
        this.exhCodigo = exhCodigo;
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

    public String getExhEstado() {
        return exhEstado;
    }

    public void setExhEstado(String exhEstado) {
        this.exhEstado = exhEstado;
    }

    public SgadContenidoDocumental getCodCodigo() {
        return codCodigo;
    }

    public void setCodCodigo(SgadContenidoDocumental codCodigo) {
        this.codCodigo = codCodigo;
    }

    public SgadDetalleTransferencia getSgadDetalleTransferencia() {
        return sgadDetalleTransferencia;
    }

    public void setSgadDetalleTransferencia(SgadDetalleTransferencia sgadDetalleTransferencia) {
        this.sgadDetalleTransferencia = sgadDetalleTransferencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exhCodigo != null ? exhCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadExpedienteHis)) {
            return false;
        }
        SgadExpedienteHis other = (SgadExpedienteHis) object;
        if ((this.exhCodigo == null && other.exhCodigo != null) || (this.exhCodigo != null && !this.exhCodigo.equals(other.exhCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadExpedienteHis[ exhCodigo=" + exhCodigo + " ]";
    }
    
}
