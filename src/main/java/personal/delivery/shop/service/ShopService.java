package personal.delivery.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.constant.Role;
import personal.delivery.member.eneity.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.shop.dto.ShopDto;
import personal.delivery.shop.dto.ShopResponseDto;
import personal.delivery.shop.entity.Shop;
import personal.delivery.shop.repository.ShopRepository;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final MemberRepository memberRepository;
    private final BeanConfiguration beanConfiguration;

    public ShopResponseDto saveShop(ShopDto shopDto) {

        Member member = memberRepository.findByEmail(shopDto.getEmail());

        if (member.getRole().equals(Role.CUSTOMER)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Shop shop = Shop.builder()
                .name(shopDto.getName())
                .member(member)
                .registrationTime(LocalDateTime.now())
                .build();

        Shop savedShop = shopRepository.save(shop);

        ShopResponseDto shopResponseDto = new ShopResponseDto();

        shopResponseDto.setName(savedShop.getName());
        shopResponseDto.setEmail(savedShop.getMember().getEmail());
        shopResponseDto.setRegistrationTime(savedShop.getRegistrationTime());
        System.out.println(savedShop.getRegistrationTime());

        return shopResponseDto;

    }

}
