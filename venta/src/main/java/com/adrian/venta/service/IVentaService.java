package com.adrian.venta.service;

import com.adrian.venta.model.Venta;
import com.adrian.venta.dto.VentaDTO;
import java.util.List;

public interface IVentaService {

    public void saveVenta(Venta venta);

    public VentaDTO findVenta(Long idVenta);

    public List<VentaDTO> findAllVentas();

    public void deleteVenta(Long idVenta);

    public VentaDTO editVenta(Long idVenta, Venta venta);

}
