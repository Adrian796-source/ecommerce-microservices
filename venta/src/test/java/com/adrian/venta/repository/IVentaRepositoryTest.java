package com.adrian.venta.repository;

import com.adrian.venta.model.Venta;
import com.adrian.venta.repository.IVentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class IVentaRepositoryTest {

    @Autowired
    private IVentaRepository ventaRepository;


    private Venta venta;

    @BeforeEach
    void setUp() {
        venta = new Venta();
        venta.setFecha(LocalDate.of(2024, 5, 21));
        venta.setIdCarrito(100L);
    }

    @Test
    @DisplayName("Test para guardar una venta")
    void saveVenta_shouldReturnSavedVenta() {
        // Arrange
        Venta ventaGuardada = ventaRepository.save(venta);

        // Assert
        assertThat(ventaGuardada).isNotNull();
        assertThat(ventaGuardada.getIdVenta()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test para listar todas las ventas")
    void findAllVentas_shouldReturnVentasList() {
        // Arrange
        Venta venta2 = new Venta();
        venta2.setFecha(LocalDate.of(2024, 5, 22));
        venta2.setIdCarrito(101L);

        ventaRepository.save(venta);
        ventaRepository.save(venta2);

        // Act
        List<Venta> listaVentas = ventaRepository.findAll();

        // Assert
        assertThat(listaVentas).isNotNull();
        assertThat(listaVentas.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test para obtener una venta por su ID")
    void findVentaById_shouldReturnVenta() {
        // Arrange
        ventaRepository.save(venta);

        // Act
        Optional<Venta> ventaEncontrada = ventaRepository.findById(venta.getIdVenta());

        // Assert
        assertThat(ventaEncontrada).isPresent();
        assertThat(ventaEncontrada.get().getIdCarrito()).isEqualTo(100L);
    }

    @Test
    @DisplayName("Test para eliminar una venta")
    void deleteVenta_shouldRemoveVenta() {
        // Arrange
        Venta ventaGuardada = ventaRepository.save(venta);

        // Act
        ventaRepository.deleteById(ventaGuardada.getIdVenta());
        Optional<Venta> ventaEliminada = ventaRepository.findById(ventaGuardada.getIdVenta());

        // Assert
        assertThat(ventaEliminada).isNotPresent();
    }
}


