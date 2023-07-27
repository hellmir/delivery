package personal.delivery.shop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.constant.Role;
import personal.delivery.member.eneity.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.shop.dto.ShopRequestDto;
import personal.delivery.shop.dto.ShopResponseDto;
import personal.delivery.shop.entity.Shop;
import personal.delivery.shop.repository.ShopRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;
    private final BeanConfiguration beanConfiguration;

    @Override
    public ShopResponseDto saveShop(ShopRequestDto shopDto) {

        Member member = memberRepository.findByEmail(shopDto.getEmail());

        validateMemberIsExist(shopDto, member);

        validateAuthority(member);

        validateShopIsExist(shopDto);

        Shop shop = Shop.builder()
                .name(shopDto.getName())
                .member(member)
                .registrationTime(LocalDateTime.now())
                .build();

        Shop savedShop = shopRepository.save(shop);

        return setShopResponseDto(savedShop);

    }

    @Override
    public List<ShopResponseDto> getAllShop() {

        List<Shop> shopList = shopRepository.findAll();

        List<ShopResponseDto> shopResponseDtoList = new ArrayList<>();

        for (Shop shop : shopList) {
            shopResponseDtoList.add(setShopResponseDto(shop));
        }

        return shopResponseDtoList;

    }

    private void validateMemberIsExist(ShopRequestDto shopDto, Member member) {

        if (member == null) {
            throw new EntityNotFoundException("해당 회원을 찾을 수 없습니다. (email: " + shopDto.getEmail() + ")");
        }

    }

    private void validateAuthority(Member member) {

        if (member.getRole().equals(Role.CUSTOMER)) {
            throw new IllegalArgumentException("권한이 없습니다. (role: " + member.getRole() + ")");
        }

    }

    private void validateShopIsExist(ShopRequestDto shopDto) {

        Shop shop = shopRepository.findByName(shopDto.getName());

        if (shop != null) {
            throw new IllegalStateException("해당 가게가 이미 존재합니다. (shopName: " + shopDto.getName() + ")");
        }

    }


    private ShopResponseDto setShopResponseDto(Shop shop) {

        ShopResponseDto shopResponseDto = new ShopResponseDto();

        shopResponseDto.setId(shop.getId());
        shopResponseDto.setName(shop.getName());
        shopResponseDto.setEmail(shop.getMember().getEmail());
        shopResponseDto.setRegistrationTime(shop.getRegistrationTime());

        return shopResponseDto;

    }


}
