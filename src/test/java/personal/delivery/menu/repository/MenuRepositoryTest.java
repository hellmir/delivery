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
import static personal.delivery.constant.MenuType.*;

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

        Menu menu1 = Menu.builder()
                .shop(savedShop)
                .name("김치찌개")
                .price(15_000)
                .salesRate(0)
                .stock(30)
                .flavor("매움")
                .portions(2)
                .cookingTime(30)
                .menuType(MAIN)
                .foodType("찌개")
                .menuOptions(menuOptions)
                .build();

        Menu menu2 = Menu.builder()
                .shop(savedShop)
                .name("후라이드 치킨")
                .price(20_000)
                .salesRate(0)
                .stock(50)
                .flavor("짬")
                .portions(1)
                .cookingTime(20)
                .menuType(SIDE)
                .foodType("치킨")
                .menuOptions(menuOptions)
                .build();

        Menu menu3 = Menu.builder()
                .shop(savedShop)
                .name("페퍼로니 피자")
                .price(23_000)
                .salesRate(0)
                .stock(100)
                .flavor("고소함")
                .portions(3)
                .cookingTime(40)
                .menuType(DESSERT)
                .foodType("피자")
                .menuOptions(menuOptions)
                .build();

        menuRepository.saveAll(List.of(menu1, menu2, menu3));

        // when
        List<Menu> menuList = menuRepository.findAllByShop_Id(shop.getId());

        // then
        assertThat(menuList).hasSize(3)
                .extracting("stock", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple(100, "페퍼로니 피자", 23_000),
                        tuple(30, "김치찌개", 15_000),
                        tuple(50, "후라이드 치킨", 20_000)
                );

    }

}
