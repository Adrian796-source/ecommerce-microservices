package com.adrian.carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public class CarritoDTO {

    private Long idCarrito;
    private Double precioTotal;
    private List<ProductoDTO> listaProductos;

    public CarritoDTO() {
    }

    public CarritoDTO(Long idCarrito, Double precioTotal, List<ProductoDTO> listaProductos) {
        this.idCarrito = idCarrito;
        this.precioTotal = precioTotal;
        this.listaProductos = listaProductos;
    }

    public Long getIdCarrito() {
        return idCarrito;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public List<ProductoDTO> getListaProductos() {
        return listaProductos;
    }

    public void setIdCarrito(Long idCarrito) {
        this.idCarrito = idCarrito;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void setListaProductos(List<ProductoDTO> listaProductos) {
        this.listaProductos = listaProductos;
    }
}
