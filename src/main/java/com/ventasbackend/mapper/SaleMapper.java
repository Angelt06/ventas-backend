package com.ventasbackend.mapper;

import com.ventasbackend.dto.SaleDTO;
import com.ventasbackend.entity.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class SaleMapper {

    private final SaleDetailMapper saleDetailMapper;
    @Autowired
    public SaleMapper(SaleDetailMapper saleDetailMapper) {
        this.saleDetailMapper = saleDetailMapper;
    }

    public SaleDTO saleToSaleDTO(Sale sale) {
        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setId(sale.getId());
        saleDTO.setSaleDate(sale.getSaleDate());
        saleDTO.setTotal(sale.getTotal());
        saleDTO.setSaleStatus(sale.getSaleStatus());
        saleDTO.setUserId(sale.getUser().getId());
        /*saleDTO.setSaleDetails(sale.getSaleDetails().stream()
                .map(saleDetailMapper::saleDetailToSaleDetailDTO)
                .collect(Collectors.toList()));*/

        return saleDTO;
    }

    public Sale saleDTOToSale(SaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setId(saleDTO.getId());
        sale.setSaleDate(saleDTO.getSaleDate());
        sale.setTotal(saleDTO.getTotal());
        sale.setSaleStatus(saleDTO.getSaleStatus());


        // No mapeamos userId ni saleDetails aquí, ya que serán manejados por separado
        return sale;
    }
}
