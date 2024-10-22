
package ec.edu.ups.sgadnuxt.entity.model.sgad;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "SGAD_CLASIFICACION_DOCUMENTAL", schema = "SGAD")
public class SgadClasificacionDocumental implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CLD_CODIGO")
    private Long cldCodigo;
    @Basic(optional = false)
    @Column(name = "CLD_CODIGO_CLASIFICACION")
    private String cldCodigoClasificacion;
    @Basic(optional = false)
    @Column(name = "CLD_DESCRIPCION")
    private String cldDescripcion;
    @Basic(optional = false)
    @Column(name = "CLD_ES_EXPEDIENTE")
    private String cldEsExpediente;
    @Column(name = "CLD_ESTADO")
    private String cldEstado;
    @Column(name = "CLD_ARCHIVOINTERMEDIO")
    private Short cldArchivointermedio;
    @Column(name = "CLD_ARCHIVODEFINITIVO")
    private Short cldArchivodefinitivo;
    @Column(name = "CLD_ARCHIVOHISTORICO")
    private Short cldArchivohistorico;
    @Column(name = "CLD_ORDEN")
    private Integer cldOrden;
    @Column(name = "CLD_NIVEL")
    private Integer cldNivel;
    @Column(name = "AUD_ADICIONADO")
    private String audAdicionado;
    @Column(name = "AUD_ELIMINADO")
    private String audEliminado;
    @Column(name = "AUD_FECHA_ADICION")
    private LocalDateTime audFechaAdicion;
    @Column(name = "AUD_FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime audFechaModificacion;
    @Column(name = "AUD_MODIFICADO")
    private String audModificado;
    @JoinColumn(name = "ACD_CODIGO", referencedColumnName = "ACD_CODIGO")
    @ManyToOne
    private SgadAccesoDocumento acdCodigo;
    @JoinColumn(name = "BAL_CODIGO", referencedColumnName = "BAL_CODIGO")
    @ManyToOne
    private SgadBaseLegal balCodigo;
    @JoinColumn(name = "CLD_CODIGO_PADRE", referencedColumnName = "CLD_CODIGO")
    @ManyToOne
    private SgadClasificacionDocumental cldCodigoPadre;

    public SgadClasificacionDocumental() {
    }

    public SgadClasificacionDocumental(Long cldCodigo) {
        this.cldCodigo = cldCodigo;
    }

    public SgadClasificacionDocumental(Long cldCodigo, String cldCodigoClasificacion, String cldDescripcion, String cldEsExpediente) {
        this.cldCodigo = cldCodigo;
        this.cldCodigoClasificacion = cldCodigoClasificacion;
        this.cldDescripcion = cldDescripcion;
        this.cldEsExpediente = cldEsExpediente;
    }

    public Long getCldCodigo() {
        return cldCodigo;
    }

    public void setCldCodigo(Long cldCodigo) {
        this.cldCodigo = cldCodigo;
    }

    public String getAudAdicionado() {
        return audAdicionado;
    }

    public void setAudAdicionado(String audAdicionado) {
        this.audAdicionado = audAdicionado;
    }

    public String getCldCodigoClasificacion() {
        return cldCodigoClasificacion;
    }

    public void setCldCodigoClasificacion(String cldCodigoClasificacion) {
        this.cldCodigoClasificacion = cldCodigoClasificacion;
    }

    public String getCldDescripcion() {
        return cldDescripcion;
    }

    public void setCldDescripcion(String cldDescripcion) {
        this.cldDescripcion = cldDescripcion;
    }

    public String getAudEliminado() {
        return audEliminado;
    }

    public void setAudEliminado(String audEliminado) {
        this.audEliminado = audEliminado;
    }

    public String getCldEsExpediente() {
        return cldEsExpediente;
    }

    public void setCldEsExpediente(String cldEsExpediente) {
        this.cldEsExpediente = cldEsExpediente;
    }

    public String getCldEstado() {
        return cldEstado;
    }

    public void setCldEstado(String cldEstado) {
        this.cldEstado = cldEstado;
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

    public Short getCldArchivointermedio() {
        return cldArchivointermedio;
    }

    public void setCldArchivointermedio(Short cldArchivointermedio) {
        this.cldArchivointermedio = cldArchivointermedio;
    }

    public Short getCldArchivodefinitivo() {
        return cldArchivodefinitivo;
    }

    public void setCldArchivodefinitivo(Short cldArchivodefinitivo) {
        this.cldArchivodefinitivo = cldArchivodefinitivo;
    }

    public Short getCldArchivohistorico() {
        return cldArchivohistorico;
    }

    public void setCldArchivohistorico(Short cldArchivohistorico) {
        this.cldArchivohistorico = cldArchivohistorico;
    }

    public Integer getCldOrden() {
        return cldOrden;
    }

    public void setCldOrden(Integer cldOrden) {
        this.cldOrden = cldOrden;
    }

    public Integer getCldNivel() {
        return cldNivel;
    }

    public void setCldNivel(Integer cldNivel) {
        this.cldNivel = cldNivel;
    }

    public SgadAccesoDocumento getAcdCodigo() {
        return acdCodigo;
    }

    public void setAcdCodigo(SgadAccesoDocumento acdCodigo) {
        this.acdCodigo = acdCodigo;
    }

    public SgadBaseLegal getBalCodigo() {
        return balCodigo;
    }

    public void setBalCodigo(SgadBaseLegal balCodigo) {
        this.balCodigo = balCodigo;
    }

    public SgadClasificacionDocumental getCldCodigoPadre() {
        return cldCodigoPadre;
    }

    public void setCldCodigoPadre(SgadClasificacionDocumental cldCodigoPadre) {
        this.cldCodigoPadre = cldCodigoPadre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cldCodigo != null ? cldCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadClasificacionDocumental)) {
            return false;
        }
        SgadClasificacionDocumental other = (SgadClasificacionDocumental) object;
        if ((this.cldCodigo == null && other.cldCodigo != null) || (this.cldCodigo != null && !this.cldCodigo.equals(other.cldCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadClasificacionDocumental[ cldCodigo=" + cldCodigo + " ]";
    }
    
}
