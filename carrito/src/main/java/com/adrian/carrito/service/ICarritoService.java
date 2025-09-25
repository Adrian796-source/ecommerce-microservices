package com.adrian.carrito.service;

import com.adrian.carrito.dto.CarritoDTO;

import java.util.List;

public interface ICarritoService {

    void createCarrito(List<Long> listaIdsProductos);

    CarritoDTO getCarrito(Long idCarrito);

    List<CarritoDTO> getCarritos();

    void deleteCarrito(Long idCarrito);

    void addProducto(Long idCarrito, Long idProducto);

    void deleteProducto(Long idCarrito, Long idProducto);

}
