package personal.delivery.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.delivery.cart.dto.CartMenuDto;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.service.CartMenuServiceImpl;
import personal.delivery.cart.service.CartService;
import personal.delivery.order.dto.OrderResponseDto;

import java.util.List;

@RestController
@RequestMapping("carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartMenuServiceImpl cartMenuService;

    @PostMapping()
    public ResponseEntity<CartMenuResponseDto> addCartMenu(@RequestBody CartMenuDto cartMenuDto) {

        CartMenuResponseDto cartMenuResponseDto = cartService.addCart(cartMenuDto);

        return ResponseEntity.status(HttpStatus.OK).body(cartMenuResponseDto);

    }

    @GetMapping()
    public ResponseEntity<List<CartMenuResponseDto>> getCartMenuList() {

        List<CartMenuResponseDto> cartMenuResponseDtoList = cartMenuService.getCartMenuList();

        return ResponseEntity.status(HttpStatus.OK).body(cartMenuResponseDtoList);

    }

    @DeleteMapping()
    public ResponseEntity<CartMenuResponseDto> deleteCartMenu
            (@RequestBody CartMenuDto cartMenuDto) throws Exception {

        CartMenuResponseDto cartMenuResponseDto = cartMenuService.deleteCartMenu(cartMenuDto);

        return ResponseEntity.status(HttpStatus.OK).body(cartMenuResponseDto);

    }

    @PostMapping("orders")
    public ResponseEntity<OrderResponseDto> orderCartMenu
            (@RequestBody CartMenuDto cartMenuDto) {

        OrderResponseDto orderResponseDto = cartService.orderCartMenu(cartMenuDto);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);

    }

}
