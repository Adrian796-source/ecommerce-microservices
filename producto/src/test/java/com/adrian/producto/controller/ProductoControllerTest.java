package com.adrian.producto.controller;

import com.adrian.producto.dto.ProductoDTO;
import com.adrian.producto.service.IProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest se enfoca solo en la capa web (el controlador).
// Es mucho más ligero que levantar toda la aplicación.
@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    // MockMvc nos permite simular llamadas HTTP a nuestros endpoints.
    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper para convertir objetos a JSON
    @Autowired
    private ObjectMapper objectMapper;

    // @MockBean crea un mock del servicio. Como ya probamos el servicio,
    // aquí solo simulamos su comportamiento para probar el controlador.
    @MockBean
    private IProductoService productoService;

    private ProductoDTO productoDTO1;
    private ProductoDTO productoDTO2;

    @BeforeEach
    void setUp() {
        productoDTO1 = new ProductoDTO(1L, "Laptop", "TechCo", 1200.00);
        productoDTO2 = new ProductoDTO(2L, "Mouse", "TechCo", 25.00);
    }

    @Test
    void deberiaDevolverListaDeProductos() throws Exception {
        // 1. Arrange
        List<ProductoDTO> listaProductos = Arrays.asList(productoDTO1, productoDTO2);

        // Simulamos que el servicio devolverá esta lista cuando se le llame.
        when(productoService.getProductos()).thenReturn(listaProductos);

        // 2. Act & 3. Assert
        mockMvc.perform(get("/api/productos") // Hacemos una llamada GET a nuestro endpoint
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verificamos que el estado HTTP es 200 OK
                .andExpect(jsonPath("$[0].nombre").value("Laptop")) // Verificamos el contenido del JSON
                .andExpect(jsonPath("$[1].nombre").value("Mouse"));
    }

    @Test
    void deberiaDevolverProductoPorId() throws Exception {
        // Arrange
        when(productoService.getProducto(1L)).thenReturn(productoDTO1);

        // Act & Assert
        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Laptop"))
                .andExpect(jsonPath("$.precio").value(1200.00));
    }

    @Test
    void deberiaCrearUnNuevoProducto() throws Exception {
        // Arrange
        ProductoDTO productoACrear = new ProductoDTO(null, "Teclado", "Keychron", 150.00);
        // No necesitamos 'when' porque el método del servicio es void.

        // Act & Assert
        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoACrear)))
                .andExpect(status().isCreated());
    }

    @Test
    void deberiaEditarUnProducto() throws Exception {
        // Arrange
        ProductoDTO productoEditado = new ProductoDTO(1L, "Laptop Pro", "TechCo", 1800.00);
        // No necesitamos 'when' porque el método del servicio es void.

        // Act & Assert
        mockMvc.perform(put("/api/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoEditado)))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaEliminarUnProducto() throws Exception {
        // Arrange: El método del servicio es void, no necesita 'when'.

        // Act & Assert
        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaDevolverNotFoundCuandoProductoNoExiste() throws Exception {
        // Arrange
        // Simulamos que el servicio devuelve null porque no encontró el producto.
        long idInexistente = 99L;
        when(productoService.getProducto(idInexistente)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/productos/" + idInexistente))
                .andExpect(status().isNotFound());
    }

}
