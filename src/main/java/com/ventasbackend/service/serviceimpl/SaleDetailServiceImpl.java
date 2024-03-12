package com.ventasbackend.service.serviceimpl;

import com.ventasbackend.dto.SaleDetailDTO;
import com.ventasbackend.entity.Product;
import com.ventasbackend.entity.SaleDetail;
import com.ventasbackend.mapper.SaleDetailMapper;
import com.ventasbackend.repository.SaleDetailRepository;
import com.ventasbackend.service.SaleDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleDetailServiceImpl implements SaleDetailService {

    private final SaleDetailRepository saleDetailRepository;
    private final SaleDetailMapper saleDetailMapper;

    public SaleDetailServiceImpl(SaleDetailRepository saleDetailRepository, SaleDetailMapper saleDetailMapper) {
        this.saleDetailRepository = saleDetailRepository;
        this.saleDetailMapper = saleDetailMapper;
    }

    @Override
    public List<SaleDetailDTO> getAllSaleDetails() {
        List<SaleDetail> saleDetails = saleDetailRepository.findAll();
        return saleDetails.stream()
                .map(saleDetailMapper::saleDetailToSaleDetailDTO)
                .collect(Collectors.toList());
    }


    @Override
    public SaleDetailDTO findSaleDetailById(Long saleDetailId) {
        Optional<SaleDetail> saleDetailOptional = saleDetailRepository.findById(saleDetailId);
        return saleDetailOptional.map(saleDetailMapper::saleDetailToSaleDetailDTO).orElse(null);
    }

    @Override
    public SaleDetailDTO saveSaleDetail(SaleDetailDTO saleDetailDTO) {
        SaleDetail saleDetail = saleDetailMapper.saleDetailDTOToSaleDetail(saleDetailDTO);
        SaleDetail savedSaleDetail = saleDetailRepository.save(saleDetail);
        return saleDetailMapper.saleDetailToSaleDetailDTO(savedSaleDetail);
    }

    @Override
    public void deleteSaleDetail(Long saleDetailId) {
        saleDetailRepository.deleteById(saleDetailId);
    }

    @Override
    public Product findProductById(Long productId) {
        return saleDetailRepository.findProductSaleDetailById(productId).orElse(null);
    }
}
