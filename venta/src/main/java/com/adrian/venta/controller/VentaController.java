package com.adrian.venta.controller;

import com.adrian.venta.dto.VentaDTO;
import com.adrian.venta.model.Venta;
import com.adrian.venta.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ventas") // Asegúrate de que el controlador tenga un RequestMapping base
public class VentaController {

    @Autowired
    private IVentaService ventaService;

    @PostMapping("/crear")
    public ResponseEntity<String> saveVenta(@RequestBody Venta venta) {
        ventaService.saveVenta(venta);
        return ResponseEntity.status(HttpStatus.OK).body("Venta creada exitosamente");
    }

    // CORRECCIÓN: Añadir la anotación @GetMapping con la ruta correcta
    @GetMapping("/{idVenta}")
    public ResponseEntity<VentaDTO> findVenta(@PathVariable Long idVenta) {
        VentaDTO venta = ventaService.findVenta(idVenta);
        if (venta != null) {
            return ResponseEntity.ok(venta);
        }
        return ResponseEntity.notFound().build();
    }

    // Asumiendo que tienes estos métodos en tu controlador también
    @GetMapping
    public ResponseEntity<List<VentaDTO>> findAllVentas() {
        List<VentaDTO> ventas = ventaService.findAllVentas();
        return ResponseEntity.ok(ventas);
    }

    @DeleteMapping("/eliminar/{idVenta}")
    public ResponseEntity<String> deleteVenta(@PathVariable Long idVenta) {
        ventaService.deleteVenta(idVenta);
        return ResponseEntity.ok("Venta eliminada correctamente");
    }

    @PutMapping("/editar/{idVenta}")
    public ResponseEntity<Void> editVenta(@PathVariable Long idVenta, @RequestBody Venta venta) {
        ventaService.editVenta(idVenta, venta);
        return ResponseEntity.ok().build();
    }
}
