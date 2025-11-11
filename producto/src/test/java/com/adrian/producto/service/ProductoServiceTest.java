package com.adrian.producto.service;

import com.adrian.producto.dto.ProductoDTO;
import com.adrian.producto.model.Producto;
import com.adrian.producto.repository.IProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita Mockito en esta clase de prueba
class ProductoServiceTest {

    // @Mock crea una simulación (un "doble de prueba") del repositorio.
    @Mock
    private IProductoRepository productoRepository;

    // @InjectMocks crea una instancia real de ProductoService
    // e inyecta los mocks (como productoRepository) en ella.
    @InjectMocks
    private ProductoService productoService;

    private Producto productoEntidad1;
    private ProductoDTO productoDTO1;

    @BeforeEach
    void setUp() {
        // Preparamos datos de prueba que usaremos en los tests
        productoEntidad1 = new Producto(1L, "Laptop", "TechCo", 1200.00);
        productoDTO1 = new ProductoDTO(1L, "Laptop", "TechCo", 1200.00);
    }

    @Test
    void getProductos() {
        // 1. Arrange (Preparar)
        Producto productoEntidad2 = new Producto(2L, "Mouse", "TechCo", 25.00);
        when(productoRepository.findAll()).thenReturn(Arrays.asList(productoEntidad1, productoEntidad2));

        // 2. Act (Actuar)
        List<ProductoDTO> productosEncontrados = productoService.getProductos();

        // 3. Assert (Verificar)
        assertNotNull(productosEncontrados);
        assertEquals(2, productosEncontrados.size());
        assertEquals("Laptop", productosEncontrados.get(0).getNombre());
    }

    @Test
    void getProducto() {
        // Arrange
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoEntidad1));

        // Act
        ProductoDTO productoEncontrado = productoService.getProducto(1L);

        // Assert
        assertNotNull(productoEncontrado);
        assertEquals("Laptop", productoEncontrado.getNombre());
    }

    @Test
    void createProducto() {
        // Arrange
        // No necesitamos 'when' porque save() no devuelve nada que usemos aquí.

        // Act
        productoService.createProducto(productoDTO1);

        // Assert
        // Verificamos que el método save del repositorio fue llamado exactamente una vez.
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void editProducto() {
        // Arrange
        // Simulamos que encontramos el producto en la BD
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoEntidad1));
        ProductoDTO productoEditadoDto = new ProductoDTO(1L, "Laptop Gamer", "TechCo", 1500.00);

        // Act
        productoService.editProducto(1L, productoEditadoDto);

        // Assert
        // Capturamos el objeto que se pasó al método save() para verificarlo
        ArgumentCaptor<Producto> productoCaptor = ArgumentCaptor.forClass(Producto.class);
        verify(productoRepository).save(productoCaptor.capture());

        Producto productoGuardado = productoCaptor.getValue();
        assertEquals("Laptop Gamer", productoGuardado.getNombre());
        assertEquals(1500.00, productoGuardado.getPrecio());
    }

    @Test
    void deleteProducto() {
        // Arrange - No necesitamos 'when' porque deleteById() no devuelve nada.

        // Act
        productoService.deleteProducto(1L);

        // Assert
        // Verificamos que el método deleteById del repositorio fue llamado exactamente una vez con el ID 1.
        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    void getProducto_deberiaDevolverNull_cuandoProductoNoExiste() {
        // Arrange
        // Simulamos que el repositorio no encuentra nada y devuelve un Optional vacío.
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        ProductoDTO productoEncontrado = productoService.getProducto(99L);

        // Assert
        // Verificamos que el servicio devuelve null, como está en tu lógica.
        assertNull(productoEncontrado);
    }

    @Test
    void editProducto_noDeberiaHacerNada_cuandoProductoNoExiste() {
        // Arrange
        // Simulamos que el repositorio no encuentra el producto a editar.
        long idInexistente = 99L;
        when(productoRepository.findById(idInexistente)).thenReturn(Optional.empty());
        ProductoDTO datosNuevos = new ProductoDTO(idInexistente, "Producto Fantasma", "Marca", 1.0);

        // Act
        // Llamamos al método para editar un producto que no existe.
        productoService.editProducto(idInexistente, datosNuevos);

        // Assert
        // Verificamos que el método 'save' del repositorio NUNCA fue llamado.
        verify(productoRepository, never()).save(any(Producto.class));
    }
}
