package com.adrian.producto.repository;

import com.adrian.producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<Producto, Long> {
}
