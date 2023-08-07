package personal.delivery.shop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.constant.Role;
import personal.delivery.member.entity.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.shop.dto.ShopRequestDto;
import personal.delivery.shop.dto.ShopResponseDto;
import personal.delivery.shop.entity.Shop;
import personal.delivery.shop.repository.ShopRepository;

import java.util.ArrayList;
import java.util.List;

import static personal.delivery.constant.Role.CUSTOMER;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;

    @Override
    public ShopResponseDto saveShop(ShopRequestDto shopRequestDto) {

        Member member = memberRepository.findByEmail(shopRequestDto.getEmail());

        validateMemberIsExist(shopRequestDto, member);

        validateAuthority(member);

        validateShopIsExist(shopRequestDto);

        Shop shop = Shop.builder()
                .name(shopRequestDto.getName())
                .member(member)
                .build();

        Shop savedShop = shopRepository.save(shop);

        return setShopResponseDto(savedShop);

    }

    @Override
    public List<ShopResponseDto> getAllShops() {

        List<Shop> shopList = shopRepository.findAll();

        List<ShopResponseDto> shopResponseDtoList = new ArrayList<>();

        for (Shop shop : shopList) {
            shopResponseDtoList.add(setShopResponseDto(shop));
        }

        return shopResponseDtoList;

    }

    @Override
    public List<ShopResponseDto> getDistinctShops(String searchWord) {

        List<Shop> shopList = shopRepository.findByNameContaining(searchWord);

        if (shopList.isEmpty()) {
            throw new EntityNotFoundException("해당 검색어를 포함하는 가게가 없습니다. (name: " + searchWord + ")");
        }

        List<ShopResponseDto> shopResponseDtoList = new ArrayList<>();

        for (Shop shop : shopList) {
            shopResponseDtoList.add(setShopResponseDto(shop));
        }

        return shopResponseDtoList;

    }

    private void validateMemberIsExist(ShopRequestDto shopRequestDto, Member member) {

        if (member == null) {
            throw new EntityNotFoundException("해당 회원을 찾을 수 없습니다. (email: " + shopRequestDto.getEmail() + ")");
        }

    }

    private void validateAuthority(Member member) {

        if (member.getRole().equals(CUSTOMER)) {
            throw new IllegalArgumentException("권한이 없습니다. (role: " + member.getRole() + ")");
        }

    }

    private void validateShopIsExist(ShopRequestDto shopRequestDto) {

        Shop shop = shopRepository.findByName(shopRequestDto.getName());

        if (shop != null) {
            throw new IllegalStateException("해당 가게가 이미 존재합니다. (shopName: " + shopRequestDto.getName() + ")");
        }

    }


    private ShopResponseDto setShopResponseDto(Shop shop) {

        ShopResponseDto shopResponseDto = new ShopResponseDto();

        shopResponseDto.setId(shop.getId());
        shopResponseDto.setName(shop.getName());
        shopResponseDto.setEmail(shop.getMember().getEmail());
        shopResponseDto.setRegisteredTime(shop.getRegisteredTime());
        shopResponseDto.setUpdatedTime(shop.getUpdatedTime());

        return shopResponseDto;

    }


}
