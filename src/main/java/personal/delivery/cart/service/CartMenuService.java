package personal.delivery.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.delivery.cart.entity.Cart;
import personal.delivery.cart.entity.CartMenu;
import personal.delivery.menu.Menu;

@Service
@RequiredArgsConstructor
public class CartMenuService {

    private final CartMenu cartMenu;

    public CartMenu createCartMenu(Cart cart, Menu menu, int menuQuantity) {

        cartMenu.setCart(cart);
        cartMenu.setMenu(menu);
        cartMenu.setMenuQuantity(menuQuantity);

        return cartMenu;

    }

    public void addMenuQuantity(int menuQuantity) {

        cartMenu.setMenuQuantity(cartMenu.getMenuQuantity() + menuQuantity);

    }
}
