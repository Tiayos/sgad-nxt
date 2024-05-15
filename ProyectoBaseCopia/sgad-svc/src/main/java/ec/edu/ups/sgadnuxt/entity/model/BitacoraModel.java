package ec.edu.ups.sgadnuxt.entity.model;

import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "SGAD_BITACORA", schema = "SGAD")
public class BitacoraModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEQUENCE_NAME = "SEQ_BITACORA";

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1,
            initialValue = 1, schema = "SGAD")
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @Column(name = "BIT_CODIGO")
    private Long codigo;
    @Column(name = "BIT_NOMBRES_REMITENTE")
    private String nombresRemitente;
    @Column(name = "BIT_APELLIDOS_REMITENTE")
    private String apellidosRemitente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CODIGO_DESTINATARIO", referencedColumnName = "PER_CODIGO")
    private GthPersona destinatario;

    @Column(name = "BIT_ASUNTO")
    private String asunto;

    @Column(name = "BIT_LUGAR_DESTINO")
    private String lugarDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CODIGO_MENSAJERO", referencedColumnName = "PER_CODIGO")
    private GthPersona mensajero;

    @Column(name = "BIT_NUM_GUIA")
    private String numeroGuia;

    @Column(name = "BIT_REG_SGAD")
    private char registroSgad;

    @Column(name = "BIT_NUM_TRAMITE")
    private String numeroTramite;

    @Column(name = "BIT_OBSERVACIONES")
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CODIGO_ENTREGA", referencedColumnName = "PER_CODIGO")
    private GthPersona usrEmisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CODIGO_RECIBE", referencedColumnName = "PER_CODIGO")
    private GthPersona receptor;

    @Column(name = "BIT_FECHA_ENTREGA")
    private LocalDate fechaEntrega;

    @Column(name = "BIT_HORA_ENTREGA")
    private String horaEntrega;

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

    public BitacoraModel() {
    }

    public BitacoraModel(Long codigo) {
        this.codigo = codigo;
    }

    public BitacoraModel(Long codigo, String nombresRemitente, String apellidosRemitente, GthPersona destinatario, String asunto, String lugarDestino, GthPersona mensajero, String numeroGuia, char registroSgad, String numeroTramite, String observaciones, GthPersona usrEmisor, GthPersona receptor, LocalDate fechaEntrega, String horaEntrega, String eliminado, String adicionado, LocalDateTime fechaAdicion, String modificado, LocalDateTime fechaModificacion) {
        this.codigo = codigo;
        this.nombresRemitente = nombresRemitente;
        this.apellidosRemitente = apellidosRemitente;
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.lugarDestino = lugarDestino;
        this.mensajero = mensajero;
        this.numeroGuia = numeroGuia;
        this.registroSgad = registroSgad;
        this.numeroTramite = numeroTramite;
        this.observaciones = observaciones;
        this.usrEmisor = usrEmisor;
        this.receptor = receptor;
        this.fechaEntrega = fechaEntrega;
        this.horaEntrega = horaEntrega;
        this.eliminado = eliminado;
        this.adicionado = adicionado;
        this.fechaAdicion = fechaAdicion;
        this.modificado = modificado;
        this.fechaModificacion = fechaModificacion;
    }

    @PrePersist
    public void prePersist(){
        this.fechaAdicion = LocalDateTime.now();
        this.eliminado = "N";
        this.adicionado="sbermeob@ups.edu.ec";
    }

    @PreUpdate
    public void preUpdate(){
        this.fechaModificacion = LocalDateTime.now();
        this.eliminado = "N";
        this.modificado="sbermeob@ups.edu.ec";
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

    public GthPersona getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(GthPersona destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getLugarDestino() {
        return lugarDestino;
    }

    public void setLugarDestino(String lugarDestino) {
        this.lugarDestino = lugarDestino;
    }

    public GthPersona getMensajero() {
        return mensajero;
    }

    public void setMensajero(GthPersona mensajero) {
        this.mensajero = mensajero;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public char getRegistroSgad() {
        return registroSgad;
    }

    public void setRegistroSgad(char registroSgad) {
        this.registroSgad = registroSgad;
    }

    public String getNumeroTramite() {
        return numeroTramite;
    }

    public void setNumeroTramite(String numeroTramite) {
        this.numeroTramite = numeroTramite;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public GthPersona getUsrEmisor() {
        return usrEmisor;
    }

    public void setUsrEmisor(GthPersona usrEmisor) {
        this.usrEmisor = usrEmisor;
    }

    public GthPersona getReceptor() {
        return receptor;
    }

    public void setReceptor(GthPersona receptor) {
        this.receptor = receptor;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
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

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }
}
