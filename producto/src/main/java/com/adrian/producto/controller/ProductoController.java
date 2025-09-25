package com.adrian.producto.controller;

import com.adrian.producto.dto.ProductoDTO;
import com.adrian.producto.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @GetMapping
    public List<ProductoDTO> getProductos() {
        // El servicio ya devuelve la lista de DTOs
        return productoService.getProductos();
    }

    @GetMapping("/{idProducto}")
    public ProductoDTO getProducto(@PathVariable Long idProducto) {
        // El servicio ya devuelve el DTO
        return productoService.getProducto(idProducto);
    }

    @PostMapping
    public String createProducto(@RequestBody ProductoDTO producto) {
        // Se envía el DTO al servicio
        productoService.createProducto(producto);
        return "Producto creado exitosamente";
    }

    @PutMapping("/{idProducto}")
    public String editProducto(@PathVariable Long idProducto, @RequestBody ProductoDTO producto) {
        // Se envía el ID y el DTO al servicio
        productoService.editProducto(idProducto, producto);
        return "Producto editado exitosamente";
    }

    @DeleteMapping("/{idProducto}")
    public String deleteProducto(@PathVariable Long idProducto) {
        productoService.deleteProducto(idProducto);
        return "Producto eliminado exitosamente";
    }
}