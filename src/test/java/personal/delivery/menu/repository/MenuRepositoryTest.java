package personal.delivery.menu.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.member.entity.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.menu.entity.Menu;
import personal.delivery.shop.entity.Shop;
import personal.delivery.shop.repository.ShopRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static personal.delivery.member.constant.Role.SELLER;
import static personal.delivery.menu.constant.MenuType.*;
import static personal.delivery.test_util.TestObjectFactory.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class MenuRepositoryTest {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("선택한 가게의 모든 메뉴들을 조회한다.")
    @Test
    void findAllByMenuId() {

        // given

        Member member = createMember("홍길동", "abcd@abc.com", "1234", SELLER);

        memberRepository.save(member);

        Shop shop = createShop("동네 맛집", member);

        shopRepository.save(shop);

        List<String> menuOptions = Arrays.asList("순한 맛", "매운 맛");

        Menu menu1 = createMenu(shop, "김치찌개", 15_000, 30,
                "매움", 2, 30, MAIN, "찌개", menuOptions);

        Menu menu2 = createMenu(shop, "후라이드 치킨", 20_000, 50,
                "짭짤함", 1, 20, SIDE, "치킨", menuOptions);

        Menu menu3 = createMenu(shop, "페퍼로니 피자", 23_000, 100,
                "고소함", 3, 40, DESSERT, "피자", menuOptions);

        menuRepository.saveAll(List.of(menu1, menu2, menu3));

        // when
        List<Menu> menus = menuRepository.findAllByShop_Id(shop.getId());

        // then
        assertThat(menus).hasSize(3)
                .extracting("stock", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple(100, "페퍼로니 피자", 23_000),
                        tuple(30, "김치찌개", 15_000),
                        tuple(50, "후라이드 치킨", 20_000)
                );

    }

    @DisplayName("선택한 ID들을 통해 해당하는 메뉴들을 찾을 수 있다.")
    @Test
    void findByIdIn() {

        // given
        Member member = createMember("홍길동", "abcd@abc.com", "1234", SELLER);

        memberRepository.save(member);

        Shop shop = createShop("동네 맛집", member);

        shopRepository.save(shop);

        Menu menu1 = createMenu(shop, "김치찌개", 15_000, 30, "매움",
                2, 30, MAIN, "찌개", Arrays.asList("순한 맛", "매운 맛"));

        Menu menu2 = createMenu(shop, "후라이드 치킨", 20_000, 50, "짭짤함",
                1, 20, SIDE, "치킨", Arrays.asList("순한 맛", "매운 맛"));

        Menu menu3 = createMenu(shop, "페퍼로니 피자", 23_000, 100, "고소함",
                3, 40, DESSERT, "피자", Arrays.asList("순한 맛", "매운 맛"));

        menuRepository.saveAll(List.of(menu1, menu2, menu3));

        // when
        List<Menu> menus = menuRepository.findByIdIn(Arrays.asList(menu1.getId(), menu3.getId()));

        // then
        assertThat(menus).hasSize(2)
                .extracting("stock", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple(100, "페퍼로니 피자", 23_000),
                        tuple(30, "김치찌개", 15_000)
                );

    }

    @DisplayName("선택한 ID들을 통해 해당하는 메뉴들을 삭제할 수 있다.")
    @Test
    void deleteByIdIn() {

        // given

        Member member = createMember("홍길동", "abcd@abc.com", "1234", SELLER);

        memberRepository.save(member);

        Shop shop = createShop("동네 맛집", member);

        shopRepository.save(shop);

        Menu menu1 = createMenu(shop, "김치찌개", 15_000, 30, "매움",
                2, 30, MAIN, "찌개", Arrays.asList("순한 맛", "매운 맛"));

        Menu menu2 = createMenu(shop, "후라이드 치킨", 20_000, 50, "짭짤함",
                1, 20, SIDE, "치킨", Arrays.asList("순한 맛", "매운 맛"));

        Menu menu3 = createMenu(shop, "페퍼로니 피자", 23_000, 100, "고소함",
                3, 40, DESSERT, "피자", Arrays.asList("순한 맛", "매운 맛"));

        Menu menu4 = createMenu(shop, "고추장 삼겹살", 18_000, 120, "맛있음",
                2, 30, MAIN, "돼지고기", Arrays.asList("기본 맛", "매운 맛"));

        menuRepository.saveAll(List.of(menu1, menu2, menu3, menu4));

        // when
        menuRepository.deleteByIdIn(Arrays.asList(menu1.getId(), menu3.getId()));
        List<Menu> menus = menuRepository.findAll();

        // then
        assertThat(menus).hasSize(2)
                .extracting("stock", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple(50, "후라이드 치킨", 20_000),
                        tuple(120, "고추장 삼겹살", 18_000)
                );

    }

}
