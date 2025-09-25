package com.adrian.carrito.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idCarrito;
    private Double precioTotal;
    @ElementCollection
    private List<Long> productos;

    public Carrito() {
    }

    public Carrito(Long idCarrito, Double precioTotal, List<Long> productos) {
        this.idCarrito = idCarrito;
        this.precioTotal = precioTotal;
        this.productos = productos;
    }

    public Long getIdCarrito() {
        return idCarrito;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public List<Long> getProductos() {
        return productos;
    }

    public void setIdCarrito(Long idCarrito) {
        this.idCarrito = idCarrito;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void setProductos(List<Long> productos) {
        this.productos = productos;
    }
}
