package ec.edu.ups.sgadnuxt.entity.model.sgad;

import ec.edu.ups.sgadnuxt.entity.dto.sgad.DocumentosBitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.EstadoDTO;
import ec.edu.ups.sgadnuxt.entity.model.BitacoraModel;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "SGAD_DOCUMENTOS_BITACORA", schema = "SGAD")
public class SgadDocumentoBitacora implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEQUENCE_NAME = "SEQ_DOCUMENTOS_BITACORA";

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1,
            initialValue = 1, schema = "SGAD")
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @Column(name = "DOC_CODIGO")
    private Long docCodigo;
    @Column(name = "DOC_ARCHIVO", columnDefinition="BLOB")
    private byte[] docArchivo;

    @Basic(optional = false)
    @Column(name = "DOC_NOMBRE_ARCHIVO")
    private String docNombreArchivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BIT_CODIGO", referencedColumnName = "BIT_CODIGO")
    private BitacoraModel bitacoraModel;

    @Column(name = "DOC_ESTADO_TRAMITE")
    private String estadoTramite;

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

    public SgadDocumentoBitacora() {
    }

    public SgadDocumentoBitacora(Long docCodigo) {
        this.docCodigo = docCodigo;
    }

    public SgadDocumentoBitacora(Long docCodigo, byte[] docArchivo, String docNombreArchivo, BitacoraModel bitacoraModel, String estadoTramite, String audAdicionado, String audEliminado, LocalDateTime audFechaAdicion, LocalDateTime audFechaModificacion, String audModificado) {
        this.docCodigo = docCodigo;
        this.docArchivo = docArchivo;
        this.docNombreArchivo = docNombreArchivo;
        this.bitacoraModel = bitacoraModel;
        this.estadoTramite = estadoTramite;
        this.audAdicionado = audAdicionado;
        this.audEliminado = audEliminado;
        this.audFechaAdicion = audFechaAdicion;
        this.audFechaModificacion = audFechaModificacion;
        this.audModificado = audModificado;
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
        this.audModificado="sgad@ups.edu.ec";
    }

    public SgadDocumentoBitacora(DocumentosBitacoraDTO dto){
        this.docCodigo = dto.codigo();
        this.docArchivo = dto.docArchivo();
        this.bitacoraModel = dto.bitacora()!= null ? new BitacoraModel(dto.bitacora().codigo()) : null;
        this.audAdicionado = dto.adicionado();
        this.docNombreArchivo = dto.docNombreArchivo();
        this.estadoTramite = dto.estadoTramite();
    }

    public Long getDocCodigo() {
        return docCodigo;
    }

    public void setDocCodigo(Long docCodigo) {
        this.docCodigo = docCodigo;
    }

    public byte[] getDocArchivo() {
        return docArchivo;
    }

    public void setDocArchivo(byte[] docArchivo) {
        this.docArchivo = docArchivo;
    }

    public BitacoraModel getBitacoraModel() {
        return bitacoraModel;
    }

    public void setBitacoraModel(BitacoraModel bitacoraModel) {
        this.bitacoraModel = bitacoraModel;
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

    public String getDocNombreArchivo() {
        return docNombreArchivo;
    }

    public void setDocNombreArchivo(String docNombreArchivo) {
        this.docNombreArchivo = docNombreArchivo;
    }

    public String getEstadoTramite() {
        return estadoTramite;
    }

    public void setEstadoTramite(String estadoTramite) {
        this.estadoTramite = estadoTramite;
    }
}
