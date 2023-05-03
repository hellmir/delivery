package personal.delivery.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.delivery.cart.entity.Cart;
import personal.delivery.cart.entity.CartMenu;
import personal.delivery.menu.Menu;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartMenuService {

    private final CartMenu cartMenu;

    public CartMenu createCartMenu(Cart cart, Menu menu, int menuQuantity) {

        cartMenu.createCartMenu(cart, menu, LocalDateTime.now());

        return cartMenu;

    }

    public void addMenuQuantity(int menuQuantity) {

        cartMenu.updateMenuQuantity(cartMenu.getMenuQuantity() + menuQuantity, LocalDateTime.now());

    }
}
