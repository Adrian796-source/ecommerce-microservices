package com.adrian.carrito.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarritoTest {

    private Carrito carrito;

    @BeforeEach
    void setUp() {
        // Creamos una instancia base para usar en varios tests
        carrito = new Carrito(1L, 150.75, List.of(10L, 20L));
    }

    @Test
    @DisplayName("Test para el constructor sin argumentos")
    void testNoArgsConstructor() {
        Carrito carritoVacio = new Carrito();
        assertNotNull(carritoVacio, "El carrito no debería ser nulo.");
        assertNull(carritoVacio.getIdCarrito(), "El ID debería ser nulo para un carrito nuevo.");
    }

    @Test
    @DisplayName("Test para el constructor con todos los argumentos")
    void testAllArgsConstructor() {
        assertEquals(1L, carrito.getIdCarrito());
        assertEquals(150.75, carrito.getPrecioTotal());
        assertEquals(2, carrito.getProductos().size());
        assertTrue(carrito.getProductos().containsAll(List.of(10L, 20L)));
    }

    @Test
    @DisplayName("Test para verificar getters y setters")
    void testGettersAndSetters() {
        // Creamos un carrito vacío y usamos los setters
        Carrito nuevoCarrito = new Carrito();

        nuevoCarrito.setIdCarrito(2L);
        nuevoCarrito.setPrecioTotal(99.99);
        nuevoCarrito.setProductos(List.of(30L));

        assertEquals(2L, nuevoCarrito.getIdCarrito());
        assertEquals(99.99, nuevoCarrito.getPrecioTotal());
        assertEquals(1, nuevoCarrito.getProductos().size());
        assertEquals(30L, nuevoCarrito.getProductos().get(0));
    }

    @Test
    @DisplayName("Test para setters con valores nulos o inválidos")
    void testSettersWithInvalidValues() {
        // Creamos un carrito vacío
        Carrito carritoInvalido = new Carrito();

        // Test 1: Asignar una lista de productos nula
        carritoInvalido.setProductos(null);
        assertNull(carritoInvalido.getProductos(), "La lista de productos debería poder ser nula.");

        // Test 2: Asignar un precio negativo (actualmente permitido por el tipo Double)
        carritoInvalido.setPrecioTotal(-50.0);
        assertEquals(-50.0, carritoInvalido.getPrecioTotal(), "El precio total debería poder ser negativo si no hay validación.");
    }

    @Test
    @DisplayName("Test para constructor con lista de productos nula")
    void testAllArgsConstructorWithNullList() {
        Carrito carritoConListaNula = new Carrito(3L, 200.0, null);
        assertNull(carritoConListaNula.getProductos(), "El constructor debería aceptar una lista de productos nula.");
    }

    @Test
    @DisplayName("Test para el método toString")
    void testToString() {
        // Given: un carrito con datos
        Carrito carritoParaString = new Carrito(1L, 150.75, List.of(10L, 20L));

        // When: llamamos al método toString
        String carritoString = carritoParaString.toString();

        // Then: verificamos que el string contenga los valores de los campos
        assertTrue(carritoString.contains("idCarrito=1"));
        assertTrue(carritoString.contains("precioTotal=150.75"));
        assertTrue(carritoString.contains("productos=[10, 20]"));
    }
}