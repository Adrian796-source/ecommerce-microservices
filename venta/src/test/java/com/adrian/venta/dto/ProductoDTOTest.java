package com.adrian.venta.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductoDTOTest {

    @Test
    @DisplayName("Test para el constructor sin argumentos y los setters")
    void testNoArgsConstructorAndSetters() {
        // Arrange: Se crea una instancia usando el constructor vacío (cubre la línea 7)
        ProductoDTO producto = new ProductoDTO();
        assertNotNull(producto);

        // Act: Se usan los setters para asignar valores (cubre las líneas 41-52)
        producto.setIdProducto(1L);
        producto.setNombre("Teclado");
        producto.setMarca("Logitech");
        producto.setPrecio(75.50);

        // Assert: Se usan los getters para verificar que los valores se asignaron correctamente
        assertEquals(1L, producto.getIdProducto());
        assertEquals("Teclado", producto.getNombre());
        assertEquals("Logitech", producto.getMarca());
        assertEquals(75.50, producto.getPrecio());
    }

    @Test
    @DisplayName("Test para el constructor con todos los argumentos y los getters")
    void testAllArgsConstructorAndGetters() {
        // Arrange & Act: Se crea una instancia usando el constructor completo (cubre líneas 9-15)
        ProductoDTO producto = new ProductoDTO(2L, "Monitor", "Samsung", 250.0);

        // Assert: Se usan los getters para verificar los valores (cubre líneas 17-38)
        assertEquals(2L, producto.getIdProducto());
        assertEquals("Monitor", producto.getNombre());
        assertEquals("Samsung", producto.getMarca());
        assertEquals(250.0, producto.getPrecio());
    }
}