package ec.edu.ups.sgadnuxt.entity.model.sgad;

import ec.edu.ups.sgadnuxt.entity.dto.sgad.EventoBitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.model.BitacoraModel;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "SGAD_EVENTO_BITACORA", schema = "SGAD")
public class SgadEventoBitacora implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEQUENCE_NAME = "SEQ_EVENTO_BITACORA";

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1,
            initialValue = 1, schema = "SGAD")
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "EBI_CODIGO")
    private Long ebiCodigo;
    @Basic(optional = false)
    @Column(name = "EBI_FECHA")
    private LocalDate ebiFecha;
    @Basic(optional = false)
    @Column(name = "EBI_VIGENCIA")
    private char ebiVigencia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BIT_CODIGO", referencedColumnName = "BIT_CODIGO")
    private BitacoraModel bitacoraModel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EST_CODIGO", referencedColumnName = "EST_CODIGO")
    private SgadEstado sgadEstado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CODIGO", referencedColumnName = "PER_CODIGO")
    private GthPersona perCodigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CODIGO_REASIGNADO", referencedColumnName = "PER_CODIGO")
    private GthPersona perCodigoReasignado;
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

    public SgadEventoBitacora() {
    }

    public SgadEventoBitacora(Long ebiCodigo) {
        this.ebiCodigo = ebiCodigo;
    }

    public SgadEventoBitacora(Long ebiCodigo, LocalDate ebiFecha, char ebiVigencia, LocalDate ebiFecha1, BitacoraModel bitacoraModel, SgadEstado sgadEstado) {
        this.ebiCodigo = ebiCodigo;
        this.ebiFecha = ebiFecha;
        this.ebiVigencia = ebiVigencia;
        this.bitacoraModel = bitacoraModel;
        this.sgadEstado = sgadEstado;
    }
    public SgadEventoBitacora(EventoBitacoraDTO dto) {
        this.ebiCodigo = dto.codigo();
        this.ebiFecha = dto.fecha();
        this.ebiVigencia = dto.vigencia();
        this.bitacoraModel = new BitacoraModel(dto.bitacora().codigo());
        this.sgadEstado = new SgadEstado(dto.estado().codigo());
        this.audAdicionado = dto.adicionado();
        this.perCodigo = dto.perCodigoResponsable() != null ? new GthPersona(dto.perCodigoResponsable().codigo()): null;
        this.perCodigoReasignado = dto.perCodigoReasignado() != null ? new GthPersona(dto.perCodigoReasignado().codigo()) : null;

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


    public Long getEbiCodigo() {
        return ebiCodigo;
    }

    public void setEbiCodigo(Long ebiCodigo) {
        this.ebiCodigo = ebiCodigo;
    }

    public LocalDate getEbiFecha() {
        return ebiFecha;
    }

    public void setEbiFecha(LocalDate ebiFecha) {
        this.ebiFecha = ebiFecha;
    }

    public char getEbiVigencia() {
        return ebiVigencia;
    }

    public void setEbiVigencia(char ebiVigencia) {
        this.ebiVigencia = ebiVigencia;
    }

    public BitacoraModel getBitacoraModel() {
        return bitacoraModel;
    }

    public void setBitacoraModel(BitacoraModel bitacoraModel) {
        this.bitacoraModel = bitacoraModel;
    }

    public SgadEstado getSgadEstado() {
        return sgadEstado;
    }

    public void setSgadEstado(SgadEstado sgadEstado) {
        this.sgadEstado = sgadEstado;
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

    public GthPersona getPerCodigo() {
        return perCodigo;
    }

    public void setPerCodigo(GthPersona perCodigo) {
        this.perCodigo = perCodigo;
    }

    public GthPersona getPerCodigoReasignado() {
        return perCodigoReasignado;
    }

    public void setPerCodigoReasignado(GthPersona perCodigoReasignado) {
        this.perCodigoReasignado = perCodigoReasignado;
    }
}
