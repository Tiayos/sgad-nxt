package ec.edu.ups.sgadnuxt.entity.model;

import ec.edu.ups.sgadnuxt.entity.dto.SumillaDTO;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PER_CODIGO_RESPONSABLE", referencedColumnName = "PER_CODIGO")
    private GthPersona responsable;

    @Column(name = "SUM_NUM_HOJAS")
    private Integer numeroHojas;

    @Column(name = "SUM_NUMERO_SUMILLA")
    private String numeroSumilla;

    @Column(name = "SUM_NUMERO_TRAMITE")
    private Long numeroTramite;

    @Column(name = "SUM_SEDE")
    private Long sumSede;

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

    public SumillaModel(SumillaDTO dto) {
        this.codigo = dto.codigo();
        this.fechaSumilla = dto.fechaSumilla();
        this.horaSumilla = dto.horaSumilla();
        this.responsable = new GthPersona(dto.responsable().codigo());
        this.numeroHojas = dto.numeroHojas();
        this.numeroSumilla = dto.numeroSumilla();
        this.numeroTramite = dto.numeroTramite();
        this.sumSede = dto.sumSede();
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

    public GthPersona getResponsable() {
        return responsable;
    }

    public void setResponsable(GthPersona responsable) {
        this.responsable = responsable;
    }

    public Integer getNumeroHojas() {
        return numeroHojas;
    }

    public void setNumeroHojas(Integer numeroHojas) {
        this.numeroHojas = numeroHojas;
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

    public String getNumeroSumilla() {
        return numeroSumilla;
    }

    public void setNumeroSumilla(String numeroSumilla) {
        this.numeroSumilla = numeroSumilla;
    }

    public Long getNumeroTramite() {
        return numeroTramite;
    }

    public void setNumeroTramite(Long numeroTramite) {
        this.numeroTramite = numeroTramite;
    }

    public Long getSumSede() {
        return sumSede;
    }

    public void setSumSede(Long sumSede) {
        this.sumSede = sumSede;
    }
}
