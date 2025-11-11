package com.adrian.carrito.service;

import com.adrian.carrito.dto.ProductoDTO;
import com.adrian.carrito.model.Carrito;
import com.adrian.carrito.repository.ICarritoRepository;
import com.adrian.carrito.repository.IProductoAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarritoServiceTest {

    @Mock
    private ICarritoRepository carritoRepository;

    @Mock
    private IProductoAPI productoAPI;

    @InjectMocks
    private CarritoService carritoService;

    private Carrito carrito;
    private ProductoDTO producto;

    @BeforeEach
    void setUp() {
        // Objeto Carrito base para los tests
        carrito = new Carrito(1L, 0.0, new ArrayList<>());
        // Objeto ProductoDTO base para los tests
        producto = new ProductoDTO(10L, "Mouse", "Razer", 50.0);
    }

    @Test
    @DisplayName("Crear un carrito nuevo")
    void createCarrito() {
        // Given: Simulamos la llamada a la API para calcular el precio
        when(productoAPI.getProducto(10L)).thenReturn(producto);

        // When
        carritoService.createCarrito(List.of(10L));

        // Then
        // Verificamos que se guarde un carrito con el precio total correcto
        verify(carritoRepository, times(1)).save(argThat(savedCarrito ->
                savedCarrito.getPrecioTotal() == 50.0 &&
                        savedCarrito.getProductos().contains(10L)
        ));
    }

    @Test
    @DisplayName("Añadir un producto a un carrito existente")
    void addProducto() {
        // Given
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(productoAPI.getProducto(10L)).thenReturn(producto);

        // When
        carritoService.addProducto(1L, 10L);

        // Then
        verify(carritoRepository, times(1)).findById(1L);
        verify(productoAPI, times(1)).getProducto(10L);
        verify(carritoRepository, times(1)).save(carrito);

        assertEquals(50.0, carrito.getPrecioTotal());
        assertTrue(carrito.getProductos().contains(10L));
    }

    @Test
    @DisplayName("No hacer nada al intentar añadir un producto a un carrito inexistente")
    void addProducto_whenCarritoNotFound() {
        // Given: El repositorio no encuentra el carrito
        when(carritoRepository.findById(99L)).thenReturn(Optional.empty());

        // When: Se intenta añadir un producto
        carritoService.addProducto(99L, 10L);

        // Then: Verificamos que se buscó el carrito pero no se guardó nada
        verify(carritoRepository, times(1)).findById(99L);
        verify(carritoRepository, never()).save(any(Carrito.class));
    }

    @Test
    @DisplayName("Eliminar un producto de un carrito")
    void deleteProducto() {
        // Given: El carrito ya tiene un producto
        carrito.getProductos().add(10L);
        carrito.setPrecioTotal(50.0);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));

        // When
        carritoService.deleteProducto(1L, 10L);

        // Then
        verify(carritoRepository, times(1)).findById(1L);
        verify(carritoRepository, times(1)).save(carrito);

        assertEquals(0.0, carrito.getPrecioTotal());
        assertFalse(carrito.getProductos().contains(10L));
    }

    @Test
    @DisplayName("No hacer nada al intentar eliminar un producto de un carrito inexistente")
    void deleteProducto_whenCarritoNotFound() {
        // Given: El repositorio no encuentra el carrito
        when(carritoRepository.findById(99L)).thenReturn(Optional.empty());

        // When: Se intenta eliminar un producto
        carritoService.deleteProducto(99L, 10L);

        // Then: Verificamos que se buscó el carrito
        verify(carritoRepository, times(1)).findById(99L);
        // Y muy importante, verificamos que NUNCA se intentó guardar nada
        verify(carritoRepository, never()).save(any(Carrito.class));
    }

    @Test
    @DisplayName("Obtener un carrito por su ID")
    void getCarrito() {
        // Given: El carrito tiene un producto
        carrito.getProductos().add(10L);
        carrito.setPrecioTotal(50.0);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(productoAPI.getProducto(10L)).thenReturn(producto);

        // When
        var carritoDTO = carritoService.getCarrito(1L);

        // Then
        assertNotNull(carritoDTO);
        assertEquals(50.0, carritoDTO.getPrecioTotal());
        assertEquals(1, carritoDTO.getProductos().size());
        assertEquals("Mouse", carritoDTO.getProductos().get(0).getNombre());
    }

    @Test
    @DisplayName("Devolver null al intentar obtener un carrito inexistente")
    void getCarrito_whenCarritoNotFound() {
        // Given: El repositorio no encuentra el carrito
        when(carritoRepository.findById(99L)).thenReturn(Optional.empty());

        // When: Se intenta obtener el carrito
        var carritoDTO = carritoService.getCarrito(99L);

        // Then: El resultado debe ser nulo
        assertNull(carritoDTO);
        verify(carritoRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Recalcular precio a cero si la lista de productos es nula")
    void recalculateAndSetPrecioTotal_whenProductListIsNull() {
        // Given: Un carrito con lista de productos nula
        carrito.setProductos(null);

        // When: Se crea el carrito (lo que dispara el recálculo)
        carritoService.createCarrito(null);

        // Then: Verificamos que se guarde con precio 0
        verify(carritoRepository).save(argThat(c -> c.getPrecioTotal() == 0.0));
    }

    @Test
    @DisplayName("Obtener todos los carritos")
    void getCarritos() {
        // Given
        when(carritoRepository.findAll()).thenReturn(Collections.singletonList(carrito));

        // When
        var carritos = carritoService.getCarritos();

        // Then
        verify(carritoRepository, times(1)).findAll();
        assertFalse(carritos.isEmpty());
        assertEquals(1, carritos.size());
    }

    @Test
    @DisplayName("Eliminar un carrito por su ID")
    void deleteCarrito() {
        // Given: No se necesita configuración previa

        // When
        carritoService.deleteCarrito(1L);

        // Then
        verify(carritoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Buscar un producto por ID (llamada a API externa)")
    void findProductoById() {
        // Given
        when(productoAPI.getProducto(10L)).thenReturn(producto);

        // When
        var foundProducto = carritoService.findProductoById(10L);

        // Then
        verify(productoAPI, times(1)).getProducto(10L);
        assertNotNull(foundProducto);
        assertEquals("Mouse", foundProducto.getNombre());
    }

    @Test
    @DisplayName("Probar el método fallback para buscar producto")
    void fallbackFindProducto() {
        // When
        var fallbackProducto = carritoService.fallbackFindProducto(1L, new Throwable());

        // Then
        assertNotNull(fallbackProducto);
        assertEquals(1L, fallbackProducto.getIdProducto()); // Debe devolver el ID que se le pasó
        assertEquals("Producto no encontrado", fallbackProducto.getNombre());
        assertEquals(0.0, fallbackProducto.getPrecio());
    }

    @Test
    @DisplayName("Obtener un carrito con productos no encontrados (null) en la lista")
    void getCarrito_withProductNotFoundInList() {
        // Given: Un carrito con un producto existente y uno que no se encontrará
        carrito.getProductos().add(10L); // Producto existente
        carrito.getProductos().add(99L); // Producto que no se encontrará
        carrito.setPrecioTotal(50.0); // Precio inicial (no se recalcula aquí)

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(productoAPI.getProducto(10L)).thenReturn(producto);
        when(productoAPI.getProducto(99L)).thenReturn(null); // Simula que el producto 99 no existe

        // When
        var carritoDTO = carritoService.getCarrito(1L);

        // Then: El carritoDTO debe contener solo el producto encontrado
        assertNotNull(carritoDTO);
        assertEquals(1, carritoDTO.getProductos().size());
        assertEquals("Mouse", carritoDTO.getProductos().get(0).getNombre());
        // El precio total no se recalcula en getCarrito, se usa el del Carrito model
        assertEquals(50.0, carritoDTO.getPrecioTotal());

        verify(carritoRepository, times(1)).findById(1L);
        verify(productoAPI, times(1)).getProducto(10L);
        verify(productoAPI, times(1)).getProducto(99L);
    }


    @Test
    @DisplayName("Recalcular precio total ignorando productos no encontrados (null)")
    void recalculateAndSetPrecioTotal_ignoringNullProducts() {
        // Given: Un carrito con un producto existente y uno que no se encontrará
        carrito.getProductos().add(10L); // Producto existente
        carrito.getProductos().add(99L); // Producto que no se encontrará

        when(productoAPI.getProducto(10L)).thenReturn(producto);
        when(productoAPI.getProducto(99L)).thenReturn(null); // Simula que el producto 99 no existe

        // When: Creamos el carrito (lo que dispara el recálculo)
        carritoService.createCarrito(carrito.getProductos());

        // Then: Verificamos que el precio total solo incluye el producto encontrado
        verify(carritoRepository).save(argThat(c ->
                c.getPrecioTotal() == 50.0 &&
                        c.getProductos().containsAll(List.of(10L, 99L))
        ));
    }

    @Test
    @DisplayName("Recalcular precio total ignorando productos con precio nulo")
    void recalculateAndSetPrecioTotal_ignoringNullPriceProducts() {
        // Given: Un carrito con un producto con precio nulo
        ProductoDTO productoConPrecioNulo = new ProductoDTO(20L, "Producto sin precio", "Marca", null);
        carrito.getProductos().add(20L);

        when(productoAPI.getProducto(20L)).thenReturn(productoConPrecioNulo);

        // When: Creamos el carrito (lo que dispara el recálculo)
        carritoService.createCarrito(carrito.getProductos());

        // Then: Verificamos que el precio total es 0.0 (ya que el producto inicial no tiene precio)
        verify(carritoRepository).save(argThat(c -> c.getPrecioTotal() == 0.0));
    }


}