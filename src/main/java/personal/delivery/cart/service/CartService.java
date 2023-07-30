package personal.delivery.cart.service;

import personal.delivery.cart.dto.CartMenuRequestDto;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.entity.Cart;
import personal.delivery.member.entity.Member;

public interface CartService {

    Cart createCart(Member member);

    CartMenuResponseDto addCart(CartMenuRequestDto cartMenuRequestDto);

}
