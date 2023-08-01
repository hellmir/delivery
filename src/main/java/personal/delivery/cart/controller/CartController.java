package personal.delivery.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.dto.CartRequestDto;
import personal.delivery.cart.dto.CartResponseDto;
import personal.delivery.cart.service.CartMenuServiceImpl;
import personal.delivery.cart.service.CartService;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.validation.group.OnCreate;

import java.net.URI;

@RestController
@RequestMapping("members/{memberId}/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartMenuServiceImpl cartMenuService;

    @PostMapping()
    public ResponseEntity<CartResponseDto> addCartMenu
            (@PathVariable Long memberId,
             @Validated(OnCreate.class) @RequestBody CartRequestDto cartRequestDto) {

        CartResponseDto cartMenuResponseDto = cartService.addCart(memberId, cartRequestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cartMenuResponseDto.getCartId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.created(location).headers(headers).body(cartMenuResponseDto);

    }

    @GetMapping("{id}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable Long id) {

        CartResponseDto cartResponseDto = cartService.getCart(id);

        return ResponseEntity.status(HttpStatus.OK).body(cartResponseDto);

    }

    @GetMapping("cart-menus/{cartMenuId}")
    public ResponseEntity<CartMenuResponseDto> getCartMenu(@PathVariable Long cartMenuId) {
        CartMenuResponseDto cartMenuResponseDto = cartMenuService.getCartMenu(cartMenuId);

        return ResponseEntity.status(HttpStatus.OK).body(cartMenuResponseDto);
    }

    @DeleteMapping("cart-menus/{cartMenuId}")
    public ResponseEntity<CartResponseDto> deleteCartMenu
            (@PathVariable Long cartMenuId, @RequestBody CartRequestDto cartRequestDto) {

        cartMenuService.deleteCartMenu(cartMenuId, cartRequestDto);

        return ResponseEntity.noContent().build();

    }

    @PostMapping("orders")
    public ResponseEntity<OrderResponseDto> orderCartMenu
            (@Valid @RequestBody CartRequestDto cartRequestDto) {

        OrderResponseDto orderResponseDto = cartMenuService.orderCartMenu(cartRequestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderResponseDto.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.created(location).headers(headers).body(orderResponseDto);

    }

}
