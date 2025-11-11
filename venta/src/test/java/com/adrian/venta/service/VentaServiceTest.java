package com.adrian.venta.service;

import com.adrian.venta.dto.CarritoDTO;
import com.adrian.venta.dto.ProductoDTO;
import com.adrian.venta.dto.VentaDTO;
import com.adrian.venta.model.Venta;
import com.adrian.venta.repository.ICarritoAPI;
import com.adrian.venta.repository.IVentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private IVentaRepository ventaRepository;

    @Mock
    private ICarritoAPI carritoAPIClient;

    @InjectMocks
    private VentaService ventaService;

    private Venta venta;
    private CarritoDTO carritoDTO;
    private ProductoDTO productoDTO;

    @BeforeEach
    void setUp() {
        // 1. Datos de prueba para un ProductoDTO
        productoDTO = new ProductoDTO(1L, "Mouse", "Logitech", 50.0);

        // 2. Datos de prueba para un CarritoDTO
        carritoDTO = new CarritoDTO();
        carritoDTO.setIdCarrito(15L);
        carritoDTO.setPrecioTotal(50.0);
        carritoDTO.setProductos(Collections.singletonList(productoDTO));

        // 3. Datos de prueba para una Venta
        venta = new Venta();
        venta.setIdVenta(1L);
        venta.setFecha(LocalDate.now());
        venta.setIdCarrito(15L);
    }

    @Test
    void saveVenta() {
        // Arrange: Este test ahora asume que el método saveVenta recibe un objeto Venta
        // y no devuelve nada (o devuelve la Venta guardada).
        Venta ventaParaGuardar = new Venta();
        ventaParaGuardar.setFecha(LocalDate.now());
        ventaParaGuardar.setIdCarrito(15L);

        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);

        // Act: Ejecutar el método a probar
        ventaService.saveVenta(ventaParaGuardar);

        // Assert: Verificar que el repositorio fue llamado para guardar la venta
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }

    @Test
    void findVenta() {
        // Arrange
        // Este test prueba que el método findVenta ensambla correctamente
        // la Venta con su CarritoDTO.
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        // CORRECCIÓN: Faltaba simular la llamada a la API de Carrito.
        when(carritoAPIClient.findCarritoById(15L)).thenReturn(carritoDTO);

        // Act
        VentaDTO result = ventaService.findVenta(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdVenta());
        // CORRECCIÓN: Primero, asegurar que el objeto Carrito no es nulo.
        assertNotNull(result.getCarrito());
        // CORRECCIÓN: Luego, comparar el ID dentro del objeto Carrito.
        assertEquals(15L, result.getCarrito().getIdCarrito());
        verify(ventaRepository, times(1)).findById(1L);
        verify(carritoAPIClient, times(1)).findCarritoById(15L);
    }

    @Test
    // CORRECCIÓN: Renombramos el test para describir mejor lo que hace.
    void findVenta_shouldReturnVentaWithCarrito() {
        // Arrange
        Long idVenta = 1L;
        Long idCarrito = 15L;

        // Mock para que el repositorio devuelva nuestra venta de prueba
        when(ventaRepository.findById(idVenta)).thenReturn(Optional.of(venta));
        // Mock para que el cliente API devuelva nuestro carrito de prueba
        when(carritoAPIClient.findCarritoById(idCarrito)).thenReturn(carritoDTO);

        // Act: Llamar al método que ensambla la Venta con su Carrito.
        // CORRECCIÓN: El método que hace esto probablemente se llama findVenta en tu servicio.
        VentaDTO result = ventaService.findVenta(idVenta);

        // Assert: Verificar que el DTO compuesto es correcto
        assertNotNull(result);
        assertEquals(idVenta, result.getIdVenta());
        assertEquals(venta.getFecha(), result.getFecha());
        assertNotNull(result.getCarrito());
        assertEquals(idCarrito, result.getCarrito().getIdCarrito());
        assertFalse(result.getCarrito().getProductos().isEmpty());
        assertEquals(1, result.getCarrito().getProductos().size());
        assertEquals("Mouse", result.getCarrito().getProductos().get(0).getNombre());

        // Verificar que los mocks fueron llamados
        verify(ventaRepository, times(1)).findById(idVenta);
        verify(carritoAPIClient, times(1)).findCarritoById(idCarrito);
    }


    @Test
    void findAllVentas() {
        // Arrange
        // Simula que el repositorio devuelve una lista con nuestra venta de prueba
        List<Venta> listaVentas = Collections.singletonList(venta);
        when(ventaRepository.findAll()).thenReturn(listaVentas);

        // CORRECCIÓN: Faltaba simular la llamada interna a findById que hace el servicio.
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        // Simula que al buscar el carrito asociado, se encuentra
        when(carritoAPIClient.findCarritoById(15L)).thenReturn(carritoDTO);

        // Act
        // El método del servicio ahora debe devolver una lista de DTOs
        List<VentaDTO> result = ventaService.findAllVentas();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(15L, result.get(0).getCarrito().getIdCarrito()); // Verifica que el carrito se adjuntó
        verify(ventaRepository, times(1)).findAll(); // Verifica que se llamó al repo
    }

    @Test
    void deleteVenta() {
        // Arrange
        Long idVenta = 1L;
        doNothing().when(ventaRepository).deleteById(idVenta);

        // Act
        ventaService.deleteVenta(idVenta);

        // Assert: Verificar que el método deleteById fue llamado una vez
        verify(ventaRepository, times(1)).deleteById(idVenta);
    }

    @Test
    void editVenta() {
        // Arrange
        Long idVenta = 1L;
        // Venta original que simularemos que está en la BD
        Venta ventaOriginal = new Venta(idVenta, LocalDate.now(), 15L);
        // Datos nuevos que se usarán para editar
        Venta ventaEditada = new Venta(idVenta, LocalDate.of(2023, 1, 1), 20L);

        // CORRECCIÓN: Simular la llamada a findById que el servicio hace primero.
        when(ventaRepository.findById(idVenta)).thenReturn(Optional.of(ventaOriginal));
        when(ventaRepository.save(any(Venta.class))).thenReturn(ventaEditada);

        // Act
        ventaService.editVenta(idVenta, ventaEditada);

        // Assert
        // CORRECCIÓN: Usar ArgumentCaptor para verificar el contenido del objeto guardado,
        // en lugar de la instancia exacta.
        ArgumentCaptor<Venta> ventaArgumentCaptor = ArgumentCaptor.forClass(Venta.class);
        verify(ventaRepository, times(1)).save(ventaArgumentCaptor.capture());

        // Obtenemos la venta que fue capturada
        Venta ventaGuardada = ventaArgumentCaptor.getValue();

        // Verificamos que los datos guardados son los correctos
        assertEquals(20L, ventaGuardada.getIdCarrito());
        assertEquals(LocalDate.of(2023, 1, 1), ventaGuardada.getFecha());
    }

    @Test
    void fallbackFindCarrito() {
        // Arrange
        Long idCarrito = 999L;
        Throwable throwable = new RuntimeException("Error de conexión");

        // Act
        CarritoDTO result = ventaService.fallbackFindCarrito(idCarrito, throwable);

        // Assert
        assertNotNull(result);
        // Verifica que el ID del carrito es el valor por defecto del fallback
        // CORRECCIÓN: El fallback probablemente devuelve un DTO con el ID original para consistencia.
        assertEquals(idCarrito, result.getIdCarrito());
    }

    @Test
    @DisplayName("Test para buscar una venta que no existe y devolver null")
    void findVenta_whenNotFound_shouldReturnNull() {
        // Arrange
        Long idInexistente = 99L;
        // Simulamos que el repositorio devuelve un Optional vacío porque no encuentra la venta
        when(ventaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act
        VentaDTO result = ventaService.findVenta(idInexistente);

        // Assert
        // Verificamos que el servicio devuelve null, cubriendo la rama del if
        assertNull(result);
        // Verificamos que el repositorio fue llamado
        verify(ventaRepository, times(1)).findById(idInexistente);
        // Verificamos que la API de carrito NUNCA fue llamada, ahorrando una llamada de red innecesaria
        verify(carritoAPIClient, never()).findCarritoById(anyLong());
    }

}