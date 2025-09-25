package com.adrian.carrito.controller;

import com.adrian.carrito.dto.CarritoDTO;
import com.adrian.carrito.service.ICarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carritos")
public class CarritoController {

    @Autowired
    private ICarritoService carritoService;

    @PostMapping
    public String createCarrito(@RequestBody List<Long> idsProductos) {
        carritoService.createCarrito(idsProductos);
        return "Carrito creado exitosamente";
    }

    @GetMapping("/{idCarrito}")
    public CarritoDTO getCarrito(@PathVariable Long idCarrito) {
        return carritoService.getCarrito(idCarrito);
    }

    @GetMapping
    public List<CarritoDTO> getCarritos() {
        return carritoService.getCarritos();
    }

    @DeleteMapping("/{idCarrito}")
    public String deleteCarrito(@PathVariable Long idCarrito) {
        carritoService.deleteCarrito(idCarrito);
        return "Carrito eliminado exitosamente";
    }

    @PostMapping("/{idCarrito}/productos/{idProducto}")
    public String addProducto(@PathVariable Long idCarrito, @PathVariable Long idProducto) {
        carritoService.addProducto(idCarrito, idProducto);
        return "Producto agregado al carrito exitosamente";
    }

    @DeleteMapping("/{idCarrito}/productos/{idProducto}")
    public String deleteProducto(@PathVariable Long idCarrito, @PathVariable Long idProducto) {
        carritoService.deleteProducto(idCarrito, idProducto);
        return "Producto eliminado del carrito exitosamente";
    }

}
