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
@Table(name = "GTH_CARGO", catalog = "", schema = "GTH")
public class GthCargo implements Serializable  {
    
    @Id
    @Column(name = "CAR_CODIGO", nullable = false)
    private Long codigo;   
    @Column(name = "CAR_DESCRIPCION", nullable = false, length = 150)
    private String descripcion;

    public GthCargo() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.codigo);
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
        final GthCargo other = (GthCargo) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GthCargo{" + "codigo=" + codigo + ", descripcion=" + descripcion + '}';
    }
    
    
}
