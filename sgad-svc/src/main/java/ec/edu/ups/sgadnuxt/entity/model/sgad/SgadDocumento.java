
package ec.edu.ups.sgadnuxt.entity.model.sgad;

import ec.edu.ups.sgadnuxt.entity.dto.sgad.DocumentoDTO;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "SGAD_DOCUMENTO", schema = "SGAD")
public class SgadDocumento implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEQUENCE_NAME = "SEQ_DOCUMENTO";

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1, initialValue = 1, schema = "SGAD")
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @Column(name = "DOC_CODIGO", nullable = false)
    private Long docCodigo;
    @Basic(optional = false)
    @Column(name = "DOC_ASUNTO")
    private String docAsunto;
    @Column(name = "DOC_FECHA_DOCU")
    private LocalDate docFechaDocu;
    @Column(name = "DOC_GRUPO")
    private Long docGrupo;
    @Column(name = "AUD_ADICIONADO")
    private String audAdicionado;
    @Column(name = "AUD_ELIMINADO")
    private String audEliminado;
    @Column(name = "AUD_FECHA_ADICION")
    private LocalDateTime audFechaAdicion;
    @Column(name = "AUD_MODIFICADO")
    private String audModificado;
    @Column(name = "AUD_FECHA_MODIFICACION")
    private LocalDateTime audFechaModificacion;
    @Column(name = "DOC_NUM_COPIAS")
    private Long docNumCopias;
    @Column(name = "DOC_PAGINAS")
    private Long docPaginas;
    @Column(name = "DOC_ASIGNACION")
    private String docAsignacion;
    @Column(name = "DOC_NUM_ORDEN_LN")
    private Long docNumOrdenLn;
    @JoinColumn(name = "DET_CODIGO", referencedColumnName = "DET_CODIGO")
    @ManyToOne
    private SgadDetalleTransferencia detCodigo;

    public SgadDocumento() {
    }

    public SgadDocumento(Long docCodigo) {
        this.docCodigo = docCodigo;
    }

    public SgadDocumento(Long docCodigo, String docAsunto) {
        this.docCodigo = docCodigo;
        this.docAsunto = docAsunto;
    }

    public SgadDocumento(DocumentoDTO dto) {
        this.docCodigo = dto.codigo();
        this.docAsunto = dto.docAsunto();
        this.docPaginas = dto.docPaginas();
        this.detCodigo = new SgadDetalleTransferencia();
        this.docNumOrdenLn = dto.docNumOrden();
        this.audAdicionado = dto.audAdicionado();
        this.docFechaDocu = dto.docFechaDocu();
    }

    @PrePersist
    public void prePersist() {
        this.audFechaAdicion = LocalDateTime.now();
        this.audEliminado = "N";
        this.docNumCopias = 0L;
    }

    @PreUpdate
    public void preUpdate() {
        this.audFechaModificacion = LocalDateTime.now();
        this.audEliminado = "N";
//        this.audModificado="";
    }

    public Long getDocCodigo() {
        return docCodigo;
    }

    public void setDocCodigo(Long docCodigo) {
        this.docCodigo = docCodigo;
    }

    public String getAudAdicionado() {
        return audAdicionado;
    }

    public void setAudAdicionado(String audAdicionado) {
        this.audAdicionado = audAdicionado;
    }

    public String getDocAsunto() {
        return docAsunto;
    }

    public void setDocAsunto(String docAsunto) {
        this.docAsunto = docAsunto;
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

    public LocalDate getDocFechaDocu() {
        return docFechaDocu;
    }

    public void setDocFechaDocu(LocalDate docFechaDocu) {
        this.docFechaDocu = docFechaDocu;
    }

    public LocalDateTime getAudFechaModificacion() {
        return audFechaModificacion;
    }

    public void setAudFechaModificacion(LocalDateTime audFechaModificacion) {
        this.audFechaModificacion = audFechaModificacion;
    }

    public Long getDocGrupo() {
        return docGrupo;
    }

    public void setDocGrupo(Long docGrupo) {
        this.docGrupo = docGrupo;
    }

    public String getAudModificado() {
        return audModificado;
    }

    public void setAudModificado(String audModificado) {
        this.audModificado = audModificado;
    }

    public Long getDocNumCopias() {
        return docNumCopias;
    }

    public void setDocNumCopias(Long docNumCopias) {
        this.docNumCopias = docNumCopias;
    }

    public Long getDocPaginas() {
        return docPaginas;
    }

    public void setDocPaginas(Long docPaginas) {
        this.docPaginas = docPaginas;
    }

    public String getDocAsignacion() {
        return docAsignacion;
    }

    public void setDocAsignacion(String docAsignacion) {
        this.docAsignacion = docAsignacion;
    }

    public Long getDocNumOrdenLn() {
        return docNumOrdenLn;
    }

    public void setDocNumOrdenLn(Long docNumOrdenLn) {
        this.docNumOrdenLn = docNumOrdenLn;
    }

    public SgadDetalleTransferencia getDetCodigo() {
        return detCodigo;
    }

    public void setDetCodigo(SgadDetalleTransferencia detCodigo) {
        this.detCodigo = detCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docCodigo != null ? docCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadDocumento)) {
            return false;
        }
        SgadDocumento other = (SgadDocumento) object;
        if ((this.docCodigo == null && other.docCodigo != null) || (this.docCodigo != null && !this.docCodigo.equals(other.docCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadDocumento[ docCodigo=" + docCodigo + " ]";
    }

}
