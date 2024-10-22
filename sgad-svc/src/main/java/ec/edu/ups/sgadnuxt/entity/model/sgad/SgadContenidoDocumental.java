
package ec.edu.ups.sgadnuxt.entity.model.sgad;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Tiago
 */
@Entity
@Table(name = "SGAD_CONTENIDO_DOCUMENTAL",schema = "SGAD")
public class SgadContenidoDocumental implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_CODIGO")
    private Long codCodigo;
    @Basic(optional = false)
    @Column(name = "AUD_ADICIONADO")
    private String audAdicionado;
    @Basic(optional = false)
    @Column(name = "COD_DESCRIPCION")
    private String codDescripcion;
    @Basic(optional = false)
    @Column(name = "COD_FECHA_FINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date codFechaFinal;
    @Basic(optional = false)
    @Column(name = "COD_FECHA_INICIAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date codFechaInicial;
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
    @Column(name = "COD_ESTADODIG")
    private String codEstadodig;
    @Column(name = "COD_ARCHIVO")
    private String codArchivo;
    @Column(name = "COD_CED_ESTUDIANTE")
    private String codCedEstudiante;
    @Column(name = "COD_TIPO_CONTENIDO")
    private String codTipoContenido;
    @Column(name = "COD_TIPO_IDENTIFICACION")
    private String codTipoIdentificacion;
    @JoinColumn(name = "CAJ_CODIGO", referencedColumnName = "CAJ_CODIGO")
    @ManyToOne
    private SgadCajas cajCodigo;
    @JoinColumn(name = "CLD_CODIGO", referencedColumnName = "CLD_CODIGO")
    @ManyToOne
    private SgadClasificacionDocumental cldCodigo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "codCodigo")
    private SgadExpedienteHis sgadExpedienteHis;

    public SgadContenidoDocumental() {
    }

    public SgadContenidoDocumental(Long codCodigo) {
        this.codCodigo = codCodigo;
    }

    public SgadContenidoDocumental(Long codCodigo, String audAdicionado, String codDescripcion, Date codFechaFinal, Date codFechaInicial, String audEliminado) {
        this.codCodigo = codCodigo;
        this.audAdicionado = audAdicionado;
        this.codDescripcion = codDescripcion;
        this.codFechaFinal = codFechaFinal;
        this.codFechaInicial = codFechaInicial;
        this.audEliminado = audEliminado;
    }

    public Long getCodCodigo() {
        return codCodigo;
    }

    public void setCodCodigo(Long codCodigo) {
        this.codCodigo = codCodigo;
    }

    public String getAudAdicionado() {
        return audAdicionado;
    }

    public void setAudAdicionado(String audAdicionado) {
        this.audAdicionado = audAdicionado;
    }

    public String getCodDescripcion() {
        return codDescripcion;
    }

    public void setCodDescripcion(String codDescripcion) {
        this.codDescripcion = codDescripcion;
    }

    public Date getCodFechaFinal() {
        return codFechaFinal;
    }

    public void setCodFechaFinal(Date codFechaFinal) {
        this.codFechaFinal = codFechaFinal;
    }

    public Date getCodFechaInicial() {
        return codFechaInicial;
    }

    public void setCodFechaInicial(Date codFechaInicial) {
        this.codFechaInicial = codFechaInicial;
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

    public String getCodEstadodig() {
        return codEstadodig;
    }

    public void setCodEstadodig(String codEstadodig) {
        this.codEstadodig = codEstadodig;
    }

    public String getCodArchivo() {
        return codArchivo;
    }

    public void setCodArchivo(String codArchivo) {
        this.codArchivo = codArchivo;
    }

    public String getCodCedEstudiante() {
        return codCedEstudiante;
    }

    public void setCodCedEstudiante(String codCedEstudiante) {
        this.codCedEstudiante = codCedEstudiante;
    }

    public String getCodTipoContenido() {
        return codTipoContenido;
    }

    public void setCodTipoContenido(String codTipoContenido) {
        this.codTipoContenido = codTipoContenido;
    }

    public String getCodTipoIdentificacion() {
        return codTipoIdentificacion;
    }

    public void setCodTipoIdentificacion(String codTipoIdentificacion) {
        this.codTipoIdentificacion = codTipoIdentificacion;
    }

    public SgadCajas getCajCodigo() {
        return cajCodigo;
    }

    public void setCajCodigo(SgadCajas cajCodigo) {
        this.cajCodigo = cajCodigo;
    }

    public SgadClasificacionDocumental getCldCodigo() {
        return cldCodigo;
    }

    public void setCldCodigo(SgadClasificacionDocumental cldCodigo) {
        this.cldCodigo = cldCodigo;
    }

    public SgadExpedienteHis getSgadExpedienteHis() {
        return sgadExpedienteHis;
    }

    public void setSgadExpedienteHis(SgadExpedienteHis sgadExpedienteHis) {
        this.sgadExpedienteHis = sgadExpedienteHis;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCodigo != null ? codCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadContenidoDocumental)) {
            return false;
        }
        SgadContenidoDocumental other = (SgadContenidoDocumental) object;
        if ((this.codCodigo == null && other.codCodigo != null) || (this.codCodigo != null && !this.codCodigo.equals(other.codCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadContenidoDocumental[ codCodigo=" + codCodigo + " ]";
    }
    
}
