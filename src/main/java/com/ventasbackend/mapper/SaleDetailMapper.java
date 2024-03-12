package com.ventasbackend.mapper;

import com.ventasbackend.dto.SaleDetailDTO;
import com.ventasbackend.entity.SaleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaleDetailMapper {

    private final ProductMapper productMapper;

    @Autowired
    public SaleDetailMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public SaleDetailDTO saleDetailToSaleDetailDTO(SaleDetail saleDetail) {
        SaleDetailDTO saleDetailDTO = new SaleDetailDTO();
        saleDetailDTO.setId(saleDetail.getId());
        saleDetailDTO.setQuantity(saleDetail.getQuantity());
        saleDetailDTO.setUnitPrice(saleDetail.getUnitPrice());
        saleDetailDTO.setSubtotal(saleDetail.getSubtotal());
        saleDetailDTO.setProduct(saleDetail.getProduct());
        return saleDetailDTO;
    }

    public SaleDetail saleDetailDTOToSaleDetail(SaleDetailDTO saleDetailDTO) {
        SaleDetail saleDetail = new SaleDetail();
        saleDetail.setId(saleDetailDTO.getId());
        saleDetail.setQuantity(saleDetailDTO.getQuantity());
        saleDetail.setUnitPrice(saleDetailDTO.getUnitPrice());
        saleDetail.setSubtotal(saleDetailDTO.getSubtotal());
        saleDetail.setProduct(saleDetailDTO.getProduct());
        return saleDetail;
    }
}
