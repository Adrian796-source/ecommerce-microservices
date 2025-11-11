package com.adrian.carrito.controller;

import com.adrian.carrito.dto.CarritoDTO;
import com.adrian.carrito.dto.ProductoDTO;
import com.adrian.carrito.service.ICarritoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarritoController.class)
class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICarritoService carritoService;

    @Autowired
    private ObjectMapper objectMapper;

    private CarritoDTO carritoDTO;

    @BeforeEach
    void setUp() {
        ProductoDTO productoDTO = new ProductoDTO(10L, "Test Product", "Test Brand", 100.50);
        carritoDTO = new CarritoDTO(1L, 100.50, List.of(productoDTO));
    }

    @Test
    @DisplayName("Crear un carrito")
    void testCreateCarrito() throws Exception {
        List<Long> idsProductos = List.of(10L);
        doNothing().when(carritoService).createCarrito(idsProductos);

        mockMvc.perform(post("/carritos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(idsProductos)))
                .andExpect(status().isOk())
                .andExpect(content().string("Carrito creado exitosamente"));
    }

    @Test
    @DisplayName("Obtener un carrito por ID")
    void testGetCarrito() throws Exception {
        when(carritoService.getCarrito(1L)).thenReturn(carritoDTO);

        mockMvc.perform(get("/carritos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito").value(1L))
                .andExpect(jsonPath("$.precioTotal").value(100.50));
    }

    @Test
    @DisplayName("Obtener todos los carritos")
    void testGetCarritos() throws Exception {
        when(carritoService.getCarritos()).thenReturn(Collections.singletonList(carritoDTO));

        mockMvc.perform(get("/carritos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCarrito").value(1L));
    }

    @Test
    @DisplayName("Eliminar un carrito")
    void testDeleteCarrito() throws Exception {
        doNothing().when(carritoService).deleteCarrito(1L);

        mockMvc.perform(delete("/carritos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Carrito eliminado exitosamente"));
    }

    @Test
    @DisplayName("Añadir un producto a un carrito")
    void testAddProducto() throws Exception {
        doNothing().when(carritoService).addProducto(1L, 10L);

        mockMvc.perform(post("/carritos/1/productos/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto agregado al carrito exitosamente"));
    }

    @Test
    @DisplayName("Eliminar un producto de un carrito")
    void testDeleteProducto() throws Exception {
        doNothing().when(carritoService).deleteProducto(1L, 10L);

        mockMvc.perform(delete("/carritos/1/productos/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto eliminado del carrito exitosamente"));
    }
}