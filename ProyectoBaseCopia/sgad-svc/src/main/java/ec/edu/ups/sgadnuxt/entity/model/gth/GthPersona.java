package ec.edu.ups.sgadnuxt.entity.model.gth;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ups
 */
@Entity
@Table(name = "GTH_PERSONA", catalog = "", schema = "GTH", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"PER_NRO_SEGURO_SOCIAL"}),
    @UniqueConstraint(columnNames = {"PER_NRO_IDENTIFICACION"})})
@XmlRootElement
public class GthPersona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PER_CODIGO", nullable = false)
    private Long perCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "PER_NRO_IDENTIFICACION", nullable = false, length = 32)
    private String perNroIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PER_APELLIDOS", nullable = false, length = 50)
    private String perApellidos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PER_NOMBRES", nullable = false, length = 50)
    private String perNombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "PER_GENERO", nullable = false, length = 1)
    private String perGenero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PER_FECHA_NACIMIENTO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date perFechaNacimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PER_CALLE_PRINCIPAL", nullable = false, length = 50)
    private String perCallePrincipal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PER_NRO_CASA", nullable = false, length = 10)
    private String perNroCasa;
    @Size(max = 50)
    @Column(name = "PER_CALLE_SECUNDARIA", length = 50)
    private String perCalleSecundaria;
    @Size(max = 250)
    @Column(name = "PER_REFERENCIA", length = 250)
    private String perReferencia;
    @Size(max = 32)
    @Column(name = "PER_NRO_PASAPORTE", length = 32)
    private String perNroPasaporte;
    @Column(name = "PER_PAS_FECHA_EMISION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perPasFechaEmision;
    @Column(name = "PER_PAS_FECHA_VENCIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perPasFechaVencimiento;
    @Size(max = 20)
    @Column(name = "PER_CASILLA", length = 20)
    private String perCasilla;
    @Size(max = 20)
    @Column(name = "PER_NRO_SEGURO_SOCIAL", length = 20)
    private String perNroSeguroSocial;
    @Column(name = "PER_FECHA_AFILIACION_SEGURO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perFechaAfiliacionSeguro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "PER_ELIMINADO", nullable = false, length = 1)
    private String perEliminado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PER_ADICIONADO", nullable = false, length = 30)
    private String perAdicionado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PER_FECHA_ADICION", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date perFechaAdicion;
    @Size(max = 30)
    @Column(name = "PER_MODIFICADO", length = 30)
    private String perModificado;
    @Column(name = "PER_FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date perFechaModificacion;

    public GthPersona() {
    }

    public GthPersona(Long perCodigo) {
        this.perCodigo = perCodigo;
    }

    public GthPersona(Long perCodigo, String perNroIdentificacion, String perApellidos, String perNombres, String perGenero, Date perFechaNacimiento, String perCallePrincipal, String perNroCasa, String perEliminado, String perAdicionado, Date perFechaAdicion) {
        this.perCodigo = perCodigo;
        this.perNroIdentificacion = perNroIdentificacion;
        this.perApellidos = perApellidos;
        this.perNombres = perNombres;
        this.perGenero = perGenero;
        this.perFechaNacimiento = perFechaNacimiento;
        this.perCallePrincipal = perCallePrincipal;
        this.perNroCasa = perNroCasa;
        this.perEliminado = perEliminado;
        this.perAdicionado = perAdicionado;
        this.perFechaAdicion = perFechaAdicion;
    }

    public Long getPerCodigo() {
        return perCodigo;
    }

    public void setPerCodigo(Long perCodigo) {
        this.perCodigo = perCodigo;
    }

    public String getPerNroIdentificacion() {
        return perNroIdentificacion;
    }

    public void setPerNroIdentificacion(String perNroIdentificacion) {
        this.perNroIdentificacion = perNroIdentificacion;
    }

    public String getPerApellidos() {
        return perApellidos;
    }

    public void setPerApellidos(String perApellidos) {
        this.perApellidos = perApellidos;
    }

    public String getPerNombres() {
        return perNombres;
    }

    public void setPerNombres(String perNombres) {
        this.perNombres = perNombres;
    }

    public String getPerGenero() {
        return perGenero;
    }

    public void setPerGenero(String perGenero) {
        this.perGenero = perGenero;
    }

    public Date getPerFechaNacimiento() {
        return perFechaNacimiento;
    }

    public void setPerFechaNacimiento(Date perFechaNacimiento) {
        this.perFechaNacimiento = perFechaNacimiento;
    }

    public String getPerCallePrincipal() {
        return perCallePrincipal;
    }

    public void setPerCallePrincipal(String perCallePrincipal) {
        this.perCallePrincipal = perCallePrincipal;
    }

    public String getPerNroCasa() {
        return perNroCasa;
    }

    public void setPerNroCasa(String perNroCasa) {
        this.perNroCasa = perNroCasa;
    }

    public String getPerCalleSecundaria() {
        return perCalleSecundaria;
    }

    public void setPerCalleSecundaria(String perCalleSecundaria) {
        this.perCalleSecundaria = perCalleSecundaria;
    }

    public String getPerReferencia() {
        return perReferencia;
    }

    public void setPerReferencia(String perReferencia) {
        this.perReferencia = perReferencia;
    }

    public String getPerNroPasaporte() {
        return perNroPasaporte;
    }

    public void setPerNroPasaporte(String perNroPasaporte) {
        this.perNroPasaporte = perNroPasaporte;
    }

    public Date getPerPasFechaEmision() {
        return perPasFechaEmision;
    }

    public void setPerPasFechaEmision(Date perPasFechaEmision) {
        this.perPasFechaEmision = perPasFechaEmision;
    }

    public Date getPerPasFechaVencimiento() {
        return perPasFechaVencimiento;
    }

    public void setPerPasFechaVencimiento(Date perPasFechaVencimiento) {
        this.perPasFechaVencimiento = perPasFechaVencimiento;
    }

    public String getPerCasilla() {
        return perCasilla;
    }

    public void setPerCasilla(String perCasilla) {
        this.perCasilla = perCasilla;
    }

    public String getPerNroSeguroSocial() {
        return perNroSeguroSocial;
    }

    public void setPerNroSeguroSocial(String perNroSeguroSocial) {
        this.perNroSeguroSocial = perNroSeguroSocial;
    }

    public Date getPerFechaAfiliacionSeguro() {
        return perFechaAfiliacionSeguro;
    }

    public void setPerFechaAfiliacionSeguro(Date perFechaAfiliacionSeguro) {
        this.perFechaAfiliacionSeguro = perFechaAfiliacionSeguro;
    }

    public String getPerEliminado() {
        return perEliminado;
    }

    public void setPerEliminado(String perEliminado) {
        this.perEliminado = perEliminado;
    }

    public String getPerAdicionado() {
        return perAdicionado;
    }

    public void setPerAdicionado(String perAdicionado) {
        this.perAdicionado = perAdicionado;
    }

    public Date getPerFechaAdicion() {
        return perFechaAdicion;
    }

    public void setPerFechaAdicion(Date perFechaAdicion) {
        this.perFechaAdicion = perFechaAdicion;
    }

    public String getPerModificado() {
        return perModificado;
    }

    public void setPerModificado(String perModificado) {
        this.perModificado = perModificado;
    }

    public Date getPerFechaModificacion() {
        return perFechaModificacion;
    }

    public void setPerFechaModificacion(Date perFechaModificacion) {
        this.perFechaModificacion = perFechaModificacion;
    }

    /*
     @XmlTransient
     public Collection<GthCuentaBancaria> getGthCuentaBancariaCollection() {
     return gthCuentaBancariaCollection;
     }

     public void setGthCuentaBancariaCollection(Collection<GthCuentaBancaria> gthCuentaBancariaCollection) {
     this.gthCuentaBancariaCollection = gthCuentaBancariaCollection;
     }

     @XmlTransient
     public Collection<GthEducacionFormal> getGthEducacionFormalCollection() {
     return gthEducacionFormalCollection;
     }

     public void setGthEducacionFormalCollection(Collection<GthEducacionFormal> gthEducacionFormalCollection) {
     this.gthEducacionFormalCollection = gthEducacionFormalCollection;
     }

     @XmlTransient
     public Collection<GthDependiente> getGthDependienteCollection() {
     return gthDependienteCollection;
     }

     public void setGthDependienteCollection(Collection<GthDependiente> gthDependienteCollection) {
     this.gthDependienteCollection = gthDependienteCollection;
     }

     @XmlTransient
     public Collection<GthExperienciaInvestigacion> getGthExperienciaInvestigacionCollection() {
     return gthExperienciaInvestigacionCollection;
     }

     public void setGthExperienciaInvestigacionCollection(Collection<GthExperienciaInvestigacion> gthExperienciaInvestigacionCollection) {
     this.gthExperienciaInvestigacionCollection = gthExperienciaInvestigacionCollection;
     }

     @XmlTransient
     public Collection<GthVehiculo> getGthVehiculoCollection() {
     return gthVehiculoCollection;
     }

     public void setGthVehiculoCollection(Collection<GthVehiculo> gthVehiculoCollection) {
     this.gthVehiculoCollection = gthVehiculoCollection;
     }

     @XmlTransient
     public Collection<GthEmpleadoEmpresa> getGthEmpleadoEmpresaCollection() {
     return gthEmpleadoEmpresaCollection;
     }

     public void setGthEmpleadoEmpresaCollection(Collection<GthEmpleadoEmpresa> gthEmpleadoEmpresaCollection) {
     this.gthEmpleadoEmpresaCollection = gthEmpleadoEmpresaCollection;
     }

     @XmlTransient
     public Collection<GthVisa> getGthVisaCollection() {
     return gthVisaCollection;
     }

     public void setGthVisaCollection(Collection<GthVisa> gthVisaCollection) {
     this.gthVisaCollection = gthVisaCollection;
     }

     @XmlTransient
     public Collection<GthLicenciaConducir> getGthLicenciaConducirCollection() {
     return gthLicenciaConducirCollection;
     }

     public void setGthLicenciaConducirCollection(Collection<GthLicenciaConducir> gthLicenciaConducirCollection) {
     this.gthLicenciaConducirCollection = gthLicenciaConducirCollection;
     }

     @XmlTransient
     public Collection<GthMail> getGthMailCollection() {
     return gthMailCollection;
     }

     public void setGthMailCollection(Collection<GthMail> gthMailCollection) {
     this.gthMailCollection = gthMailCollection;
     }

     @XmlTransient
     public Collection<GthHistoricoHorasNocturnas> getGthHistoricoHorasNocturnasCollection() {
     return gthHistoricoHorasNocturnasCollection;
     }

     public void setGthHistoricoHorasNocturnasCollection(Collection<GthHistoricoHorasNocturnas> gthHistoricoHorasNocturnasCollection) {
     this.gthHistoricoHorasNocturnasCollection = gthHistoricoHorasNocturnasCollection;
     }

     @XmlTransient
     public Collection<GthTelefono> getGthTelefonoCollection() {
     return gthTelefonoCollection;
     }

     public void setGthTelefonoCollection(Collection<GthTelefono> gthTelefonoCollection) {
     this.gthTelefonoCollection = gthTelefonoCollection;
     }

     @XmlTransient
     public Collection<GthEducacionInformal> getGthEducacionInformalCollection() {
     return gthEducacionInformalCollection;
     }

     public void setGthEducacionInformalCollection(Collection<GthEducacionInformal> gthEducacionInformalCollection) {
     this.gthEducacionInformalCollection = gthEducacionInformalCollection;
     }

     @XmlTransient
     public Collection<GthRevisionEducacionFormal> getGthRevisionEducacionFormalCollection() {
     return gthRevisionEducacionFormalCollection;
     }

     public void setGthRevisionEducacionFormalCollection(Collection<GthRevisionEducacionFormal> gthRevisionEducacionFormalCollection) {
     this.gthRevisionEducacionFormalCollection = gthRevisionEducacionFormalCollection;
     }

     @XmlTransient
     public Collection<GthNotificacionCorreo> getGthNotificacionCorreoCollection() {
     return gthNotificacionCorreoCollection;
     }

     public void setGthNotificacionCorreoCollection(Collection<GthNotificacionCorreo> gthNotificacionCorreoCollection) {
     this.gthNotificacionCorreoCollection = gthNotificacionCorreoCollection;
     }

     @XmlTransient
     public Collection<GthEmpresa> getGthEmpresaCollection() {
     return gthEmpresaCollection;
     }

     public void setGthEmpresaCollection(Collection<GthEmpresa> gthEmpresaCollection) {
     this.gthEmpresaCollection = gthEmpresaCollection;
     }

     @XmlTransient
     public Collection<GthEmpresa> getGthEmpresaCollection1() {
     return gthEmpresaCollection1;
     }

     public void setGthEmpresaCollection1(Collection<GthEmpresa> gthEmpresaCollection1) {
     this.gthEmpresaCollection1 = gthEmpresaCollection1;
     }

     @XmlTransient
     public Collection<GthReferencia> getGthReferenciaCollection() {
     return gthReferenciaCollection;
     }

     public void setGthReferenciaCollection(Collection<GthReferencia> gthReferenciaCollection) {
     this.gthReferenciaCollection = gthReferenciaCollection;
     }

     @XmlTransient
     public Collection<GthExperienciaLaboral> getGthExperienciaLaboralCollection() {
     return gthExperienciaLaboralCollection;
     }

     public void setGthExperienciaLaboralCollection(Collection<GthExperienciaLaboral> gthExperienciaLaboralCollection) {
     this.gthExperienciaLaboralCollection = gthExperienciaLaboralCollection;
     }

     @XmlTransient
     public Collection<GthIdiomaPersona> getGthIdiomaPersonaCollection() {
     return gthIdiomaPersonaCollection;
     }

     public void setGthIdiomaPersonaCollection(Collection<GthIdiomaPersona> gthIdiomaPersonaCollection) {
     this.gthIdiomaPersonaCollection = gthIdiomaPersonaCollection;
     }

     @XmlTransient
     public Collection<GthBienInmueble> getGthBienInmuebleCollection() {
     return gthBienInmuebleCollection;
     }

     public void setGthBienInmuebleCollection(Collection<GthBienInmueble> gthBienInmuebleCollection) {
     this.gthBienInmuebleCollection = gthBienInmuebleCollection;
     }

     @XmlTransient
     public Collection<GthHobby> getGthHobbyCollection() {
     return gthHobbyCollection;
     }

     public void setGthHobbyCollection(Collection<GthHobby> gthHobbyCollection) {
     this.gthHobbyCollection = gthHobbyCollection;
     }

     public GthServicioMilitar getGthServicioMilitar() {
     return gthServicioMilitar;
     }

     public void setGthServicioMilitar(GthServicioMilitar gthServicioMilitar) {
     this.gthServicioMilitar = gthServicioMilitar;
     }

     public GthContratoVistaPrev getGthContratoVistaPrev() {
     return gthContratoVistaPrev;
     }

     public void setGthContratoVistaPrev(GthContratoVistaPrev gthContratoVistaPrev) {
     this.gthContratoVistaPrev = gthContratoVistaPrev;
     }

     public GthFirmaDigital getGthFirmaDigital() {
     return gthFirmaDigital;
     }

     public void setGthFirmaDigital(GthFirmaDigital gthFirmaDigital) {
     this.gthFirmaDigital = gthFirmaDigital;
     }

     @XmlTransient
     public Collection<GthBienMueble> getGthBienMuebleCollection() {
     return gthBienMuebleCollection;
     }

     public void setGthBienMuebleCollection(Collection<GthBienMueble> gthBienMuebleCollection) {
     this.gthBienMuebleCollection = gthBienMuebleCollection;
     }

     @XmlTransient
     public Collection<GthDesignacionOrganismo> getGthDesignacionOrganismoCollection() {
     return gthDesignacionOrganismoCollection;
     }

     public void setGthDesignacionOrganismoCollection(Collection<GthDesignacionOrganismo> gthDesignacionOrganismoCollection) {
     this.gthDesignacionOrganismoCollection = gthDesignacionOrganismoCollection;
     }

     @XmlTransient
     public Collection<GthDiscapacidadPersona> getGthDiscapacidadPersonaCollection() {
     return gthDiscapacidadPersonaCollection;
     }

     public void setGthDiscapacidadPersonaCollection(Collection<GthDiscapacidadPersona> gthDiscapacidadPersonaCollection) {
     this.gthDiscapacidadPersonaCollection = gthDiscapacidadPersonaCollection;
     }

     public GthEstadoCivil getEscCodigo() {
     return escCodigo;
     }

     public void setEscCodigo(GthEstadoCivil escCodigo) {
     this.escCodigo = escCodigo;
     }

     public GthParroquia getGthParroquia() {
     return gthParroquia;
     }

     public void setGthParroquia(GthParroquia gthParroquia) {
     this.gthParroquia = gthParroquia;
     }

     public GthParroquia getGthParroquia1() {
     return gthParroquia1;
     }

     public void setGthParroquia1(GthParroquia gthParroquia1) {
     this.gthParroquia1 = gthParroquia1;
     }

     public GthReligion getRelCodigo() {
     return relCodigo;
     }

     public void setRelCodigo(GthReligion relCodigo) {
     this.relCodigo = relCodigo;
     }

     public GthTipoIdentificacion getTiiCodigo() {
     return tiiCodigo;
     }

     public void setTiiCodigo(GthTipoIdentificacion tiiCodigo) {
     this.tiiCodigo = tiiCodigo;
     }

     public GthTipoSangre getTisCodigo() {
     return tisCodigo;
     }

     public void setTisCodigo(GthTipoSangre tisCodigo) {
     this.tisCodigo = tisCodigo;
     }

     @XmlTransient
     public Collection<GthSeniaParticular> getGthSeniaParticularCollection() {
     return gthSeniaParticularCollection;
     }

     public void setGthSeniaParticularCollection(Collection<GthSeniaParticular> gthSeniaParticularCollection) {
     this.gthSeniaParticularCollection = gthSeniaParticularCollection;
     }

     @XmlTransient
     public Collection<GthEmergencia> getGthEmergenciaCollection() {
     return gthEmergenciaCollection;
     }

     public void setGthEmergenciaCollection(Collection<GthEmergencia> gthEmergenciaCollection) {
     this.gthEmergenciaCollection = gthEmergenciaCollection;
     }

     @XmlTransient
     public Collection<GthUsuario> getGthUsuarioCollection() {
     return gthUsuarioCollection;
     }

     public void setGthUsuarioCollection(Collection<GthUsuario> gthUsuarioCollection) {
     this.gthUsuarioCollection = gthUsuarioCollection;
     }

     public GthFoto getGthFoto() {
     return gthFoto;
     }

     public void setGthFoto(GthFoto gthFoto) {
     this.gthFoto = gthFoto;
     }

     @XmlTransient
     public Collection<PedDocenteMalla> getPedDocenteMallaCollection() {
     return pedDocenteMallaCollection;
     }

     public void setPedDocenteMallaCollection(Collection<PedDocenteMalla> pedDocenteMallaCollection) {
     this.pedDocenteMallaCollection = pedDocenteMallaCollection;
     }
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perCodigo != null ? perCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GthPersona)) {
            return false;
        }
        GthPersona other = (GthPersona) object;
        if ((this.perCodigo == null && other.perCodigo != null) || (this.perCodigo != null && !this.perCodigo.equals(other.perCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GthPersona[ perCodigo=" + perCodigo + " ]";
    }

}
