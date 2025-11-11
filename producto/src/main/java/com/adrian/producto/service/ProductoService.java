package com.adrian.producto.service;

import com.adrian.producto.dto.ProductoDTO;
import com.adrian.producto.model.Producto;
import com.adrian.producto.repository.IProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService implements IProductoService {


    private final IProductoRepository productoRepository;

    @Override
    public List<ProductoDTO> getProductos() {
        // 1. Se obtienen las entidades desde la BD
        List<Producto> listaProductos = productoRepository.findAll();
        // 2. Se mapea cada entidad a un DTO y se devuelve la lista de DTOs
        return listaProductos.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public ProductoDTO getProducto(Long idProducto) {
        // Se busca la entidad y se mapea a DTO antes de devolverla
        return productoRepository.findById(idProducto)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    public void createProducto(ProductoDTO productoDto) {
        // Se convierte el DTO a entidad para poder guardarlo
        Producto producto = new Producto();
        producto.setNombre(productoDto.getNombre());
        producto.setMarca(productoDto.getMarca());
        producto.setPrecio(productoDto.getPrecio());
        productoRepository.save(producto);
    }

    @Override
    public void editProducto(Long idProducto, ProductoDTO productoDto) {
        // Se busca el producto existente por su ID
        Producto prod = productoRepository.findById(idProducto).orElse(null);

        if (prod != null) {
            // Se actualizan los datos de la entidad con la información del DTO
            prod.setNombre(productoDto.getNombre());
            prod.setMarca(productoDto.getMarca());
            prod.setPrecio(productoDto.getPrecio());

            // Se guarda la entidad actualizada
            productoRepository.save(prod);
        }
    }

    @Override
    public void deleteProducto(Long idProducto) {
        productoRepository.deleteById(idProducto);
    }

    // Método privado para la conversión de Entidad a DTO
    private ProductoDTO convertToDto(Producto producto) {
        return new ProductoDTO(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getMarca(),
                producto.getPrecio()
        );
    }
}