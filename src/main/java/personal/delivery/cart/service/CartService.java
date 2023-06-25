package personal.delivery.cart.service;

import personal.delivery.cart.dto.CartMenuDto;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.entity.Cart;
import personal.delivery.member.domain.Member;

public interface CartService {

    Cart createCart(Member member);

    CartMenuResponseDto addCart(CartMenuDto cartMenuDto);

}
