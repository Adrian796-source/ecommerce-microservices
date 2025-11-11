package com.adrian.venta.service;

import com.adrian.venta.dto.CarritoDTO;
import com.adrian.venta.repository.ICarritoAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CarritoClient implements ICarritoClient {

    @Autowired
    private ICarritoAPI carritoAPI;

    @Override
    @CircuitBreaker(name = "carrito-service", fallbackMethod = "fallbackFindCarrito")
    public CarritoDTO findCarritoById(Long idCarrito) {
        return carritoAPI.findCarritoById(idCarrito);
    }

    public CarritoDTO fallbackFindCarrito(Long idCarrito, Throwable t) {
        return new CarritoDTO(idCarrito, -1.0, new ArrayList<>());
    }
}