
package ec.edu.ups.sgadnuxt.entity.model.sgad;

import ec.edu.ups.sgadnuxt.entity.dto.PersonaDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.DocumentoDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.DocumentoTipoDTO;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Table(name = "SGAD_DOCUMENTO_TIPO", schema = "SGAD")
public class SgadDocumentoTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "DOT_CODIGO")
    private Long dotCodigo;
    @Basic(optional = false)
    @Column(name = "AUD_ADICIONADO")
    private String audAdicionado;
    @Basic(optional = false)
    @Column(name = "AUD_ELIMINADO")
    private String audEliminado;
    @Column(name = "AUD_FECHA_ADICION")
    private LocalDateTime audFechaAdicion;
    @Column(name = "AUD_FECHA_MODIFICACION")
    private LocalDateTime audFechaModificacion;
    @Column(name = "AUD_MODIFICADO")
    private String audModificado;
    @JoinColumn(name = "DOC_CODIGO", referencedColumnName = "DOC_CODIGO")
    @ManyToOne
    private SgadDocumento docCodigo;
    @JoinColumn(name = "TID_CODIGO", referencedColumnName = "TID_CODIGO")
    @ManyToOne
    private SgadTipoDocumental tidCodigo;

    public SgadDocumentoTipo() {
    }

    public SgadDocumentoTipo(Long dotCodigo) {
        this.dotCodigo = dotCodigo;
    }

    public SgadDocumentoTipo(Long dotCodigo, String audAdicionado, String audEliminado) {
        this.dotCodigo = dotCodigo;
        this.audAdicionado = audAdicionado;
        this.audEliminado = audEliminado;
    }

    public SgadDocumentoTipo(DocumentoTipoDTO dto){
        this.dotCodigo = dto.codigo();
        this.docCodigo = new SgadDocumento(dto.documentoDTO().codigo());
        this.tidCodigo = new SgadTipoDocumental(dto.tipoDocumentalDTO().codigo());
        this.audAdicionado = dto.audAdicionado();
    }

    @PrePersist
    public void prePersist(){
        this.audFechaAdicion = LocalDateTime.now();
        this.audEliminado = "N";
    }

    @PreUpdate
    public void preUpdate(){
        this.audFechaModificacion = LocalDateTime.now();
        this.audEliminado = "N";
    }

    public Long getDotCodigo() {
        return dotCodigo;
    }

    public void setDotCodigo(Long dotCodigo) {
        this.dotCodigo = dotCodigo;
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

    public SgadDocumento getDocCodigo() {
        return docCodigo;
    }

    public void setDocCodigo(SgadDocumento docCodigo) {
        this.docCodigo = docCodigo;
    }

    public SgadTipoDocumental getTidCodigo() {
        return tidCodigo;
    }

    public void setTidCodigo(SgadTipoDocumental tidCodigo) {
        this.tidCodigo = tidCodigo;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dotCodigo != null ? dotCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadDocumentoTipo)) {
            return false;
        }
        SgadDocumentoTipo other = (SgadDocumentoTipo) object;
        if ((this.dotCodigo == null && other.dotCodigo != null) || (this.dotCodigo != null && !this.dotCodigo.equals(other.dotCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadDocumentoTipo[ dotCodigo=" + dotCodigo + " ]";
    }
    
}
