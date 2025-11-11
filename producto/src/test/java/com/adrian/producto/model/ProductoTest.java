package com.adrian.producto.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void testConstructorsAndAccessors() {
        // Probamos el constructor sin argumentos y los setters
        Producto productoVacio = new Producto();
        assertNotNull(productoVacio);

        productoVacio.setIdProducto(1L);
        productoVacio.setNombre("Laptop");
        productoVacio.setMarca("TechBrand");
        productoVacio.setPrecio(1200.50);

        // Probamos los getters de forma agrupada para mayor claridad
        assertAll("Getters should return the correct values",
                () -> assertEquals(1L, productoVacio.getIdProducto()),
                () -> assertEquals("Laptop", productoVacio.getNombre()),
                () -> assertEquals("TechBrand", productoVacio.getMarca()),
                () -> assertEquals(1200.50, productoVacio.getPrecio())
        );

        // Probamos el constructor con todos los argumentos
        Producto productoLleno = new Producto(1L, "Laptop", "TechBrand", 1200.50);
        assertEquals(productoVacio, productoLleno);
    }

    @Test
    void testEqualsAndHashCodeContract() {
        // Arrange: Creamos dos objetos idénticos y uno diferente
        Producto productoIgual = new Producto(1L, "Laptop", "TechBrand", 1200.50);
        Producto productoIgual2 = new Producto(1L, "Laptop", "TechBrand", 1200.50);
        Producto productoDiferente = new Producto(2L, "Teclado", "TechBrand", 75.00);
        
        // Assert para el método equals()
        assertEquals(productoIgual, productoIgual2);
        assertNotEquals(productoIgual, productoDiferente);
        assertNotEquals(null, productoIgual);
        assertNotEquals(productoIgual, new Object());
        
        // Assert para el método hashCode()
        assertEquals(productoIgual.hashCode(), productoIgual2.hashCode());
        assertNotEquals(productoIgual.hashCode(), productoDiferente.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Producto producto = new Producto(1L, "Laptop", "TechBrand", 1200.50);
        
        // Verificamos que el toString no sea nulo y contenga los nombres de los campos
        String productoString = producto.toString();        
        assertNotNull(productoString);
        assertTrue(productoString.contains("idProducto=1"));
        assertTrue(productoString.contains("nombre=Laptop"));
        assertTrue(productoString.contains("marca=TechBrand"));
        assertTrue(productoString.contains("precio=1200.5"));
    }
}