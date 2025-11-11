package com.adrian.producto.controller;

import com.adrian.producto.dto.ProductoDTO;
import com.adrian.producto.service.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor // Lombok crea el constructor con los campos 'final'
public class ProductoController {

    private final IProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getProductos() {
        return ResponseEntity.ok(productoService.getProductos());
    }

    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> getProducto(@PathVariable Long idProducto) {
        ProductoDTO producto = productoService.getProducto(idProducto);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<String> createProducto(@RequestBody ProductoDTO producto) {
        // Se envía el DTO al servicio
        productoService.createProducto(producto);
        return new ResponseEntity<>("Producto creado exitosamente", HttpStatus.CREATED);
    }

    @PutMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> editProducto(@PathVariable Long idProducto, @RequestBody ProductoDTO producto) {
        // Se envía el ID y el DTO al servicio
        productoService.editProducto(idProducto, producto);
        // Devolvemos el producto actualizado
        return ResponseEntity.ok(productoService.getProducto(idProducto));
    }

    @DeleteMapping("/{idProducto}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long idProducto) {
        productoService.deleteProducto(idProducto);
        return ResponseEntity.ok("Producto eliminado exitosamente");
    }
}