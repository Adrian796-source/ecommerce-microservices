package com.adrian.venta.repository;

import com.adrian.venta.dto.CarritoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * name = es el nombre con el que el microservicio Carrito está registrado en Eureka Server.
 * La URL real debería estar en el archivo application.properties y no hardcodeada.
*/
@FeignClient(name = "carrito")
public interface ICarritoAPI {

    /**
     * Consume el endpoint del microservicio carrito para obtener un carrito por su id.
     * El microservicio carrito se encarga de buscar los productos y devolver el DTO completo.
     * @param idCarrito el ID del carrito a buscar
     * @return un CarritoDTO con la información completa, incluyendo la lista de productos.
     */
    @GetMapping("/carritos/{idCarrito}")
    public CarritoDTO findCarritoById(@PathVariable("idCarrito") Long idCarrito);

}
