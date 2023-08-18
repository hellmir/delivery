package personal.delivery.shop.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import personal.delivery.member.constant.Role;
import personal.delivery.member.entity.Address;
import personal.delivery.member.entity.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.menu.repository.MenuRepository;
import personal.delivery.shop.entity.Shop;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static personal.delivery.member.constant.Role.SELLER;
import static personal.delivery.test_util.TestObjectFactory.createMember;
import static personal.delivery.test_util.TestObjectFactory.createShop;

@ActiveProfiles("test")
@SpringBootTest
public class ShopRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("가게 이름을 통해 특정 가게를 검색할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "동네 맛집, 홍길동, abcd@abc.com, 1234, SELLER",
            "맛있는 김치찌개, 고길동, abcd@abcd.com, 1234, CUSTOMER",
            "맛있는 삼겹살, 김길동, abcd@abcde.com, 1234, SELLER",
    })
    void findByName(String shopName, String memberName, String email, String password, String role) {

        // given

        Member member = Member.builder()
                .name(memberName)
                .email(email)
                .password(password)
                .address(mock(Address.class))
                .role(Role.valueOf(role))
                .build();

        memberRepository.save(member);

        Shop shop = Shop.builder()
                .name(shopName)
                .member(member)
                .build();

        shopRepository.save(shop);

        // when
        Shop findedShop = shopRepository.findByName(shopName);

        // then
        assertThat(findedShop.getId()).isEqualTo(shop.getId());

    }

    @DisplayName("일부 키워드를 통해 가게들을 검색할 수 있다.")
    @Test
    void findByNameContaining() {

        // given

        Member member1 = createMember("홍길동", "abcd@abc.com", "1234", SELLER);

        Member member2 = createMember("고길동", "abcd@abcd.com", "1234", SELLER);

        memberRepository.saveAll(List.of(member1, member2));

        Shop shop1 = createShop("동네 맛집", member1);

        Shop shop2 = createShop("맛있는 김치찌개", member2);

        Shop shop3 = createShop("맛있는 삼겹살", member1);

        shopRepository.saveAll(List.of(shop1, shop2, shop3));

        // when

        List<Shop> findedShopList1 = shopRepository.findByNameContaining("맛");

        List<Shop> findedShopList2 = shopRepository.findByNameContaining("맛있는");

        List<Shop> findedShopList3 = shopRepository.findByNameContaining("김치");

        // then

        assertThat(findedShopList1).hasSize(3)
                .extracting("name")
                .containsExactlyInAnyOrder(
                        "동네 맛집",
                        "맛있는 김치찌개",
                        "맛있는 삼겹살"
                );

        assertThat(findedShopList2).hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder(
                        "맛있는 김치찌개",
                        "맛있는 삼겹살"
                );

        assertThat(findedShopList3).hasSize(1)
                .extracting("name")
                .containsExactlyInAnyOrder(
                        "맛있는 김치찌개"
                );

    }

}