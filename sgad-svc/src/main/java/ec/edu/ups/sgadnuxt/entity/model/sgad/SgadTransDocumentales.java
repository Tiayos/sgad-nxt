package ec.edu.ups.sgadnuxt.entity.model.sgad;
import ec.edu.ups.sgadnuxt.entity.dto.sgad.TransDocumentalDTO;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "SGAD_TRANS_DOCUMENTALES", schema = "SGAD")
public class SgadTransDocumentales implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEQUENCE_NAME = "SEQ_TRANS_DOCU";

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1, initialValue = 1, schema = "SGAD")
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @Column(name = "TRD_CODIGO")
    private Long trdCodigo;
    @Column(name = "AMB_REC_CODIGO")
    private Long ambRecCodigo;
    @Column(name = "AMB_RES_CODIGO")
    private Long ambResCodigo;
    @Column(name = "CAR_REC_CODIGO")
    private Long carRecCodigo;
    @Column(name = "CAR_RES_CODIGO")
    private Long carResCodigo;
    @Column(name = "TRD_FECHA_EMISION")
    private LocalDate trdFechaEmision;
    @Column(name = "TRD_FECHA_RECEPCION")
    private LocalDate trdFechaRecepcion;
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
    @Basic(optional = false)
    @Column(name = "TRD_NUM_REGISTRO")
    private long trdNumRegistro;
    @Basic(optional = false)
    @Column(name = "TRD_NUM_UNIDAD")
    private long trdNumUnidad;
    @Column(name = "PER_REC_CODIGO")
    private Long perRecCodigo;
    @Column(name = "PER_RES_CODIGO")
    private Long perResCodigo;
    @Column(name = "TRD_TIPO_TRANSFE")
    private String trdTipoTransfe;
    @Column(name = "EMP_REC_CODIGO")
    private Long empRecCodigo;
    @Column(name = "EMP_CODIGO")
    private Long empCodigo;
    @Column(name = "TRD_ESTADO")
    private String trdEstado;
    @Column(name = "TRD_LEER_RES")
    private String trdLeerRes;
    @Column(name = "TRD_LEER_REC")
    private String trdLeerRec;
    @Column(name = "TRD_TIPO_ARCHIVO")
    private String trdTipoArchivo;

    public SgadTransDocumentales() {
    }

    public SgadTransDocumentales(Long trdCodigo) {
        this.trdCodigo = trdCodigo;
    }

    public SgadTransDocumentales(Long trdCodigo, long trdNumRegistro, long trdNumUnidad) {
        this.trdCodigo = trdCodigo;
        this.trdNumRegistro = trdNumRegistro;
        this.trdNumUnidad = trdNumUnidad;
    }

    public SgadTransDocumentales(TransDocumentalDTO dto) {
        this.trdCodigo = dto.codigo();
        this.ambRecCodigo = dto.ambRecCodigo();
        this.ambResCodigo = dto.ambResCodigo();
        this.carRecCodigo = dto.carRecCodigo();
        this.carResCodigo = dto.carResCodigo();
        this.trdFechaEmision = dto.fechaEmision();
        this.trdFechaRecepcion = dto.fechaRecepcion();
        this.trdNumRegistro = dto.numRegistro();
        this.trdNumUnidad = dto.numUnidad();
        this.perRecCodigo = dto.perRecCodigo();
        this.perResCodigo = dto.perResCodigo();
        this.trdTipoTransfe = dto.tipoTransferencia();
        this.empRecCodigo = dto.empRecCodigo();
        this.empCodigo = dto.empCodigo();
        this.trdEstado = dto.estado();
        this.trdLeerRes = dto.leerResponsable();
        this.trdLeerRec = dto.leerReceptor();
        this.trdTipoArchivo = dto.tipoArchivo();
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
//        this.audModificado="";
    }

    public Long getTrdCodigo() {
        return trdCodigo;
    }

    public void setTrdCodigo(Long trdCodigo) {
        this.trdCodigo = trdCodigo;
    }

    public String getAudAdicionado() {
        return audAdicionado;
    }

    public void setAudAdicionado(String audAdicionado) {
        this.audAdicionado = audAdicionado;
    }

    public Long getAmbRecCodigo() {
        return ambRecCodigo;
    }

    public void setAmbRecCodigo(Long ambRecCodigo) {
        this.ambRecCodigo = ambRecCodigo;
    }

    public Long getAmbResCodigo() {
        return ambResCodigo;
    }

    public void setAmbResCodigo(Long ambResCodigo) {
        this.ambResCodigo = ambResCodigo;
    }

    public Long getCarRecCodigo() {
        return carRecCodigo;
    }

    public void setCarRecCodigo(Long carRecCodigo) {
        this.carRecCodigo = carRecCodigo;
    }

    public Long getCarResCodigo() {
        return carResCodigo;
    }

    public void setCarResCodigo(Long carResCodigo) {
        this.carResCodigo = carResCodigo;
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

    public LocalDate getTrdFechaEmision() {
        return trdFechaEmision;
    }

    public void setTrdFechaEmision(LocalDate trdFechaEmision) {
        this.trdFechaEmision = trdFechaEmision;
    }

    public LocalDateTime getAudFechaModificacion() {
        return audFechaModificacion;
    }

    public void setAudFechaModificacion(LocalDateTime audFechaModificacion) {
        this.audFechaModificacion = audFechaModificacion;
    }

    public LocalDate getTrdFechaRecepcion() {
        return trdFechaRecepcion;
    }

    public void setTrdFechaRecepcion(LocalDate trdFechaRecepcion) {
        this.trdFechaRecepcion = trdFechaRecepcion;
    }

    public String getAudModificado() {
        return audModificado;
    }

    public void setAudModificado(String audModificado) {
        this.audModificado = audModificado;
    }

    public long getTrdNumRegistro() {
        return trdNumRegistro;
    }

    public void setTrdNumRegistro(long trdNumRegistro) {
        this.trdNumRegistro = trdNumRegistro;
    }

    public long getTrdNumUnidad() {
        return trdNumUnidad;
    }

    public void setTrdNumUnidad(long trdNumUnidad) {
        this.trdNumUnidad = trdNumUnidad;
    }

    public Long getPerRecCodigo() {
        return perRecCodigo;
    }

    public void setPerRecCodigo(Long perRecCodigo) {
        this.perRecCodigo = perRecCodigo;
    }

    public Long getPerResCodigo() {
        return perResCodigo;
    }

    public void setPerResCodigo(Long perResCodigo) {
        this.perResCodigo = perResCodigo;
    }

    public String getTrdTipoTransfe() {
        return trdTipoTransfe;
    }

    public void setTrdTipoTransfe(String trdTipoTransfe) {
        this.trdTipoTransfe = trdTipoTransfe;
    }

    public Long getEmpRecCodigo() {
        return empRecCodigo;
    }

    public void setEmpRecCodigo(Long empRecCodigo) {
        this.empRecCodigo = empRecCodigo;
    }

    public Long getEmpCodigo() {
        return empCodigo;
    }

    public void setEmpCodigo(Long empCodigo) {
        this.empCodigo = empCodigo;
    }

    public String getTrdEstado() {
        return trdEstado;
    }

    public void setTrdEstado(String trdEstado) {
        this.trdEstado = trdEstado;
    }

    public String getTrdLeerRes() {
        return trdLeerRes;
    }

    public void setTrdLeerRes(String trdLeerRes) {
        this.trdLeerRes = trdLeerRes;
    }

    public String getTrdLeerRec() {
        return trdLeerRec;
    }

    public void setTrdLeerRec(String trdLeerRec) {
        this.trdLeerRec = trdLeerRec;
    }

    public String getTrdTipoArchivo() {
        return trdTipoArchivo;
    }

    public void setTrdTipoArchivo(String trdTipoArchivo) {
        this.trdTipoArchivo = trdTipoArchivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trdCodigo != null ? trdCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgadTransDocumentales)) {
            return false;
        }
        SgadTransDocumentales other = (SgadTransDocumentales) object;
        if ((this.trdCodigo == null && other.trdCodigo != null) || (this.trdCodigo != null && !this.trdCodigo.equals(other.trdCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ups.edu.ec.sgadproject.SgadTransDocumentales[ trdCodigo=" + trdCodigo + " ]";
    }
    
}
