package personal.delivery.menu.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import personal.delivery.menu.entity.Menu;
import personal.delivery.shop.entity.Shop;
import personal.delivery.shop.repository.ShopRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static personal.delivery.menu.constant.MenuType.*;
import static personal.delivery.test_util.TestObjectFactory.createMenu;

@ActiveProfiles("test")
@SpringBootTest
public class MenuRepositoryTest {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("선택한 가게의 모든 메뉴들을 조회한다.")
    @Test
    void findAllByMenuId() {

        // given

        Shop shop = new Shop();

        Shop savedShop = shopRepository.save(shop);

        List<String> menuOptions = Arrays.asList("순한 맛", "매운 맛");

        Menu menu1 = createMenu(savedShop, "김치찌개", 15_000, 30,
                "매움", 2, 30, MAIN, "찌개", menuOptions);

        Menu menu2 = createMenu(savedShop, "후라이드 치킨", 20_000, 50,
                "짭짤함", 1, 20, SIDE, "치킨", menuOptions);

        Menu menu3 = createMenu(savedShop, "페퍼로니 피자", 23_000, 100,
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


}
