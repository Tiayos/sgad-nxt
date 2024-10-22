
package ec.edu.ups.sgadnuxt.entity.model.sgad;

import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.DetalleTransferenciaDTO;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Tiago
 */
@Entity
@Table(name = "SGAD_DETALLE_TRANSFERENCIA", schema = "SGAD")
public class SgadDetalleTransferencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "DET_CODIGO")
    private Long detCodigo;
    @Basic(optional = false)
    @Column(name = "DET_DESCRIPCION")
    private String detDescripcion;
    @Column(name = "DET_FECHA_FINAL")
    private LocalDate detFechaFinal;
    @Column(name = "DET_FECHA_INICIO")
    private LocalDate detFechaInicio;
    @Column(name = "DET_NUM_ORDEN")
    private String detNumOrden;
    @Column(name = "DET_OBSERVACIONES")
    private String detObservaciones;
    @Column(name = "DET_MODIFICACIONES")
    private String detModificaciones;
    @Column(name = "DET_REFERENCIA_ACTA")
    private String detReferenciaActa;
    @Column(name = "DET_ASIGNA")
    private String detAsigna;
    @Column(name = "DET_NUM_ORDEN_LN")
    private Long detNumOrdenLn;
    @Column(name = "DET_NUM_CAJA")
    private Long detNumCaja;
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
    @JoinColumn(name = "CLD_CODIGO", referencedColumnName = "CLD_CODIGO")
    @ManyToOne
    private SgadClasificacionDocumental cldCodigo;
    @JoinColumn(name = "EXH_CODIGO", referencedColumnName = "EXH_CODIGO")
    @OneToOne
    private SgadExpedienteHis exhCodigo;
    @JoinColumn(name = "TRD_CODIGO", referencedColumnName = "TRD_CODIGO")
    @ManyToOne(optional = false)
    private SgadTransDocumentales trdCodigo;

    public SgadDetalleTransferencia() {
    }

    public SgadDetalleTransferencia(Long detCodigo) {
        this.detCodigo = detCodigo;
    }

    public SgadDetalleTransferencia(Long detCodigo, String detDescripcion) {
        this.detCodigo = detCodigo;
        this.detDescripcion = detDescripcion;
    }

    public SgadDetalleTransferencia(DetalleTransferenciaDTO dto) {
        this.detCodigo = dto.codigo();
        this.detDescripcion = dto.detDescripcion();
        this.detFechaFinal = dto.detFechaFinal();
        this.detFechaInicio = dto.detFechaInicio();
        this.detNumCaja = dto.detNumCaja();
        this.detNumOrden = dto.detNumOrden();
        this.trdCodigo = new SgadTransDocumentales();
        this.audAdicionado = dto.audAdicionado();
    }

    @PrePersist
    public void prePersist(){
        this.cldCodigo = null;
        this.exhCodigo = null;
        this.audFechaAdicion = LocalDateTime.now();
        this.audEliminado = "N";
    }

    @PreUpdate
    public void preUpdate(){
        this.audFechaModificacion = LocalDateTime.now();
        this.audEliminado = "N";
    }


    public Long getDetCodigo() {
        return detCodigo;
    }

    public void setDetCodigo(Long detCodigo) {
        this.detCodigo = detCodigo;
    }

    public String getAudAdicionado() {
        return audAdicionado;
    }

    public void setAudAdicionado(String audAdicionado) {
        this.audAdicionado = audAdicionado;
    }

    public String getDetDescripcion() {
        return detDescripcion;
    }

    public void setDetDescripcion(String detDescripcion) {
        this.detDescripcion = detDescripcion;
    }

    public String getAudEliminado() {
        return audEliminado;
    }

    public void setAudEliminado(String audEliminado) {
        this.audEliminado = audEliminado;
    }


    public LocalDate getDetFechaFinal() {
        return detFechaFinal;
    }

    public void setDetFechaFinal(LocalDate detFechaFinal) {
        this.detFechaFinal = detFechaFinal;
    }

    public LocalDate getDetFechaInicio() {
        return detFechaInicio;
    }

    public void setDetFechaInicio(LocalDate detFechaInicio) {
        this.detFechaInicio = detFechaInicio;
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

    public String getDetNumOrden() {
        return detNumOrden;
    }

    public void setDetNumOrden(String detNumOrden) {
        this.detNumOrden = detNumOrden;
    }

    public String getDetObservaciones() {
        return detObservaciones;
    }

    public void setDetObservaciones(String detObservaciones) {
        this.detObservaciones = detObservaciones;
    }

    public String getDetModificaciones() {
        return detModificaciones;
    }

    public void setDetModificaciones(String detModificaciones) {
        this.detModificaciones = detModificaciones;
    }

    public String getDetReferenciaActa() {
        return detReferenciaActa;
    }

    public void setDetReferenciaActa(String detReferenciaActa) {
        this.detReferenciaActa = detReferenciaActa;
    }

    public String getDetAsigna() {
        return detAsigna;
    }

    public void setDetAsigna(String detAsigna) {
        this.detAsigna = detAsigna;
    }

    public Long getDetNumOrdenLn() {
        return detNumOrdenLn;
    }

    public void setDetNumOrdenLn(Long detNumOrdenLn) {
        this.detNumOrdenLn = detNumOrdenLn;
    }

    public Long getDetNumCaja() {
        return detNumCaja;
    }

    public void setDetNumCaja(Long detNumCaja) {
        this.detNumCaja = detNumCaja;
    }

    public SgadClasificacionDocumental getCldCodigo() {
        return cldCodigo;
    }

    public void setCldCodigo(SgadClasificacionDocumental cldCodigo) {
        this.cldCodigo = cldCodigo;
    }

    public SgadExpedienteHis getExhCodigo() {
        return exhCodigo;
    }

    public void setExhCodigo(SgadExpedienteHis exhCodigo) {
        this.exhCodigo = exhCodigo;
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
        hash += (detCodigo != null ? detCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadDetalleTransferencia)) {
            return false;
        }
        SgadDetalleTransferencia other = (SgadDetalleTransferencia) object;
        if ((this.detCodigo == null && other.detCodigo != null) || (this.detCodigo != null && !this.detCodigo.equals(other.detCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadDetalleTransferencia[ detCodigo=" + detCodigo + " ]";
    }
    
}
