package com.adrian.venta.dto;

import java.time.LocalDate;

public class VentaDTO {

    private Long idVenta;
    private LocalDate fecha;
    private CarritoDTO carrito;


    public VentaDTO() {
    }

    public VentaDTO(Long idVenta, LocalDate fecha, CarritoDTO carrito) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.carrito = carrito;
    }

    public Long getIdVenta() {
        return idVenta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public CarritoDTO getCarrito() {
        return carrito;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setCarrito(CarritoDTO carrito) {
        this.carrito = carrito;
    }
}
