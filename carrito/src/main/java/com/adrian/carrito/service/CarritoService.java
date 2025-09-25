package com.adrian.carrito.service;

import com.adrian.carrito.dto.CarritoDTO;
import com.adrian.carrito.dto.ProductoDTO;
import com.adrian.carrito.model.Carrito;
import com.adrian.carrito.repository.ICarritoRepository;
import com.adrian.carrito.repository.IProductoAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoService implements ICarritoService {

    @Autowired
    private ICarritoRepository carritoRepository;

    @Autowired
    private IProductoAPI productoAPI;

    // --- MÉTODOS DE ESCRITURA (Sin cambios, pero ahora están protegidos indirectamente) ---

    @Override
    public void createCarrito(List<Long> idsProductos) {
        Carrito carrito = new Carrito();
        carrito.setProductos(idsProductos);
        this.recalculateAndSetPrecioTotal(carrito);
        carritoRepository.save(carrito);
    }

    @Override
    public void addProducto(Long idCarrito, Long idProducto) {
        Optional<Carrito> optionalCarrito = carritoRepository.findById(idCarrito);
        if (optionalCarrito.isPresent()) {
            Carrito carrito = optionalCarrito.get();
            carrito.getProductos().add(idProducto);
            this.recalculateAndSetPrecioTotal(carrito);
            carritoRepository.save(carrito);
        }
    }

    @Override
    public void deleteProducto(Long idCarrito, Long idProducto) {
        Optional<Carrito> optionalCarrito = carritoRepository.findById(idCarrito);
        if (optionalCarrito.isPresent()) {
            Carrito carrito = optionalCarrito.get();
            carrito.getProductos().remove(idProducto);
            this.recalculateAndSetPrecioTotal(carrito);
            carritoRepository.save(carrito);
        }
    }

    // --- MÉTODO DE LECTURA (Simplificado) ---

    @Override
    public CarritoDTO getCarrito(Long idCarrito) { // <-- CAMBIO: Ya no necesita Circuit Breaker aquí
        Optional<Carrito> optionalCarrito = carritoRepository.findById(idCarrito);
        if (optionalCarrito.isPresent()) {
            Carrito carrito = optionalCarrito.get();
            List<ProductoDTO> listaProductos = new ArrayList<>();
            for (Long idProducto : carrito.getProductos()) {
                // <-- CAMBIO: Llamamos a nuestro método protegido
                ProductoDTO producto = this.findProductoById(idProducto);
                if (producto != null) {
                    listaProductos.add(producto);
                }
            }
            return new CarritoDTO(
                    carrito.getIdCarrito(),
                    carrito.getPrecioTotal(),
                    listaProductos
            );
        }
        return null;
    }

    // --- OTROS MÉTODOS (Sin cambios) ---

    @Override
    public List<CarritoDTO> getCarritos() {
        List<Carrito> carritos = carritoRepository.findAll();
        return carritos.stream()
                .map(carrito -> this.getCarrito(carrito.getIdCarrito()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCarrito(Long idCarrito) {
        carritoRepository.deleteById(idCarrito);
    }

    // --- MÉTODO HELPER PARA RECALCULAR (Simplificado) ---

    private void recalculateAndSetPrecioTotal(Carrito carrito) {
        if (carrito.getProductos() == null || carrito.getProductos().isEmpty()) {
            carrito.setPrecioTotal(0.0);
            return;
        }
        double total = 0.0;
        for (Long idProducto : carrito.getProductos()) {
            // <-- CAMBIO: Llamamos a nuestro método protegido
            ProductoDTO producto = this.findProductoById(idProducto);
            if (producto != null && producto.getPrecio() != null) {
                total += producto.getPrecio();
            }
        }
        carrito.setPrecioTotal(total);
    }

    // --- NUEVO MÉTODO PROTEGIDO Y SU FALLBACK ---

    @CircuitBreaker(name = "productos-service", fallbackMethod = "fallbackFindProducto") // <-- CAMBIO: La protección se mueve aquí
    public ProductoDTO findProductoById(Long idProducto) {
        // Este método ahora es el único que habla con la API de producto
        return productoAPI.getProducto(idProducto);
    }

    public ProductoDTO fallbackFindProducto(Long idProducto, Throwable t) {
        // Este fallback devuelve un producto "fantasma" con precio 0.0
        // para que los cálculos de precio total no fallen.
        return new ProductoDTO(idProducto, "Producto no encontrado", "N/A", 0.0);
    }
}