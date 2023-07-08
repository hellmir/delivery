package personal.delivery.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.delivery.shop.dto.ShopDto;
import personal.delivery.shop.dto.ShopResponseDto;
import personal.delivery.shop.service.ShopService;

@RestController
@RequestMapping("shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping()
    public ResponseEntity<ShopResponseDto> createShop(@RequestBody ShopDto shopDto) {

        ShopResponseDto shopResponseDto = shopService.saveShop(shopDto);

        return ResponseEntity.status(HttpStatus.OK).body(shopResponseDto);

    }

}
