package com.adrian.venta.dto;

import java.util.List;


public class CarritoDTO {

    private Long idCarrito;
    private Double precioTotal;
    private List<ProductoDTO> productos;


    public CarritoDTO() {
    }

    public CarritoDTO(Long idCarrito, Double precioTotal, List<ProductoDTO> productos) {
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

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setIdCarrito(Long idCarrito) {
        this.idCarrito = idCarrito;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }
}