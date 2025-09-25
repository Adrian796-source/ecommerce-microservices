package com.adrian.producto.service;

import com.adrian.producto.dto.ProductoDTO;
import java.util.List;

public interface IProductoService {


    List<ProductoDTO> getProductos();


    ProductoDTO getProducto(Long idProducto);


    void createProducto(ProductoDTO producto);


    void editProducto(Long idProducto, ProductoDTO producto);


    void deleteProducto(Long idProducto);
}