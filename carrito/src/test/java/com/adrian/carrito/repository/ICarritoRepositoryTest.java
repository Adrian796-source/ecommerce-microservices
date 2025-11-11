package com.adrian.carrito.repository;

import com.adrian.carrito.model.Carrito;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
class ICarritoRepositoryTest {

    @Autowired
    private ICarritoRepository carritoRepository;

    @Test
    @DisplayName("Cuando se guarda un carrito, se debe poder recuperar por su ID")
    void whenSaveCarrito_thenItShouldBeFound() {
        // Given: Creamos una nueva instancia de Carrito
        Carrito nuevoCarrito = new Carrito();
        nuevoCarrito.setProductos(List.of(101L, 102L));
        nuevoCarrito.setPrecioTotal(250.50);

        // When: Guardamos el carrito en la base de datos
        Carrito carritoGuardado = carritoRepository.save(nuevoCarrito);
        Carrito carritoEncontrado = carritoRepository.findById(carritoGuardado.getIdCarrito()).orElse(null);

        // Then: Verificamos que el carrito recuperado es el correcto
        assertThat(carritoEncontrado).isNotNull();
        assertThat(carritoEncontrado.getPrecioTotal()).isEqualTo(250.50);
        assertThat(carritoEncontrado.getProductos()).containsExactlyInAnyOrder(101L, 102L);
    }
}