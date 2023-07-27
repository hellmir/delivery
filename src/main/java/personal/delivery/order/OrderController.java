package personal.delivery.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import personal.delivery.order.dto.OrderRequestDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.dto.OrderStatusChangeDto;
import personal.delivery.order.service.OrderService;
import personal.delivery.validation.group.OnCreate;

import java.net.URI;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderResponseDto> createOrder(@Validated(OnCreate.class) @RequestBody OrderRequestDto orderRequestDto) {

        OrderResponseDto orderResponseDto = orderService.takeOrder(orderRequestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderResponseDto.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.created(location).headers(headers).body(orderResponseDto);

    }

    @GetMapping()
    public ResponseEntity<OrderResponseDto> getOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {

        OrderResponseDto orderResponseDto = orderService.gerOrder(orderRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);

    }

    @PatchMapping("{id}")
    public ResponseEntity<OrderResponseDto> changeOrderStatus
            (@PathVariable Long id, @Valid @RequestBody OrderStatusChangeDto orderStatusChangeDto) {

        OrderResponseDto orderResponseDto = orderService.changeOrderStatus(id, orderStatusChangeDto);

        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);

    }

}
