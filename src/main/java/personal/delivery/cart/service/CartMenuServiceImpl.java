package personal.delivery.cart.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.delivery.cart.dto.CartMenuDto;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.entity.Cart;
import personal.delivery.cart.entity.CartMenu;
import personal.delivery.cart.repository.CartMenuRepository;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.menu.Menu;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartMenuServiceImpl implements CartMenuService {

    private final CartMenu cartMenu;
    private final CartMenuRepository cartMenuRepository;
    private final BeanConfiguration beanConfiguration;

    @Override
    public CartMenu createCartMenu(Cart cart, Menu menu, int menuQuantity) {

        cartMenu.createCartMenu(cart, menu, menuQuantity, LocalDateTime.now());

        return cartMenu;

    }

    @Override
    public void addMenuQuantity(int menuQuantity) {

        cartMenu.updateMenuQuantity(cartMenu.getMenuQuantity() + menuQuantity, LocalDateTime.now());

    }

    @Override
    public List<CartMenuResponseDto> getCartMenuList() {

        List<CartMenu> cartMenuList = cartMenuRepository.findAll();

        CartMenuResponseDto cartMenuResponseDto = new CartMenuResponseDto();
        List<CartMenuResponseDto> cartMenuResponseDtoList = new ArrayList<>();

        for (CartMenu cartMenu : cartMenuList) {

            cartMenuResponseDto.setId(cartMenu.getId());
            cartMenuResponseDto.setMenu(cartMenu.getMenu());
            cartMenuResponseDto.setMenuQuantity(cartMenu.getMenuQuantity());
            cartMenuResponseDto.setRegistrationTime(cartMenu.getRegistrationTime());
            cartMenuResponseDto.setUpdateTime(cartMenu.getUpdateTime());

            cartMenuResponseDtoList.add(cartMenuResponseDto);

        }

        return cartMenuResponseDtoList;

    }

    @Override
    public CartMenuResponseDto deleteCartMenu(CartMenuDto cartMenuDto) throws Exception {

        Optional<CartMenu> selectedCartMenu = cartMenuRepository.findById(cartMenuDto.getMenuId());
        CartMenu deletedCartMenu;

        if (selectedCartMenu.isPresent()) {
            CartMenu cartMenu = selectedCartMenu.get();
            cartMenuRepository.delete(cartMenu);
            deletedCartMenu = cartMenu;
        } else {
            throw new EntityNotFoundException();
        }

        CartMenuResponseDto cartMenuResponseDto = new CartMenuResponseDto();

        cartMenuResponseDto.setId(deletedCartMenu.getId());
        cartMenuResponseDto.setMenu(deletedCartMenu.getMenu());
        cartMenuResponseDto.setMenuQuantity(deletedCartMenu.getMenuQuantity());
        cartMenuResponseDto.setRegistrationTime(deletedCartMenu.getRegistrationTime());
        cartMenuResponseDto.setUpdateTime(deletedCartMenu.getUpdateTime());

        return cartMenuResponseDto;

    }

}
