package com.adrian.carrito.repository;

import com.adrian.carrito.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// El nombre "producto" es correcto, ya que es el spring.application.name del otro servicio.
@FeignClient(name = "producto")
public interface IProductoAPI {

    // El path aquí debe coincidir con el @RequestMapping del ProductoController
    // y el @GetMapping del método getProductos().
    @GetMapping("/productos/")
    List<ProductoDTO> getProductos();

    // El path aquí debe coincidir con el @RequestMapping del ProductoController
    // y el @GetMapping del método getProducto().
    @GetMapping("/productos/{idProducto}")
    ProductoDTO getProducto(@PathVariable("idProducto") Long idProducto);

}
