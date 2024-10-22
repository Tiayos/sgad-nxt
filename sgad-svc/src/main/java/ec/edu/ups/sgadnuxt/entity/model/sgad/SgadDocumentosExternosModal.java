package ec.edu.ups.sgadnuxt.entity.model.sgad;

import ec.edu.ups.sgadnuxt.entity.dto.DocumentosExternosDTO;
import ec.edu.ups.sgadnuxt.entity.model.BitacoraDocumentosExternosModel;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "SGAD_DOCUMENTOS_EXTERNOS", schema = "SGAD")
public class SgadDocumentosExternosModal implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEQUENCE_NAME = "SEQ_NUMERO_DOCUMENTOS_EXTERNOS";

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1,
            initialValue = 1, schema = "SGAD")
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @Column(name = "DOE_CODIGO")
    private Long doeCodigo;
    @Column(name = "DOE_ARCHIVO", columnDefinition="BLOB")
    private byte[] doeArchivo;
    @Basic(optional = false)
    @Column(name = "DOE_NOMBRE_ARCHIVO")
    private String doeNombreArchivo;

    @Basic(optional = false)
    @Column(name = "DOE_ESTADO_DOC_ELECTRONICO")
    private char estadoDocumentoElectronico;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BID_CODIGO", referencedColumnName = "BID_CODIGO")
    private BitacoraDocumentosExternosModel bitacoraDocumentosExternosModel;
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

    public SgadDocumentosExternosModal() {
    }

    public SgadDocumentosExternosModal(Long doeCodigo) {
        this.doeCodigo = doeCodigo;
    }

    public SgadDocumentosExternosModal(DocumentosExternosDTO dto) {
        this.doeCodigo = dto.codigo();
        this.doeArchivo = dto.doeArchivo();
        this.doeNombreArchivo = dto.doeNombreArchivo();
        this.bitacoraDocumentosExternosModel = dto.documentosExternos() != null ? new BitacoraDocumentosExternosModel(dto.documentosExternos().codigo()) : null;
        this.audAdicionado = dto.adicionado();
        this.estadoDocumentoElectronico = dto.estadoDocumentoElectronico();
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

    public Long getDoeCodigo() {
        return doeCodigo;
    }

    public void setDoeCodigo(Long doeCodigo) {
        this.doeCodigo = doeCodigo;
    }

    public byte[] getDoeArchivo() {
        return doeArchivo;
    }

    public void setDoeArchivo(byte[] doeArchivo) {
        this.doeArchivo = doeArchivo;
    }

    public String getDoeNombreArchivo() {
        return doeNombreArchivo;
    }

    public void setDoeNombreArchivo(String doeNombreArchivo) {
        this.doeNombreArchivo = doeNombreArchivo;
    }

    public BitacoraDocumentosExternosModel getBitacoraDocumentosExternosModel() {
        return bitacoraDocumentosExternosModel;
    }

    public void setBitacoraDocumentosExternosModel(BitacoraDocumentosExternosModel bitacoraDocumentosExternosModel) {
        this.bitacoraDocumentosExternosModel = bitacoraDocumentosExternosModel;
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

    public char getEstadoDocumentoElectronico() {
        return estadoDocumentoElectronico;
    }

    public void setEstadoDocumentoElectronico(char estadoDocumentoElectronico) {
        this.estadoDocumentoElectronico = estadoDocumentoElectronico;
    }
}
