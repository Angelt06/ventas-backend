package com.ventasbackend.service.serviceimpl;

import com.ventasbackend.dto.SaleDTO;
import com.ventasbackend.entity.Product;
import com.ventasbackend.entity.Sale;
import com.ventasbackend.entity.User;
import com.ventasbackend.mapper.SaleMapper;
import com.ventasbackend.repository.SaleRepository;
import com.ventasbackend.service.SaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public SaleServiceImpl(SaleRepository saleRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
    }

    @Override
    public List<SaleDTO> getAllSales() {
        List<Sale> sales = saleRepository.findAll();
        return sales.stream()
                .map(saleMapper::saleToSaleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<SaleDTO> getAllSales(Pageable pageable) {
        Page<Sale> salesPage = saleRepository.findAll(pageable);
        return salesPage.map(saleMapper::saleToSaleDTO);
    }

    @Override
    public SaleDTO findSaleById(Long saleId) {
        Optional<Sale> saleOptional = saleRepository.findById(saleId);
        return saleOptional.map(saleMapper::saleToSaleDTO).orElse(null);
    }

    @Override
    @Transactional
    public SaleDTO saveSale(SaleDTO saleDTO) {
        logger.info("Inicio del método saveSale()");
        Sale sale = saleMapper.saleDTOToSale(saleDTO);
        logger.info("Mapeado correctamente");
        User user = saleRepository.findUserSaleById(saleDTO.getUserId()).orElse(null);
        logger.info("El nombre del usuario es: {}", user.getUsername());
        sale.setUser(user);
        Sale savedSale = saleRepository.save(sale);
        return saleMapper.saleToSaleDTO(savedSale);
    }

    @Override
    public void deleteSale(Long saleId) {
        saleRepository.deleteById(saleId);
    }

}