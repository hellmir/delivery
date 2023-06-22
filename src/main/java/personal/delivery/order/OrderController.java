package personal.delivery.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.delivery.order.dto.OrderDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.service.OrderService;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderResponseDto> order(@RequestBody OrderDto orderDto) {
        OrderResponseDto orderResponseDto = orderService.takeOrder(orderDto);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long id) {
        OrderResponseDto orderResponseDto = orderService.gerOrder(id);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

}
