package com.adrian.venta;

import com.adrian.venta.dto.VentaDTO;
import com.adrian.venta.model.Venta;
import com.adrian.venta.repository.IVentaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VentaResilienceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IVentaRepository ventaRepository;

    @AfterEach
    void tearDown() {
        ventaRepository.deleteAll();
    }

    @Test
    @DisplayName("Test de Resiliencia: Obtener una venta debe activar el fallback cuando carrito no está disponible")
    void getVenta_whenCarritoIsDown_shouldTriggerFallback() {
        // Arrange: Creamos una venta en la BD.
        Venta ventaGuardada = ventaRepository.save(new Venta(null, LocalDate.now(), 15L));
        Long ventaId = ventaGuardada.getIdVenta();

        // Act: Hacemos una petición GET. El servicio 'carrito' NO está levantado.
        ResponseEntity<VentaDTO> response = restTemplate.getForEntity("/ventas/" + ventaId, VentaDTO.class);

        // Assert: Verificamos que la respuesta es exitosa (200 OK) pero contiene los datos del fallback.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCarrito().getIdCarrito()).isEqualTo(15L); // El ID se preserva
        assertThat(response.getBody().getCarrito().getPrecioTotal()).isEqualTo(-1.0); // El precio del fallback
    }
}