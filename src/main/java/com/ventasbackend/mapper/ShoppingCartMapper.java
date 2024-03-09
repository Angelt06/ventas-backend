package com.ventasbackend.mapper;

import com.ventasbackend.dto.ShoppingCartDTO;
import com.ventasbackend.entity.ShoppingCart;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapper {

    public ShoppingCartDTO shoppingCartToShoppingCartDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setId(shoppingCart.getId());
        shoppingCartDTO.setTotalQuantity(shoppingCart.getTotalQuantity());
        shoppingCartDTO.setTotalPrice(shoppingCart.getTotalPrice());
        shoppingCartDTO.setStatusCart(shoppingCart.getStatusCart());
        shoppingCartDTO.setCreationDate(shoppingCart.getCreationDate());
        shoppingCartDTO.setLastUpdate(shoppingCart.getLastUpdate());
        return shoppingCartDTO;
    }

    public ShoppingCart shoppingCartDTOToShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartDTO.getId());
        shoppingCart.setTotalQuantity(shoppingCartDTO.getTotalQuantity());
        shoppingCart.setTotalPrice(shoppingCartDTO.getTotalPrice());
        shoppingCart.setStatusCart(shoppingCartDTO.getStatusCart());
        shoppingCart.setCreationDate(shoppingCartDTO.getCreationDate());
        shoppingCart.setLastUpdate(shoppingCartDTO.getLastUpdate());
        // Aquí no necesitas asignar el usuario, ya que no hay un campo correspondiente en el DTO
        return shoppingCart;
    }
}