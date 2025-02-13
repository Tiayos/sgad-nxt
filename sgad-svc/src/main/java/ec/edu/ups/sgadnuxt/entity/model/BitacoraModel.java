package ec.edu.ups.sgadnuxt.entity.model;

import ec.edu.ups.sgadnuxt.entity.dto.BitacoraDTO;
import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import ec.edu.ups.sgadnuxt.entity.model.sgad.SgadEstado;
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
    @JoinColumn(name = "PER_CODIGO_RECEPTOR", referencedColumnName = "PER_CODIGO")
    private GthPersona usrReceptorDocumento;
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
    @Column(name = "BIT_FECHA_RECEPCION")
    private LocalDate fechaRecepcion;
    @Column(name = "BIT_HORA_RECEPCION")
    private String horaRecepcion;
    @Column(name = "BIT_ESTADO_TRANSFERENCIA")
    private char estadoTransferencia;
    @Column(name = "BIT_DOC_ARCHIVO", columnDefinition="BLOB")
    private byte[] docArchivo;
    @Column(name = "BIT_NOMBRE_ARCHIVO", columnDefinition="BLOB")
    private String nombreArchivo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUM_CODIGO", referencedColumnName = "SUM_CODIGO")
    private SumillaModel sumilla;
    @Column(name = "BIT_MENSAJERO_EXTERNO")
    private String mensajeroExterno;

    @Column(name = "BIT_SECUENCIAL_SEDE")
    private Long secuencialSede;

    @Column(name = "BIT_NUMERO_TRAMITE")
    private Long numeroTramite;

    @Column(name = "BIT_SECUENCIAL_DOCUMENTO")
    private Long secuencialDocumento;

    @Column(name = "PER_CODIGO_RECEPCION_REASIGNADO")
    private Long codigoRecepcionReasignado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_COD_RECIBE_DOCUMENTACION", referencedColumnName = "PER_CODIGO")
    private GthPersona perCodigoRecibeDocumentacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_COD_ENTREGA_DOCUMENTACION", referencedColumnName = "PER_CODIGO")
    private GthPersona perCodigoEntregaDocumentacion;

    @Column(name = "BIT_FECHA_ENTREGA_DOCUMENTACION")
    private LocalDate fechaEntregaDocumentacion;
    @Column(name = "BIT_HORA_ENTREGA_DOCUMENTACION")
    private String horaEntregaDocumentacion;

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

    public BitacoraModel(BitacoraDTO dto) {
        this.codigo = dto.codigo();
        this.nombresRemitente = dto.nombresRemitente();
        this.apellidosRemitente = dto.apellidosRemitente();
        this.usrReceptorDocumento = dto.receptorDocumento() != null ? new GthPersona(dto.receptorDocumento().codigo()) : null;
        this.destinatario = dto.destinatario() != null ? new GthPersona(dto.destinatario().codigo()) : null;
        this.asunto = dto.asunto();
        this.lugarDestino = dto.lugarDestino();
        this.mensajero = dto.mensajero() != null ? new GthPersona(dto.mensajero().codigo()): null;
        this.numeroGuia = dto.numeroGuia();
        this.observaciones = dto.observaciones();
        this.usrEmisor = dto.usrEmisor() != null ? new GthPersona(dto.usrEmisor().codigo()) : null;
        this.receptor = dto.usrReceptor() != null ? new GthPersona(dto.usrReceptor().codigo()) : null;
        this.fechaEntrega = dto.fechaEntrega();
        this.horaEntrega = dto.horaEntrega();
        this.fechaRecepcion = dto.fechaRecepcion();
        this.horaRecepcion = dto.horaRecepcion();
        this.sumilla = new SumillaModel(dto.sumilla().codigo());
        this.docArchivo = dto.docArchivo();
        this.nombreArchivo = dto.nombreArchivo();
        this.estadoTransferencia = dto.estadoTransferencia();
        this.adicionado = dto.adicionado();
        this.mensajeroExterno = dto.mensajeroExterno();
        this.secuencialSede = dto.secuencialSede();
        this.numeroTramite = dto.numeroTramite();
        this.secuencialDocumento = dto.secuencialDocumento();
        this.codigoRecepcionReasignado = dto.codigoRecepcionReasignado();
        this.perCodigoEntregaDocumentacion = dto.perCodigoEntregaDocumentacion() != null ? new GthPersona(dto.perCodigoEntregaDocumentacion().codigo()) : null;
        this.perCodigoRecibeDocumentacion = dto.perCodigoRecibeDocumentacion() != null ? new GthPersona(dto.perCodigoRecibeDocumentacion().codigo()) : null;
        this.fechaEntregaDocumentacion = dto.fechaEntregaDocumentacion();
        this.horaEntregaDocumentacion = dto.horaEntreagaDocumentacion();
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

    public SumillaModel getSumilla() {
        return sumilla;
    }

    public void setSumilla(SumillaModel sumilla) {
        this.sumilla = sumilla;
    }

    public LocalDate getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getHoraRecepcion() {
        return horaRecepcion;
    }

    public void setHoraRecepcion(String horaRecepcion) {
        this.horaRecepcion = horaRecepcion;
    }

    public GthPersona getUsrReceptorDocumento() {
        return usrReceptorDocumento;
    }

    public void setUsrReceptorDocumento(GthPersona usrReceptorDocumento) {
        this.usrReceptorDocumento = usrReceptorDocumento;
    }

    public byte[] getDocArchivo() {
        return docArchivo;
    }

    public void setDocArchivo(byte[] docArchivo) {
        this.docArchivo = docArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public char getEstadoTransferencia() {
        return estadoTransferencia;
    }

    public void setEstadoTransferencia(char estadoTransferencia) {
        this.estadoTransferencia = estadoTransferencia;
    }

    public String getMensajeroExterno() {
        return mensajeroExterno;
    }

    public void setMensajeroExterno(String mensajeroExterno) {
        this.mensajeroExterno = mensajeroExterno;
    }

    public Long getSecuencialSede() {
        return secuencialSede;
    }

    public void setSecuencialSede(Long secuencialSede) {
        this.secuencialSede = secuencialSede;
    }

    public Long getSecuencialDocumento() {
        return secuencialDocumento;
    }

    public void setSecuencialDocumento(Long secuencialDocumento) {
        this.secuencialDocumento = secuencialDocumento;
    }

    public Long getCodigoRecepcionReasignado() {
        return codigoRecepcionReasignado;
    }

    public void setCodigoRecepcionReasignado(Long codigoRecepcionReasignado) {
        this.codigoRecepcionReasignado = codigoRecepcionReasignado;
    }

    public GthPersona getPerCodigoRecibeDocumentacion() {
        return perCodigoRecibeDocumentacion;
    }

    public void setPerCodigoRecibeDocumentacion(GthPersona perCodigoRecibeDocumentacion) {
        this.perCodigoRecibeDocumentacion = perCodigoRecibeDocumentacion;
    }

    public GthPersona getPerCodigoEntregaDocumentacion() {
        return perCodigoEntregaDocumentacion;
    }

    public void setPerCodigoEntregaDocumentacion(GthPersona perCodigoEntregaDocumentacion) {
        this.perCodigoEntregaDocumentacion = perCodigoEntregaDocumentacion;
    }

    public LocalDate getFechaEntregaDocumentacion() {
        return fechaEntregaDocumentacion;
    }

    public void setFechaEntregaDocumentacion(LocalDate fechaEntregaDocumentacion) {
        this.fechaEntregaDocumentacion = fechaEntregaDocumentacion;
    }

    public String getHoraEntregaDocumentacion() {
        return horaEntregaDocumentacion;
    }

    public void setHoraEntregaDocumentacion(String horaEntregaDocumentacion) {
        this.horaEntregaDocumentacion = horaEntregaDocumentacion;
    }

    public Long getNumeroTramite() {
        return numeroTramite;
    }

    public void setNumeroTramite(Long numeroTramite) {
        this.numeroTramite = numeroTramite;
    }
}
