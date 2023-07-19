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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 장바구니 메뉴를 찾을 수 없습니다. (cartId: " + id + ")"));

        CartMenuResponseDto cartMenuResponseDto = new CartMenuResponseDto();

        cartMenuResponseDto.setId(selectedCartMenu.getId());
        cartMenuResponseDto.setMenu(selectedCartMenu.getMenu());
        cartMenuResponseDto.setMenuQuantity(selectedCartMenu.getMenuQuantity());

        return cartMenuResponseDto;

    }

    @Override
    public CartMenuResponseDto deleteCartMenu(CartMenuDto cartMenuDto) throws Exception {

        CartMenu cartMenu = cartMenuRepository.findById(cartMenuDto.getCartMenuId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 장바구니 메뉴를 찾을 수 없습니다. (cartId: " + cartMenuDto.getMenuId() + ")"));

        Long cartId = cartMenu.getCart().getId();

        if (cartId == null) {
            throw new EntityNotFoundException
                    ("해당 메뉴의 장바구니를 찾을 수 없습니다. (cartMenuId: "
                            + cartMenuDto.getCartMenuId() + ", cartId: null)");
        }

        if (!cartId.equals(cartMenuDto.getCartId())) {
            throw new EntityNotFoundException
                    ("입력한 장바구니 ID가 삭제 요청 메뉴의 장바구니 ID와 일치하지 않습니다. (입력한 cartId: "
                            + cartMenuDto.getCartId() + ", 삭제 요청 메뉴의 cartID: " + cartId + ")");
        }

        String memberEmail = cartMenu.getCart().getMember().getEmail();

        if (memberEmail == null) {
            throw new EntityNotFoundException
                    ("해당 장바구니의 회원을 찾을 수 없습니다. (cartId: "
                            + cartMenu.getCart().getId() + ", memberEmail: null)");
        } else if (!memberEmail.equals(cartMenuDto.getEmail())) {
            throw new EntityNotFoundException
                    ("삭제 요청 이메일 주소가 해당 장바구니 회원의 이메일 주소와 일치하지 않습니다. (입력한 email: "
                            + cartMenuDto.getEmail() + ", 장바구니 회원 email: " + memberEmail + ")");
        }

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

        CartMenu cartMenuToOrder = cartMenuRepository.findById(cartMenuDto.getCartMenuId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 장바구니 메뉴를 찾을 수 없습니다. (cartMenuId: " + cartMenuDto.getCartMenuId() + ")"));

        String memberEmail = cartMenuToOrder.getCart().getMember().getEmail();

        if (memberEmail == null) {
            throw new EntityNotFoundException
                    ("해당 장바구니의 회원을 찾을 수 없습니다. (cartId: "
                            + cartMenuToOrder.getCart().getId() + ", memberEmail: null)");
        } else if (!memberEmail.equals(cartMenuDto.getEmail())) {
            throw new EntityNotFoundException
                    ("주문 요청 이메일 주소가 해당 장바구니 회원의 이메일 주소와 일치하지 않습니다. (입력한 이메일 주소: "
                            + cartMenuDto.getEmail() + ", 장바구니 회원 이메일 주소: " + memberEmail + ")");
        }

        Map<Long, Integer> menuIdAndQuantityMap = new HashMap<>();
        menuIdAndQuantityMap.put(cartMenuToOrder.getMenu().getId(), cartMenuToOrder.getMenuQuantity());
        orderDto.setMenuIdAndQuantityMap(menuIdAndQuantityMap);

        orderDto.setEmail(memberEmail);

        OrderResponseDto cartMenuOrder = orderService.takeOrder(orderDto);

        cartMenuRepository.delete(cartMenuToOrder);

        return cartMenuOrder;

    }

}
