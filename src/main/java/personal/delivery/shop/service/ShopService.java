package personal.delivery.shop.service;

import personal.delivery.shop.dto.ShopRequestDto;
import personal.delivery.shop.dto.ShopResponseDto;

import java.util.List;

public interface ShopService {

    ShopResponseDto saveShop(ShopRequestDto shopDto);

    List<ShopResponseDto> getAllShop();

}
