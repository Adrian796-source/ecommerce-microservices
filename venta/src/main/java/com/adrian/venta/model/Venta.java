package com.adrian.venta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;


@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;
    private LocalDate fecha;
    private Long idCarrito;

    public Venta() {
    }

    public Venta(Long idVenta, LocalDate fecha, Long idCarrito) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.idCarrito = idCarrito;
    }

    public Long getIdVenta() {
        return idVenta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Long getIdCarrito() {
        return idCarrito;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setIdCarrito(Long idCarrito) {
        this.idCarrito = idCarrito;
    }
}