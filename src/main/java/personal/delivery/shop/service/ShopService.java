package personal.delivery.shop.service;

import personal.delivery.shop.dto.ShopRequestDto;
import personal.delivery.shop.dto.ShopResponseDto;

import java.util.List;

public interface ShopService {

    ShopResponseDto saveShop(ShopRequestDto shopRequestDto);

    List<ShopResponseDto> getAllShops();

    List<ShopResponseDto> getDistinctShops(String shopName);
}
