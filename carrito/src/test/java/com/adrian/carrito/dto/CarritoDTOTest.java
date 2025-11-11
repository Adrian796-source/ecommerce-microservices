package com.adrian.carrito.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarritoDTOTest {

    @Test
    @DisplayName("Test para constructor sin argumentos y setters")
    void testNoArgsConstructorAndSetters() {
        // Given: Creamos un DTO usando el constructor sin argumentos
        CarritoDTO carritoDTO = new CarritoDTO();

        // When: Usamos los setters para asignar valores
        carritoDTO.setIdCarrito(1L);
        carritoDTO.setPrecioTotal(100.50);
        ProductoDTO producto = new ProductoDTO(10L, "Test Product", "Test Brand", 100.50);
        List<ProductoDTO> productos = Collections.singletonList(producto);
        carritoDTO.setProductos(productos);

        // Then: Verificamos que los getters devuelven los valores correctos
        assertEquals(1L, carritoDTO.getIdCarrito());
        assertEquals(100.50, carritoDTO.getPrecioTotal());
        assertNotNull(carritoDTO.getProductos());
        assertEquals(1, carritoDTO.getProductos().size());
        assertEquals("Test Product", carritoDTO.getProductos().get(0).getNombre());
    }

    @Test
    @DisplayName("Test para constructor con todos los argumentos")
    void testAllArgsConstructor() {
        // Given: Creamos un DTO usando el constructor con todos los argumentos
        CarritoDTO carritoDTO = new CarritoDTO(2L, 200.0, Collections.emptyList());

        // Then: Verificamos que los valores se asignaron correctamente
        assertEquals(2L, carritoDTO.getIdCarrito());
        assertEquals(200.0, carritoDTO.getPrecioTotal());
        assertTrue(carritoDTO.getProductos().isEmpty());
    }
}