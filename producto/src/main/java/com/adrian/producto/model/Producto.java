package com.adrian.producto.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    private String nombre;
    private String marca;
    private Double precio;


    public Producto() {
    }

    public Producto(Long idProducto, String nombre, String marca, Double precio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}