package com.adrian.producto.repository;

import com.adrian.producto.model.Producto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Anotación clave para tests de la capa de persistencia
class IProductoRepositoryTest {

    @Autowired
    private IProductoRepository productoRepository;

    @Test
    @DisplayName("Debería guardar un producto correctamente en la base de datos")
    void deberiaGuardarProducto() {
        // Arrange: Creamos una nueva entidad Producto
        Producto nuevoProducto = new Producto(null, "Teclado Mecánico", "Keychron", 150.00);

        // Act: Guardamos el producto usando el repositorio
        Producto productoGuardado = productoRepository.save(nuevoProducto);

        // Assert: Verificamos que el producto se guardó y se le asignó un ID
        assertNotNull(productoGuardado);
        assertTrue(productoGuardado.getIdProducto() > 0);
    }

    @Test
    @DisplayName("Debería encontrar un producto por su ID después de guardarlo")
    void deberiaEncontrarProductoPorId() {
        // Arrange: Guardamos un producto primero
        Producto productoGuardado = productoRepository.save(new Producto(null, "Monitor Ultrawide", "LG", 800.00));

        // Act: Buscamos el producto por el ID que se le asignó
        Optional<Producto> productoEncontrado = productoRepository.findById(productoGuardado.getIdProducto());

        // Assert: Verificamos que el producto fue encontrado y sus datos son correctos
        assertTrue(productoEncontrado.isPresent());
        assertEquals("Monitor Ultrawide", productoEncontrado.get().getNombre());
    }

}