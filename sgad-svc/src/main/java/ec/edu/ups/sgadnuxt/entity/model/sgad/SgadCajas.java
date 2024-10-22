/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.sgadnuxt.entity.model.sgad;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "SGAD_CAJAS",schema = "SGAD")
public class SgadCajas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CAJ_CODIGO")
    private Long cajCodigo;
    @Basic(optional = false)
    @Column(name = "AUD_ADICIONADO")
    private String audAdicionado;
    @Basic(optional = false)
    @Column(name = "CAJ_NUMERO")
    private int cajNumero;
    @Column(name = "CAJ_NUMERO_CAPETAS")
    private Integer cajNumeroCapetas;
    @Column(name = "CAJ_OBSERVACIONES")
    private String cajObservaciones;
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
    @Basic(optional = false)
    @Column(name = "AMB_CODIGO")
    private BigInteger ambCodigo;
    @Column(name = "CAJ_ESTADO")
    private String cajEstado;
    @Column(name = "CAJ_ARCHIVO")
    private String cajArchivo;
    @Basic(optional = false)
    @Column(name = "CAJ_SUB_NUMERO")
    private int cajSubNumero;
    @Column(name = "EMP_CODIGO")
    private Short empCodigo;
    @OneToMany(mappedBy = "cajCodigo")
    private Collection<SgadContenidoDocumental> sgadContenidoDocumentalCollection;

    public SgadCajas() {
    }

    public SgadCajas(Long cajCodigo) {
        this.cajCodigo = cajCodigo;
    }

    public SgadCajas(Long cajCodigo, String audAdicionado, int cajNumero, String audEliminado, BigInteger ambCodigo, int cajSubNumero) {
        this.cajCodigo = cajCodigo;
        this.audAdicionado = audAdicionado;
        this.cajNumero = cajNumero;
        this.audEliminado = audEliminado;
        this.ambCodigo = ambCodigo;
        this.cajSubNumero = cajSubNumero;
    }

    public Long getCajCodigo() {
        return cajCodigo;
    }

    public void setCajCodigo(Long cajCodigo) {
        this.cajCodigo = cajCodigo;
    }

    public String getAudAdicionado() {
        return audAdicionado;
    }

    public void setAudAdicionado(String audAdicionado) {
        this.audAdicionado = audAdicionado;
    }

    public int getCajNumero() {
        return cajNumero;
    }

    public void setCajNumero(int cajNumero) {
        this.cajNumero = cajNumero;
    }

    public Integer getCajNumeroCapetas() {
        return cajNumeroCapetas;
    }

    public void setCajNumeroCapetas(Integer cajNumeroCapetas) {
        this.cajNumeroCapetas = cajNumeroCapetas;
    }

    public String getCajObservaciones() {
        return cajObservaciones;
    }

    public void setCajObservaciones(String cajObservaciones) {
        this.cajObservaciones = cajObservaciones;
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

    public BigInteger getAmbCodigo() {
        return ambCodigo;
    }

    public void setAmbCodigo(BigInteger ambCodigo) {
        this.ambCodigo = ambCodigo;
    }

    public String getCajEstado() {
        return cajEstado;
    }

    public void setCajEstado(String cajEstado) {
        this.cajEstado = cajEstado;
    }

    public String getCajArchivo() {
        return cajArchivo;
    }

    public void setCajArchivo(String cajArchivo) {
        this.cajArchivo = cajArchivo;
    }

    public int getCajSubNumero() {
        return cajSubNumero;
    }

    public void setCajSubNumero(int cajSubNumero) {
        this.cajSubNumero = cajSubNumero;
    }

    public Short getEmpCodigo() {
        return empCodigo;
    }

    public void setEmpCodigo(Short empCodigo) {
        this.empCodigo = empCodigo;
    }

    public Collection<SgadContenidoDocumental> getSgadContenidoDocumentalCollection() {
        return sgadContenidoDocumentalCollection;
    }

    public void setSgadContenidoDocumentalCollection(Collection<SgadContenidoDocumental> sgadContenidoDocumentalCollection) {
        this.sgadContenidoDocumentalCollection = sgadContenidoDocumentalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cajCodigo != null ? cajCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadCajas)) {
            return false;
        }
        SgadCajas other = (SgadCajas) object;
        if ((this.cajCodigo == null && other.cajCodigo != null) || (this.cajCodigo != null && !this.cajCodigo.equals(other.cajCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadCajas[ cajCodigo=" + cajCodigo + " ]";
    }
    
}
