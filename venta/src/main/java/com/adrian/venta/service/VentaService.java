package com.adrian.venta.service;

import com.adrian.venta.dto.CarritoDTO;
import com.adrian.venta.dto.VentaDTO;
import com.adrian.venta.model.Venta;
import com.adrian.venta.repository.ICarritoAPI;
import com.adrian.venta.repository.IVentaRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService implements IVentaService {


    private final IVentaRepository ventaRepo;
    private final ICarritoAPI carritoAPI;
    private final ICarritoClient carritoClient;

    public VentaService(IVentaRepository ventaRepo, ICarritoAPI carritoAPI, ICarritoClient carritoClient) {
        this.ventaRepo = ventaRepo;
        this.carritoAPI = carritoAPI;
        this.carritoClient = carritoClient;
    }

    @Override
    public void saveVenta(Venta venta) {
        // CORRECCIÓN: El método saveVenta solo debe guardar la entidad.
        ventaRepo.save(venta);
    }

    @Override
    public VentaDTO findVenta(Long idVenta) {
        Venta venta = ventaRepo.findById(idVenta).orElse(null);
        if (venta == null) {
            return null;
        }

        // Llamamos al método en el nuevo bean, que sí está protegido por el proxy
        CarritoDTO carrito = carritoClient.findCarritoById(venta.getIdCarrito());

        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setIdVenta(venta.getIdVenta());

        ventaDTO.setFecha(venta.getFecha());
        ventaDTO.setCarrito(carrito);

        return ventaDTO;
    }

    @Override
    public List<VentaDTO> findAllVentas() {
        List<Venta> listaVentas = ventaRepo.findAll();
        List<VentaDTO> listaVentasDTO = new ArrayList<>();

        for (Venta venta : listaVentas) {
            listaVentasDTO.add(this.findVenta(venta.getIdVenta()));
        }

        return listaVentasDTO;
    }

    @Override
    public void deleteVenta(Long idVenta) {
        ventaRepo.deleteById(idVenta);
    }

    @Override
    public VentaDTO editVenta(Long idVenta, Venta venta) {
        Venta ventaExistente = ventaRepo.findById(idVenta).orElse(null);

        if (ventaExistente != null) {
            ventaExistente.setFecha(venta.getFecha());
            ventaExistente.setIdCarrito(venta.getIdCarrito());
            this.saveVenta(ventaExistente);

            return this.findVenta(idVenta);
        }
        return null;
    }

    // --- NUEVO MÉTODO PROTEGIDO Y SU FALLBACK ---

    @CircuitBreaker(name = "carrito-service", fallbackMethod = "fallbackFindCarrito") // <-- CAMBIO: La protección se mueve aquí
    public CarritoDTO findCarritoById(Long idCarrito) {
        // Este método ahora es el único que habla con la API de carrito
        return carritoAPI.findCarritoById(idCarrito);
    }

    public CarritoDTO fallbackFindCarrito(Long idCarrito, Throwable t) {
        // Este fallback devuelve un carrito "fantasma" para que la aplicación no falle.
        return new CarritoDTO(idCarrito, -1.0, new ArrayList<>());
    }
}
