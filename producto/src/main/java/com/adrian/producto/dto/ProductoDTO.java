package com.adrian.producto.dto;

public class ProductoDTO {

    private Long idProducto;
    private String nombre;
    private String marca;
    private Double precio; // <-- CAMBIADO a Double

    // Constructor vacío
    public ProductoDTO() {
    }

    // Constructor con todos los parámetros
    public ProductoDTO(Long idProducto, String nombre, String marca, Double precio) { // <-- CAMBIADO a Double
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
    }

    // --- Getters ---
    public Long getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public Double getPrecio() { // <-- CAMBIADO a Double
        return precio;
    }

    // --- Setters ---
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPrecio(Double precio) { // <-- CAMBIADO a Double
        this.precio = precio;
    }
}
