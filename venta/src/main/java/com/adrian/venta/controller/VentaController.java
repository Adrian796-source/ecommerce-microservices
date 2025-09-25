package com.adrian.venta.controller;

import com.adrian.venta.model.Venta;
import com.adrian.venta.dto.VentaDTO;
import com.adrian.venta.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private IVentaService ventaServ;

    @PostMapping("/crear")
    public String saveVenta(@RequestBody Venta venta) {
        ventaServ.saveVenta(venta);
        return "Venta creada exitosamente";
    }

    @GetMapping("/traer/{idVenta}")
    public VentaDTO findVenta(@PathVariable Long idVenta) {
        return ventaServ.findVenta(idVenta);
    }

    @GetMapping("/traer")
    public List<VentaDTO> findAllVentas() {
        return ventaServ.findAllVentas();
    }

    @DeleteMapping("/eliminar/{idVenta}")
    public String deleteVenta(@PathVariable Long idVenta) {
        ventaServ.deleteVenta(idVenta);
        return "Venta eliminada exitosamente";
    }

    @PutMapping("/editar/{idVenta}")
    public VentaDTO editVenta(@PathVariable Long idVenta, @RequestBody Venta venta) {
        return ventaServ.editVenta(idVenta, venta);
    }
}
