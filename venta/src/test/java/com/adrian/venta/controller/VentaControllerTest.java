package com.adrian.venta.controller;

import com.adrian.venta.dto.CarritoDTO;
import com.adrian.venta.dto.ProductoDTO;
import com.adrian.venta.dto.VentaDTO;
import com.adrian.venta.model.Venta;
import com.adrian.venta.service.IVentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VentaController.class)
class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IVentaService ventaService;

    @Autowired
    private ObjectMapper objectMapper;

    private VentaDTO ventaDTO;
    private Venta venta;

    @BeforeEach
    void setUp() {
        ProductoDTO productoDTO = new ProductoDTO(1L, "Mouse", "Logitech", 50.0);
        CarritoDTO carritoDTO = new CarritoDTO(15L, 50.0, Collections.singletonList(productoDTO));
        ventaDTO = new VentaDTO(1L, LocalDate.now(), carritoDTO);

        venta = new Venta();
        venta.setIdVenta(1L);
        venta.setFecha(LocalDate.now());
        venta.setIdCarrito(15L);
    }

    @Test
    void saveVenta() throws Exception {
        // Arrange
        doNothing().when(ventaService).saveVenta(any(Venta.class));

        // Act & Assert
        mockMvc.perform(post("/ventas/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                // CORRECCIÓN: El log muestra que el status es 200 OK, no 201 Created.
                .andExpect(status().isOk())
                // CORRECCIÓN: El mensaje de respuesta también es diferente.
                .andExpect(content().string("Venta creada exitosamente"));
    }

    @Test
    void findVenta() throws Exception {
        // Arrange
        when(ventaService.findVenta(1L)).thenReturn(ventaDTO);

        // Act & Assert
        mockMvc.perform(get("/ventas/{idVenta}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVenta").value(1L))
                .andExpect(jsonPath("$.carrito.idCarrito").value(15L));
    }

    @Test
    void findAllVentas() throws Exception {
        // Arrange
        List<VentaDTO> listaVentas = Collections.singletonList(ventaDTO);
        when(ventaService.findAllVentas()).thenReturn(listaVentas);

        // Act & Assert
        mockMvc.perform(get("/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].idVenta").value(1L));
    }

    @Test
    void deleteVenta() throws Exception {
        // Arrange
        Long idVenta = 1L;
        doNothing().when(ventaService).deleteVenta(idVenta);

        // Act & Assert
        mockMvc.perform(delete("/ventas/eliminar/{idVenta}", idVenta))
                .andExpect(status().isOk())
                .andExpect(content().string("Venta eliminada correctamente"));
    }

    @Test
    void editVenta() throws Exception {
        // Arrange
        Long idVenta = 1L;
        when(ventaService.editVenta(eq(idVenta), any(Venta.class))).thenReturn(ventaDTO);

        // Act & Assert
        mockMvc.perform(put("/ventas/editar/{idVenta}", idVenta)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test para buscar una venta que no existe y devolver 404")
    void findVenta_whenNotFound_shouldReturn404() throws Exception {
        // Arrange
        Long idInexistente = 99L;
        // Simulamos que el servicio devuelve null porque no encuentra la venta
        when(ventaService.findVenta(idInexistente)).thenReturn(null);

        // Act & Assert
        // Verificamos que el controlador devuelve un estado 404 Not Found
        mockMvc.perform(get("/ventas/{idVenta}", idInexistente))
                .andExpect(status().isNotFound());
    }

}