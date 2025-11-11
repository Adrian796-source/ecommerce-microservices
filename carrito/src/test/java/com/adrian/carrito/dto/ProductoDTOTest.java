package com.adrian.carrito.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoDTOTest {

    @Test
    @DisplayName("Test para constructor sin argumentos y setters")
    void testNoArgsConstructorAndSetters() {
        // Given: Creamos un DTO usando el constructor sin argumentos
        ProductoDTO productoDTO = new ProductoDTO();

        // When: Usamos los setters para asignar valores
        productoDTO.setIdProducto(1L);
        productoDTO.setNombre("Test Product");
        productoDTO.setMarca("Test Brand");
        productoDTO.setPrecio(99.99);

        // Then: Verificamos que los getters devuelven los valores correctos
        assertEquals(1L, productoDTO.getIdProducto());
        assertEquals("Test Product", productoDTO.getNombre());
        assertEquals("Test Brand", productoDTO.getMarca());
        assertEquals(99.99, productoDTO.getPrecio());
    }

    @Test
    @DisplayName("Test para constructor con todos los argumentos")
    void testAllArgsConstructor() {
        // Given: Creamos un DTO usando el constructor con todos los argumentos
        ProductoDTO productoDTO = new ProductoDTO(2L, "Another Product", "Another Brand", 150.0);

        // Then: Verificamos que los valores se asignaron correctamente
        assertEquals(2L, productoDTO.getIdProducto());
        assertEquals("Another Product", productoDTO.getNombre());
        assertEquals("Another Brand", productoDTO.getMarca());
        assertEquals(150.0, productoDTO.getPrecio());
    }
}