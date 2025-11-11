package com.adrian.venta;



import com.adrian.venta.dto.CarritoDTO;
import com.adrian.venta.dto.ProductoDTO;
import com.adrian.venta.dto.VentaDTO;
import com.adrian.venta.model.Venta;
import com.adrian.venta.repository.ICarritoAPI;
import com.adrian.venta.repository.IVentaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VentaComponentTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IVentaRepository ventaRepository;

    @MockBean
    private ICarritoAPI carritoAPI;

    @AfterEach
    void tearDown() {
        // Limpiamos la base de datos después de cada test para mantener el aislamiento
        ventaRepository.deleteAll();
    }

    @Test
    @DisplayName("Test de Componente: Flujo completo para crear y obtener una venta")
    void createAndGetVentaFlow() {
        // --- Parte 1: Crear una Venta ---

        // Arrange: Datos para la nueva venta
        Venta nuevaVenta = new Venta(null, LocalDate.now(), 15L);

        // Act: Realizamos una petición POST real al endpoint del controlador
        ResponseEntity<String> createResponse = restTemplate.postForEntity("/ventas/crear", nuevaVenta, String.class);

        // Assert: Verificamos que la creación fue exitosa
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createResponse.getBody()).isEqualTo("Venta creada exitosamente");

        // --- Parte 2: Verificar que la Venta se guardó y se puede obtener ---

        // Arrange: Simulamos la respuesta de la API de carrito para cuando se pida la venta
        ProductoDTO producto = new ProductoDTO(1L, "Test Product", "Test Brand", 100.0);
        CarritoDTO carrito = new CarritoDTO(15L, 100.0, Collections.singletonList(producto));
        when(carritoAPI.findCarritoById(15L)).thenReturn(carrito);

        // Act: Hacemos una petición GET para obtener la venta que acabamos de crear
        // Asumimos que el ID asignado será 1, ya que la BD está limpia.
        ResponseEntity<VentaDTO> getResponse = restTemplate.getForEntity("/ventas/1", VentaDTO.class);

        // Assert: Verificamos que los datos son correctos
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getCarrito().getIdCarrito()).isEqualTo(15L);
    }

    @Test
    @DisplayName("Test de Componente: Obtener una venta que no existe debe devolver 404")
    void getVenta_whenNotFound_shouldReturn404() {
        // Arrange: Un ID que sabemos que no existe en la base de datos limpia.
        Long idInexistente = 999L;

        // Act: Realizamos una petición GET a un recurso que no existe.
        ResponseEntity<VentaDTO> response = restTemplate.getForEntity("/ventas/" + idInexistente, VentaDTO.class);

        // Assert: Verificamos que la respuesta es un 404 Not Found.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Test de Componente: Obtener todas las ventas debe devolver una lista")
    void findAllVentas_shouldReturnListOfVentas() {
        // Arrange: Creamos y guardamos dos ventas directamente en la BD de prueba.
        ventaRepository.save(new Venta(null, LocalDate.now(), 15L));
        ventaRepository.save(new Venta(null, LocalDate.now().minusDays(1), 16L));

        // Act: Realizamos una petición GET para obtener todas las ventas.
        // Esperamos un array de VentaDTO.
        ResponseEntity<VentaDTO[]> response = restTemplate.getForEntity("/ventas", VentaDTO[].class);

        // Assert: Verificamos que la respuesta es correcta.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(2);
    }

    @Test
    @DisplayName("Test de Componente: Editar una venta debe actualizar los datos")
    void editVenta_shouldUpdateData() {
        // Arrange: Creamos una venta inicial en la BD.
        Venta ventaOriginal = ventaRepository.save(new Venta(null, LocalDate.now(), 15L));
        Long ventaId = ventaOriginal.getIdVenta();

        // Nuevos datos para la venta
        Venta ventaEditada = new Venta(ventaId, LocalDate.now().minusDays(5), 99L);

        // Act: Realizamos una petición PUT para editar la venta.
        // El método put no devuelve cuerpo, solo ejecuta la acción.
        restTemplate.put("/ventas/editar/" + ventaId, ventaEditada);

        // Assert: Verificamos que los datos se actualizaron en la BD.
        // Para esto, volvemos a buscar la venta y comprobamos sus nuevos valores.

        // Simulamos la respuesta de la API de carrito para la nueva ID de carrito
        when(carritoAPI.findCarritoById(99L)).thenReturn(new CarritoDTO(99L, 0.0, Collections.emptyList()));

        ResponseEntity<VentaDTO> response = restTemplate.getForEntity("/ventas/" + ventaId, VentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCarrito().getIdCarrito()).isEqualTo(99L);
        assertThat(response.getBody().getFecha()).isEqualTo(LocalDate.now().minusDays(5));
    }

    @Test
    @DisplayName("Test de Componente: Eliminar una venta debe quitarla de la BD")
    void deleteVenta_shouldRemoveVenta() {
        // Arrange: Creamos una venta para poder eliminarla.
        Venta ventaGuardada = ventaRepository.save(new Venta(null, LocalDate.now(), 15L));
        Long ventaId = ventaGuardada.getIdVenta();

        // Act: Realizamos una petición DELETE para eliminar la venta.
        restTemplate.delete("/ventas/eliminar/" + ventaId);

        // Assert: Verificamos que la venta ya no existe.
        // La mejor forma es intentar obtenerla y esperar un 404.
        ResponseEntity<VentaDTO> response = restTemplate.getForEntity("/ventas/" + ventaId, VentaDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
