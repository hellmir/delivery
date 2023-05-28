package personal.delivery.cart.service;

import personal.delivery.cart.dto.CartMenuDto;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.dto.CartOrderDto;
import personal.delivery.cart.entity.Cart;
import personal.delivery.member.Member;
import personal.delivery.order.dto.OrderResponseDto;

import java.util.List;

public interface CartService {

    Cart createCart(Member member);

    CartMenuResponseDto addCart(CartMenuDto cartMenuDto);

    List<CartMenuResponseDto> getCartMenuList();

    OrderResponseDto orderCartMenu(CartOrderDto cartOrderDto);

}
