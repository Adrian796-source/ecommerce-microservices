package com.adrian.venta.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VentaTest {

    @Test
    void getIdVenta() {
        Venta venta = new Venta();
        Long idVenta = 1L;
        venta.setIdVenta(idVenta);
        assertEquals(idVenta, venta.getIdVenta());
    }

    @Test
    void getFecha() {
        Venta venta = new Venta();
        LocalDate fecha = LocalDate.now();
        venta.setFecha(fecha);
        assertEquals(fecha, venta.getFecha());
    }

    @Test
    void getIdCarrito() {
        Venta venta = new Venta();
        Long idCarrito = 15L;
        venta.setIdCarrito(idCarrito);
        assertEquals(idCarrito, venta.getIdCarrito());
    }

    @Test
    void setIdVenta() {
        Venta venta = new Venta();
        Long idVenta = 1L;
        venta.setIdVenta(idVenta);
        assertEquals(idVenta, venta.getIdVenta(), "El idVenta debería ser el que se estableció.");
    }

    @Test
    void setFecha() {
        Venta venta = new Venta();
        LocalDate fecha = LocalDate.of(2023, 10, 27);
        venta.setFecha(fecha);
        assertEquals(fecha, venta.getFecha(), "La fecha debería ser la que se estableció.");
    }

    @Test
    void setIdCarrito() {
        Venta venta = new Venta();
        Long idCarrito = 15L;
        venta.setIdCarrito(idCarrito);
        assertEquals(idCarrito, venta.getIdCarrito(), "El idCarrito debería ser el que se estableció.");
    }
}