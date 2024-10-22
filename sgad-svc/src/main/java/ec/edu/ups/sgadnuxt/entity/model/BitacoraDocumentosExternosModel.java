package ec.edu.ups.sgadnuxt.entity.model;
import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDocumentosExternosDTO;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "SGAD_BIT_DOCUMENTOS_EXTERNOS", schema = "SGAD")
public class BitacoraDocumentosExternosModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEQUENCE_NAME = "SEQ_BITACORA_DOCUMENTOS_EXTERNOS";

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1,
            initialValue = 1, schema = "SGAD")
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @Column(name = "BID_CODIGO")
    private Long codigo;
    @Column(name = "BID_NOMBRES_REMITENTE")
    private String nombresRemitente;
    @Column(name = "BID_APELLIDOS_REMITENTE")
    private String apellidosRemitente;
    @Column(name = "BID_NOMBRE_COMPLETO")
    private String nombreCompletoDestinatario;
    @Column(name = "BID_ASUNTO")
    private String asunto;
    @Column(name = "BID_CORREO_REMITENTE")
    private String correoRemitente;
    @Column(name = "BID_CORREO_DESTINATARIO")
    private String correoDestinatario;
    @Column(name = "BID_ESTADO")
    private Long estado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUM_CODIGO", referencedColumnName = "SUM_CODIGO")
    private SumillaModel sumilla;
    @Column(name = "BID_OBSERVACION")
    private String observacion;
    @Column(name = "BID_NOMBRE_ORGANIZACION")
    private String nombreOrganizacion;
    @Column(name = "BID_CODIGO_CONSULTA")
    private String codigoConsulta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CODIGO", referencedColumnName = "PER_CODIGO")
    private GthPersona destinatario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CODIGO_RESPONSABLE", referencedColumnName = "PER_CODIGO")
    private GthPersona responsable;
    @Column(name = "AUD_ELIMINADO")
    private String eliminado;
    @Column(name = "AUD_ADICIONADO")
    private String adicionado;
    @Column(name = "AUD_FECHA_ADICION")
    private LocalDateTime fechaAdicion;
    @Column(name = "AUD_MODIFICADO")
    private String modificado;
    @Column(name = "AUD_FECHA_MODIFICACION")
    private LocalDateTime fechaModificacion;

    public BitacoraDocumentosExternosModel() {
    }

    public BitacoraDocumentosExternosModel(Long codigo) {
        this.codigo = codigo;
    }

    public BitacoraDocumentosExternosModel(BitacoraDocumentosExternosDTO dto) {
        this.codigo = dto.codigo();
        this.nombresRemitente = dto.nombresRemitente();
        this.apellidosRemitente = dto.apellidosRemitente();
        this.asunto = dto.asunto();
        this.correoRemitente = dto.correoRemitente();
        this.correoDestinatario = dto.correoDestinatario();
        this.sumilla = dto.sumilla() != null ? new SumillaModel(dto.sumilla().codigo()) : null;
        this.adicionado = dto.adicionado();
        this.nombreCompletoDestinatario = dto.nombreCompletoDestinatario();
        this.estado = dto.estado();
        this.destinatario = dto.destinatario() != null ? new GthPersona(dto.destinatario().codigo()) : null;
        this.responsable = dto.responsable() != null ? new GthPersona(dto.responsable().codigo()) : null;
        this.observacion = dto.observacion();
        this.nombreOrganizacion = dto.nombreOrganizacion();
        this.codigoConsulta = dto.codigoConsulta();

    }

    @PrePersist
    public void prePersist(){
        this.fechaAdicion = LocalDateTime.now();
        this.eliminado = "N";
    }

    @PreUpdate
    public void preUpdate(){
        this.fechaModificacion = LocalDateTime.now();
        this.eliminado = "N";
        this.modificado="sgad@ups.edu.ec";
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombresRemitente() {
        return nombresRemitente;
    }

    public void setNombresRemitente(String nombresRemitente) {
        this.nombresRemitente = nombresRemitente;
    }

    public String getApellidosRemitente() {
        return apellidosRemitente;
    }

    public void setApellidosRemitente(String apellidosRemitente) {
        this.apellidosRemitente = apellidosRemitente;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCorreoRemitente() {
        return correoRemitente;
    }

    public void setCorreoRemitente(String correoRemitente) {
        this.correoRemitente = correoRemitente;
    }

    public String getCorreoDestinatario() {
        return correoDestinatario;
    }

    public void setCorreoDestinatario(String correoDestinatario) {
        this.correoDestinatario = correoDestinatario;
    }

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }

    public String getAdicionado() {
        return adicionado;
    }

    public void setAdicionado(String adicionado) {
        this.adicionado = adicionado;
    }

    public LocalDateTime getFechaAdicion() {
        return fechaAdicion;
    }

    public void setFechaAdicion(LocalDateTime fechaAdicion) {
        this.fechaAdicion = fechaAdicion;
    }

    public String getModificado() {
        return modificado;
    }

    public void setModificado(String modificado) {
        this.modificado = modificado;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public SumillaModel getSumilla() {
        return sumilla;
    }

    public void setSumilla(SumillaModel sumilla) {
        this.sumilla = sumilla;
    }

    public String getNombreCompletoDestinatario() {
        return nombreCompletoDestinatario;
    }

    public void setNombreCompletoDestinatario(String nombreCompletoDestinatario) {
        this.nombreCompletoDestinatario = nombreCompletoDestinatario;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public GthPersona getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(GthPersona destinatario) {
        this.destinatario = destinatario;
    }

    public GthPersona getResponsable() {
        return responsable;
    }

    public void setResponsable(GthPersona responsable) {
        this.responsable = responsable;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public String getCodigoConsulta() {
        return codigoConsulta;
    }

    public void setCodigoConsulta(String codigoConsulta) {
        this.codigoConsulta = codigoConsulta;
    }
}
