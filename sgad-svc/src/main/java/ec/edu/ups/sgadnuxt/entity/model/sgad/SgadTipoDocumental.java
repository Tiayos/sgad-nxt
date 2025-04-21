package ec.edu.ups.sgadnuxt.entity.model.sgad;

import ec.edu.ups.sgadnuxt.entity.dto.sgad.TipoDocumentalDTO;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "SGAD_TIPO_DOCUMENTAL")
public class SgadTipoDocumental implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sgad_documento_tipo_seq")
    @SequenceGenerator(name = "sgad_documento_tipo_seq", sequenceName = "SGAD.SEQ_DOCUMENTO_TIPO", allocationSize = 1)
    @Column(name = "TID_CODIGO")
    private Long tidCodigo;
    @Column(name = "AUD_ADICIONADO")
    private String audAdicionado;
    @Basic(optional = false)
    @Column(name = "TID_DESCRIPCION")
    private String tidDescripcion;
    @Column(name = "AUD_ELIMINADO")
    private String audEliminado;
    @Column(name = "AUD_FECHA_ADICION")
    private LocalDateTime audFechaAdicion;
    @Column(name = "AUD_FECHA_MODIFICACION")
    private LocalDateTime audFechaModificacion;
    @Column(name = "AUD_MODIFICADO")
    private String audModificado;
    @OneToMany(mappedBy = "tidCodigo")
    private Collection<SgadDocumentoTipo> sgadDocumentoTipoCollection;

    public SgadTipoDocumental() {
    }

    public SgadTipoDocumental(Long tidCodigo) {
        this.tidCodigo = tidCodigo;
    }

    public SgadTipoDocumental(Long tidCodigo, String tidDescripcion) {
        this.tidCodigo = tidCodigo;
        this.tidDescripcion = tidDescripcion;
    }
    public SgadTipoDocumental(TipoDocumentalDTO dto){
        this.tidCodigo = dto.codigo();
        this.tidDescripcion = dto.descripcion();
    }

    public Long getTidCodigo() {
        return tidCodigo;
    }

    public void setTidCodigo(Long tidCodigo) {
        this.tidCodigo = tidCodigo;
    }

    public String getAudAdicionado() {
        return audAdicionado;
    }

    public void setAudAdicionado(String audAdicionado) {
        this.audAdicionado = audAdicionado;
    }

    public String getTidDescripcion() {
        return tidDescripcion;
    }

    public void setTidDescripcion(String tidDescripcion) {
        this.tidDescripcion = tidDescripcion;
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

    public Collection<SgadDocumentoTipo> getSgadDocumentoTipoCollection() {
        return sgadDocumentoTipoCollection;
    }

    public void setSgadDocumentoTipoCollection(Collection<SgadDocumentoTipo> sgadDocumentoTipoCollection) {
        this.sgadDocumentoTipoCollection = sgadDocumentoTipoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tidCodigo != null ? tidCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadTipoDocumental)) {
            return false;
        }
        SgadTipoDocumental other = (SgadTipoDocumental) object;
        if ((this.tidCodigo == null && other.tidCodigo != null) || (this.tidCodigo != null && !this.tidCodigo.equals(other.tidCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadTipoDocumental[ tidCodigo=" + tidCodigo + " ]";
    }
    
}
