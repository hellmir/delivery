package personal.delivery.cart.service;

import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.dto.CartRequestDto;
import personal.delivery.cart.entity.Cart;
import personal.delivery.cart.entity.CartMenu;
import personal.delivery.menu.entity.Menu;
import personal.delivery.order.dto.OrderResponseDto;

import java.util.List;
import java.util.Map;

public interface CartMenuService {

    List<CartMenu> createCartMenuOrAddQuantity(Map<Menu, Integer> menuToAddCartMap);

    void updateCartToCartMenu(List<CartMenu> cartMenuList, Cart savedCart);

    CartMenuResponseDto getCartMenu(Long id);

    void deleteCartMenu(Long cartMenuId, CartRequestDto cartRequestDto);

    OrderResponseDto orderCartMenu(CartRequestDto cartRequestDto);

}
