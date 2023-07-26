package personal.delivery.shop;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import personal.delivery.shop.dto.ShopDto;
import personal.delivery.shop.dto.ShopResponseDto;
import personal.delivery.shop.service.ShopService;

import java.net.URI;

@RestController
@RequestMapping("shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping()
    public ResponseEntity<ShopResponseDto> createShop(@Valid @RequestBody ShopDto shopDto) {

        ShopResponseDto shopResponseDto = shopService.saveShop(shopDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(shopResponseDto.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.created(location).headers(headers).body(shopResponseDto);

    }

}
