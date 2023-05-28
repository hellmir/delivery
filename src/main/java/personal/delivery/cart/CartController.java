package personal.delivery.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.delivery.cart.dto.CartMenuDto;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.dto.CartOrderDto;
import personal.delivery.cart.service.CartService;
import personal.delivery.order.dto.OrderResponseDto;

import java.util.List;

@RestController
@RequestMapping("carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping()
    public ResponseEntity<CartMenuResponseDto> addCartMenu(@RequestBody CartMenuDto cartMenuDto) {

        CartMenuResponseDto cartMenuResponseDto = cartService.addCart(cartMenuDto);

        return ResponseEntity.status(HttpStatus.OK).body(cartMenuResponseDto);

    }

    @GetMapping()
    public ResponseEntity<List<CartMenuResponseDto>> getCartMenuList() {

        List<CartMenuResponseDto> cartMenuResponseDtoList = cartService.getCartMenuList();

        return ResponseEntity.status(HttpStatus.OK).body(cartMenuResponseDtoList);

    }

    @PostMapping("orders")
    public ResponseEntity<OrderResponseDto> orderCartMenu(@RequestBody CartOrderDto cartOrderDto) {

        OrderResponseDto orderResponseDto = cartService.orderCartMenu(cartOrderDto);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);

    }

}
