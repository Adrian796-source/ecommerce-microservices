package com.adrian.venta.repository;

import com.adrian.venta.dto.CarritoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(
        // Aquí le decimos a Spring Cloud Contract dónde encontrar los stubs.
        // El artifactId debe coincidir con el del microservicio proveedor (carrito).
        ids = "com.adrian:carrito:+:stubs:8081",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
public class CarritoAPIContractTest {

    @Autowired
    private ICarritoAPI carritoAPI;

    @Test
    @DisplayName("Test de Contrato: Verificar que el cliente Feign puede consumir el endpoint de carritos")
    void testFindCarritoById() {
        // Arrange
        Long idCarrito = 15L;

        // Act: Llamamos a nuestro cliente Feign. StubRunner interceptará esta llamada
        // y devolverá la respuesta definida en el contrato.
        CarritoDTO carrito = carritoAPI.findCarritoById(idCarrito);

        // Assert: Verificamos que el cliente Feign deserializó la respuesta correctamente.
        assertThat(carrito).isNotNull();
        assertThat(carrito.getIdCarrito()).isEqualTo(idCarrito);
        assertThat(carrito.getPrecioTotal()).isEqualTo(1250.99);
        assertThat(carrito.getProductos()).hasSize(1);
        assertThat(carrito.getProductos().get(0).getNombre()).isEqualTo("Laptop Gamer");
    }
}