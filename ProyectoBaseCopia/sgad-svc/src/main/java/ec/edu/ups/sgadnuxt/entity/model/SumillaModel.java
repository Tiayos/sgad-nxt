package ec.edu.ups.sgadnuxt.entity.model;

import ec.edu.ups.sgadnuxt.entity.model.gth.GthPersona;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "SGAD_SUMILLA", schema = "SGAD")
public class SumillaModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEQUENCE_NAME = "SEQ_SUMILLA";

    @Id
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1,
            initialValue = 1, schema = "SGAD")
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @Column(name = "SUM_CODIGO")
    private Long codigo;
    @Column(name = "SUM_FECHA")
    private LocalDate fechaSumilla;
    @Column(name = "SUM_HORA")
    private String horaSumilla;

    @Column(name = "SUM_NOMBRE")
    private String nombreResponsableSumilla;

    @Column(name = "SUM_APELLIDO")
    private String apellidoResponsableSumilla;

    @Column(name = "SUM_NUM_HOJAS")
    private Integer numeroHojas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CODIGO_MENSAJERO", referencedColumnName = "PER_CODIGO")
    private GthPersona mensajero;

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

    public SumillaModel() {
    }

    public SumillaModel(Long codigo) {
        this.codigo = codigo;
    }

    public SumillaModel(Long codigo, LocalDate fechaSumilla, String horaSumilla, String nombreResponsableSumilla, String apellidoResponsableSumilla, Integer numeroHojas, GthPersona mensajero, String eliminado, String adicionado, LocalDateTime fechaAdicion, String modificado, LocalDateTime fechaModificacion) {
        this.codigo = codigo;
        this.fechaSumilla = fechaSumilla;
        this.horaSumilla = horaSumilla;
        this.nombreResponsableSumilla = nombreResponsableSumilla;
        this.apellidoResponsableSumilla = apellidoResponsableSumilla;
        this.numeroHojas = numeroHojas;
        this.mensajero = mensajero;
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

    public LocalDate getFechaSumilla() {
        return fechaSumilla;
    }

    public void setFechaSumilla(LocalDate fechaSumilla) {
        this.fechaSumilla = fechaSumilla;
    }

    public String getHoraSumilla() {
        return horaSumilla;
    }

    public void setHoraSumilla(String horaSumilla) {
        this.horaSumilla = horaSumilla;
    }

    public String getNombreResponsableSumilla() {
        return nombreResponsableSumilla;
    }

    public void setNombreResponsableSumilla(String nombreResponsableSumilla) {
        this.nombreResponsableSumilla = nombreResponsableSumilla;
    }

    public String getApellidoResponsableSumilla() {
        return apellidoResponsableSumilla;
    }

    public void setApellidoResponsableSumilla(String apellidoResponsableSumilla) {
        this.apellidoResponsableSumilla = apellidoResponsableSumilla;
    }

    public Integer getNumeroHojas() {
        return numeroHojas;
    }

    public void setNumeroHojas(Integer numeroHojas) {
        this.numeroHojas = numeroHojas;
    }

    public GthPersona getMensajero() {
        return mensajero;
    }

    public void setMensajero(GthPersona mensajero) {
        this.mensajero = mensajero;
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
