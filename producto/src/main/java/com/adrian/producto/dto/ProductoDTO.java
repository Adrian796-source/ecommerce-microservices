package com.adrian.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera el constructor sin argumentos
@AllArgsConstructor
public class ProductoDTO {

    private Long idProducto;
    private String nombre;
    private String marca;
    private Double precio; // <-- CAMBIADO a Double

}