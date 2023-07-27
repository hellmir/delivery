package personal.delivery.cart.service;

import personal.delivery.cart.dto.CartMenuRequestDto;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.entity.Cart;
import personal.delivery.cart.entity.CartMenu;
import personal.delivery.menu.Menu;
import personal.delivery.order.dto.OrderResponseDto;

import java.util.List;

public interface CartMenuService {

    CartMenu createCartMenu(Cart cart, Menu menu, int menuQuantity);

    void addMenuQuantity(int menuQuantity);

    List<CartMenuResponseDto> getCartMenuList();

    CartMenuResponseDto getCartMenu(Long id);

    void deleteCartMenu(CartMenuRequestDto cartMenuRequestDto);

    OrderResponseDto orderCartMenu(CartMenuRequestDto cartMenuRequestDto);

}
