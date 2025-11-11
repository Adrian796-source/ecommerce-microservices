package com.adrian.producto.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;

import static org.junit.jupiter.api.Assertions.*;

class ProductoDTOTest {

    @Test
    void testDtoConstructionAndAccessors() {
        // 1. Probamos el constructor sin argumentos y los setters
        ProductoDTO dto = new ProductoDTO();
        assertNotNull(dto);

        dto.setIdProducto(1L);
        dto.setNombre("Laptop");
        dto.setMarca("TechCo");
        dto.setPrecio(1200.0);

        // 2. Probamos los getters de forma agrupada
        assertAll("Getters should return the correct values",
                () -> assertEquals(1L, dto.getIdProducto()),
                () -> assertEquals("Laptop", dto.getNombre()),
                () -> assertEquals("TechCo", dto.getMarca()),
                () -> assertEquals(1200.0, dto.getPrecio())
        );

        // 3. Probamos el constructor con todos los argumentos
        ProductoDTO dtoCompleto = new ProductoDTO(1L, "Laptop", "TechCo", 1200.0);
        assertEquals(dto, dtoCompleto);
    }

    @Test
    void testEqualsAndHashCodeContract() {
        // Arrange: Creamos dos objetos idénticos y uno diferente
        ProductoDTO dto1 = new ProductoDTO(1L, "Laptop", "TechCo", 1200.0);
        ProductoDTO dto2 = new ProductoDTO(1L, "Laptop", "TechCo", 1200.0);
        ProductoDTO dto3 = new ProductoDTO(2L, "Mouse", "TechCo", 25.0);

        // Assert para el método equals()
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(null, dto1);
        assertNotEquals(dto1, new Object());

        // Assert para el método hashCode()
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}