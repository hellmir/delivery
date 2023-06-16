package personal.delivery.cart.service;

import personal.delivery.cart.dto.CartMenuDto;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.entity.Cart;
import personal.delivery.member.Member;
import personal.delivery.order.dto.OrderResponseDto;

public interface CartService {

    Cart createCart(Member member);

    CartMenuResponseDto addCart(CartMenuDto cartMenuDto);

    OrderResponseDto orderCartMenu(CartMenuDto cartMenuDto);

}
