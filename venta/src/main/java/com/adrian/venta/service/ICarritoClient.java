package com.adrian.venta.service;

import com.adrian.venta.dto.CarritoDTO;

public interface ICarritoClient {
    CarritoDTO findCarritoById(Long idCarrito);
}