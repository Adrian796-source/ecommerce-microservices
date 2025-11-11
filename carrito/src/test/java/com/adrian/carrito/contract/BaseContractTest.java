package com.adrian.carrito.contract;

import com.adrian.carrito.controller.CarritoController;
import com.adrian.carrito.dto.CarritoDTO;
import com.adrian.carrito.dto.ProductoDTO;
import com.adrian.carrito.service.ICarritoService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CarritoController.class)
public abstract class BaseContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICarritoService carritoService;

    @BeforeEach
    public void setup() {
        // 1. Preparamos los datos que el contrato espera
        ProductoDTO producto = new ProductoDTO(1L, "Laptop Gamer", "Asus", 1250.99);
        CarritoDTO carrito = new CarritoDTO(15L, 1250.99, Collections.singletonList(producto));

        // 2. Simulamos el comportamiento del servicio
        // Cuando el test generado pida el carrito 15, nuestro servicio mockeado devolverá los datos correctos.
        when(carritoService.getCarrito(15L)).thenReturn(carrito);

        // 3. Configuramos RestAssured para que use nuestro MockMvc
        // Esto permite que el test generado haga peticiones a nuestro controlador simulado.
        RestAssuredMockMvc.mockMvc(mockMvc);
    }
}