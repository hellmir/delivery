package personal.delivery.cart.service;

import personal.delivery.cart.dto.CartRequestDto;
import personal.delivery.cart.dto.CartResponseDto;

public interface CartService {

    CartResponseDto addCart(Long memberId, CartRequestDto cartRequestDto);

    CartResponseDto getCart(Long id);

}
