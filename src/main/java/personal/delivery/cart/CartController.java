package personal.delivery.cart;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import personal.delivery.cart.dto.CartMenuRequestDto;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.service.CartMenuServiceImpl;
import personal.delivery.cart.service.CartService;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.validation.group.OnCreate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartMenuServiceImpl cartMenuService;

    @PostMapping()
    public ResponseEntity<CartMenuResponseDto> addCartMenu(@Validated(OnCreate.class) @RequestBody CartMenuRequestDto cartMenuRequestDto) {

        CartMenuResponseDto cartMenuResponseDto = cartService.addCart(cartMenuRequestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cartMenuResponseDto.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.created(location).headers(headers).body(cartMenuResponseDto);

    }

    @GetMapping()
    public ResponseEntity<List<CartMenuResponseDto>> getCartMenuList() {

        List<CartMenuResponseDto> cartMenuResponseDtoList = cartMenuService.getCartMenuList();

        return ResponseEntity.status(HttpStatus.OK).body(cartMenuResponseDtoList);

    }

    @GetMapping("{id}")
    public ResponseEntity<CartMenuResponseDto> getMenu(@PathVariable Long id) {
        CartMenuResponseDto cartMenuResponseDto = cartMenuService.getCartMenu(id);

        return ResponseEntity.status(HttpStatus.OK).body(cartMenuResponseDto);
    }

    @DeleteMapping()
    public ResponseEntity<CartMenuResponseDto> deleteCartMenu
            (@Valid @RequestBody CartMenuRequestDto cartMenuRequestDto) {

        cartMenuService.deleteCartMenu(cartMenuRequestDto);

        return ResponseEntity.noContent().build();

    }

    @PostMapping("orders")
    public ResponseEntity<OrderResponseDto> orderCartMenu
            (@Valid @RequestBody CartMenuRequestDto cartMenuRequestDto) {

        OrderResponseDto orderResponseDto = cartMenuService.orderCartMenu(cartMenuRequestDto);

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
