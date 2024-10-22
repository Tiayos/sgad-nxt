/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.sgadnuxt.entity.model.gth;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "GTH_MAIL", catalog = "", schema = "GTH")
public class GthMail implements Serializable {
    
    @Id
    @Column(name = "MAI_CODIGO", nullable = false)
    private Long maiCodigo;   
    
    @Column(name = "PER_CODIGO", nullable = false)
    private Long perCodigo;   
    
    @Column(name = "TIM_CODIGO", nullable = false)
    private Long timCodigo;   
    
    @Column(name = "MAI_DIRECCION", nullable = false, length = 50)
    private String maiDireccion;
    
    public GthMail(){}

    public GthMail(Long maiCodigo) {
        this.maiCodigo = maiCodigo;
    }

    public GthMail(Long maiCodigo, Long perCodigo, Long timCodigo, String maiDireccion) {
        this.maiCodigo = maiCodigo;
        this.perCodigo = perCodigo;
        this.timCodigo = timCodigo;
        this.maiDireccion = maiDireccion;
    }

    public Long getMaiCodigo() {
        return maiCodigo;
    }

    public void setMaiCodigo(Long maiCodigo) {
        this.maiCodigo = maiCodigo;
    }

    public Long getPerCodigo() {
        return perCodigo;
    }

    public void setPerCodigo(Long perCodigo) {
        this.perCodigo = perCodigo;
    }

    public Long getTimCodigo() {
        return timCodigo;
    }

    public void setTimCodigo(Long timCodigo) {
        this.timCodigo = timCodigo;
    }

    public String getMaiDireccion() {
        return maiDireccion;
    }

    public void setMaiDireccion(String maiDireccion) {
        this.maiDireccion = maiDireccion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.maiCodigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GthMail other = (GthMail) obj;
        if (!Objects.equals(this.maiCodigo, other.maiCodigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GthMail{" + "perCodigo=" + perCodigo + ", maiDireccion=" + maiDireccion + '}';
    }
    
    
}
