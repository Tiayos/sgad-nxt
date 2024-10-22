/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.ups.sgadnuxt.entity.model.sgad;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Entity
@Table(name = "SGAD_DOCU_ELECTRONICO", schema = "SGAD")
public class SgadDocuElectronico implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "DOE_CODIGO")
    private BigDecimal doeCodigo;
    @Basic(optional = false)
    @Column(name = "AUD_ADICIONADO")
    private String audAdicionado;
    @Lob
    @Column(name = "DOE_DATOS")
    private Serializable doeDatos;
    @Column(name = "DOE_DESCRIPCION")
    private String doeDescripcion;
    @Column(name = "DOE_NOMBRE")
    private String doeNombre;
    @Column(name = "DOE_TIPO")
    private String doeTipo;
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
    @JoinColumn(name = "DOC_CODIGO", referencedColumnName = "DOC_CODIGO")
    @ManyToOne
    private SgadDocumento docCodigo;

    public SgadDocuElectronico() {
    }

    public SgadDocuElectronico(BigDecimal doeCodigo) {
        this.doeCodigo = doeCodigo;
    }

    public SgadDocuElectronico(BigDecimal doeCodigo, String audAdicionado, String audEliminado) {
        this.doeCodigo = doeCodigo;
        this.audAdicionado = audAdicionado;
        this.audEliminado = audEliminado;
    }

    public BigDecimal getDoeCodigo() {
        return doeCodigo;
    }

    public void setDoeCodigo(BigDecimal doeCodigo) {
        this.doeCodigo = doeCodigo;
    }

    public String getAudAdicionado() {
        return audAdicionado;
    }

    public void setAudAdicionado(String audAdicionado) {
        this.audAdicionado = audAdicionado;
    }

    public Serializable getDoeDatos() {
        return doeDatos;
    }

    public void setDoeDatos(Serializable doeDatos) {
        this.doeDatos = doeDatos;
    }

    public String getDoeDescripcion() {
        return doeDescripcion;
    }

    public void setDoeDescripcion(String doeDescripcion) {
        this.doeDescripcion = doeDescripcion;
    }

    public String getDoeNombre() {
        return doeNombre;
    }

    public void setDoeNombre(String doeNombre) {
        this.doeNombre = doeNombre;
    }

    public String getDoeTipo() {
        return doeTipo;
    }

    public void setDoeTipo(String doeTipo) {
        this.doeTipo = doeTipo;
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

    public SgadDocumento getDocCodigo() {
        return docCodigo;
    }

    public void setDocCodigo(SgadDocumento docCodigo) {
        this.docCodigo = docCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (doeCodigo != null ? doeCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadDocuElectronico)) {
            return false;
        }
        SgadDocuElectronico other = (SgadDocuElectronico) object;
        if ((this.doeCodigo == null && other.doeCodigo != null) || (this.doeCodigo != null && !this.doeCodigo.equals(other.doeCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadDocuElectronico[ doeCodigo=" + doeCodigo + " ]";
    }
    
}
