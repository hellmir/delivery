package personal.delivery.cart.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.cart.entity.Cart;
import personal.delivery.cart.entity.CartMenu;
import personal.delivery.member.entity.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.menu.entity.Menu;
import personal.delivery.menu.repository.MenuRepository;
import personal.delivery.shop.entity.Shop;
import personal.delivery.shop.repository.ShopRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static personal.delivery.member.constant.Role.CUSTOMER;
import static personal.delivery.member.constant.Role.SELLER;
import static personal.delivery.menu.constant.MenuType.*;
import static personal.delivery.test_util.TestObjectFactory.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class CartRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CartMenuRepository cartMenuRepository;

    @Autowired
    private CartRepository cartRepository;

    @DisplayName("회원 ID를 통해 해당 회원의 장바구니를 찾을 수 있다.")
    @Test
    void findByMemberId() {

        // given
        Member member1 = createMember("고길동", "abcde@abc.com", "1234", CUSTOMER);
        Member member2 = createMember("김길동", "abcdef@abc.com", "1234", CUSTOMER);
        Member member3 = createMember("홍길동", "abcd@abc.com", "1234", SELLER);

        memberRepository.saveAll(Arrays.asList(member1, member2, member3));

        Shop shop = createShop("동네 맛집", member3);

        shopRepository.save(shop);

        Menu menu1 = createMenu(shop, "김치찌개", 15_000, 30, "매움",
                2, 30, MAIN, "찌개", Arrays.asList("순한 맛", "매운 맛"));

        Menu menu2 = createMenu(shop, "후라이드 치킨", 20_000, 50, "짭짤함",
                1, 20, SIDE, "치킨", Arrays.asList("순한 맛", "매운 맛"));

        Menu menu3 = createMenu(shop, "페퍼로니 피자", 23_000, 100, "고소함",
                3, 40, DESSERT, "피자", Arrays.asList("순한 맛", "매운 맛"));

        Menu menu4 = createMenu(shop, "고추장 삼겹살", 18_000, 120, "맛있음",
                2, 30, MAIN, "돼지고기", Arrays.asList("기본 맛", "매운 맛"));

        menuRepository.saveAll(Arrays.asList(menu1, menu2, menu3, menu4));

        CartMenu cartMenu1 = createCartMenu(menu1, menu1.getPrice(), 3);
        CartMenu cartMenu2 = createCartMenu(menu2, menu2.getPrice(), 2);
        CartMenu cartMenu3 = createCartMenu(menu3, menu3.getPrice(), 1);
        CartMenu cartMenu4 = createCartMenu(menu4, menu4.getPrice(), 4);

        List<CartMenu> cartMenus1 = Arrays.asList(cartMenu1, cartMenu2, cartMenu3);
        List<CartMenu> cartMenus2 = Arrays.asList(cartMenu3, cartMenu4);

        Cart cart1 = createCart(member1, shop, cartMenus1);
        Cart cart2 = createCart(member2, shop, cartMenus2);

        cartRepository.saveAll(Arrays.asList(cart1, cart2));

        // when
        Cart selectedCart = cartRepository.findByMemberId(member1.getId());

        // then

        assertThat(selectedCart.getTotalCartPrice()).isEqualTo(23_000 + 15_000 * 3 + 20_000 * 2);

        assertThat(selectedCart.getCartMenuList()).hasSize(3)
                .extracting("menuPrice", "menuQuantity", "totalCartMenuPrice")
                .containsExactlyInAnyOrder(
                        tuple(23_000, 1, 23_000),
                        tuple(15_000, 3, 15_000 * 3),
                        tuple(20_000, 2, 20_000 * 2)
                );

    }

}