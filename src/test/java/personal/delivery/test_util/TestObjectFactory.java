package personal.delivery.test_util;

import personal.delivery.member.constant.Role;
import personal.delivery.member.entity.Address;
import personal.delivery.member.entity.Member;
import personal.delivery.menu.constant.MenuType;
import personal.delivery.menu.entity.Menu;
import personal.delivery.shop.entity.Shop;

import java.util.List;

import static org.mockito.Mockito.mock;

public class TestObjectFactory {

    public static Member createMember(String memberName, String email, String password, Role role) {

        return Member.builder()
                .name(memberName)
                .email(email)
                .password(password)
                .address(mock(Address.class))
                .role(role)
                .build();

    }

    public static Shop createShop(String shopName, Member member) {

        return Shop.builder()
                .name(shopName)
                .member(member)
                .build();

    }

    public static Menu createMenu(Shop shop, String menuName, int price, int stock, String flavor, int portions,
                                  int cookingTime, MenuType menuType, String foodType, List<String> menuOptions) {

        return Menu.builder()
                .shop(shop)
                .name(menuName)
                .price(price)
                .salesRate(0)
                .stock(stock)
                .flavor(flavor)
                .portions(portions)
                .cookingTime(cookingTime)
                .menuType(menuType)
                .foodType(foodType)
                .menuOptions(menuOptions)
                .build();

    }

}
