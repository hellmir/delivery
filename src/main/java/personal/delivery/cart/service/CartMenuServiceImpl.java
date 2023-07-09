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
import personal.delivery.order.dto.OrderDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartMenuServiceImpl implements CartMenuService {

    private final CartMenu cartMenu;
    private final CartMenuRepository cartMenuRepository;
    private final BeanConfiguration beanConfiguration;
    private final OrderService orderService;

    @Override
    public CartMenu createCartMenu(Cart cart, Menu menu, int menuQuantity) {

        CartMenu cartMenu = CartMenu.builder()
                .cart(cart)
                .menu(menu)
                .menuQuantity(menuQuantity)
                .registrationTime(LocalDateTime.now())
                .build();

        return cartMenu;

    }

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
    public CartMenuResponseDto getCartMenu(Long id) {

        CartMenu selectedCartMenu = cartMenuRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        CartMenuResponseDto cartMenuResponseDto = new CartMenuResponseDto();

        cartMenuResponseDto.setId(selectedCartMenu.getId());
        cartMenuResponseDto.setMenu(selectedCartMenu.getMenu());
        cartMenuResponseDto.setMenuQuantity(selectedCartMenu.getMenuQuantity());

        return cartMenuResponseDto;

    }

    @Override
    public CartMenuResponseDto deleteCartMenu(CartMenuDto cartMenuDto) throws Exception {

        CartMenu cartMenu = cartMenuRepository.findById(cartMenuDto.getMenuId())
                .orElseThrow(EntityNotFoundException::new);

        CartMenu deletedCartMenu;

        cartMenuRepository.delete(cartMenu);
        deletedCartMenu = cartMenu;

        CartMenuResponseDto cartMenuResponseDto = new CartMenuResponseDto();

        cartMenuResponseDto.setId(deletedCartMenu.getId());
        cartMenuResponseDto.setMenu(deletedCartMenu.getMenu());
        cartMenuResponseDto.setMenuQuantity(deletedCartMenu.getMenuQuantity());
        cartMenuResponseDto.setRegistrationTime(deletedCartMenu.getRegistrationTime());
        cartMenuResponseDto.setUpdateTime(deletedCartMenu.getUpdateTime());

        return cartMenuResponseDto;

    }

    @Override
    public OrderResponseDto orderCartMenu(CartMenuDto cartMenuDto) {

        OrderDto orderDto = new OrderDto();

        CartMenu cartMenuToOrder = cartMenuRepository.findById(cartMenuDto.getMenuId())
                .orElseThrow(EntityNotFoundException::new);

        orderDto.setMenuId(cartMenuDto.getMenuId());
        orderDto.setOrderQuantity(cartMenuToOrder.getMenuQuantity());
        orderDto.setEmail(cartMenuDto.getEmail());

        OrderResponseDto cartMenuOrder = orderService.takeOrder(orderDto);

        cartMenuRepository.delete(cartMenuToOrder);

        return cartMenuOrder;

    }

}
