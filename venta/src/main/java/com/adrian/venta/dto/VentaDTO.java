package com.adrian.venta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {

    private Long idVenta;
    private LocalDate fecha;
    private CarritoDTO carrito;
}
