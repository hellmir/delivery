package personal.delivery.cart.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.dto.CartRequestDto;
import personal.delivery.cart.entity.Cart;
import personal.delivery.cart.entity.CartMenu;
import personal.delivery.cart.repository.CartMenuRepository;
import personal.delivery.menu.entity.Menu;
import personal.delivery.order.dto.OrderRequestDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.service.OrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartMenuServiceImpl implements CartMenuService {

    private final CartMenuRepository cartMenuRepository;
    private final OrderService orderService;

    @Override
    public List<CartMenu> createCartMenuOrAddQuantity(Map<Menu, Integer> menuToAddCartMap) {

        List<CartMenu> cartMenuList = new ArrayList<>();

        for (Menu menuToAddCart : menuToAddCartMap.keySet()) {

            CartMenu cartMenu = cartMenuRepository.findByMenuId(menuToAddCart.getId());

            if (cartMenu == null) {
                cartMenu = CartMenu.createCartMenu(menuToAddCart, menuToAddCart.getPrice());
            }

            cartMenu.addMenuQuantityAndComputeTotalPrice(menuToAddCartMap.get(menuToAddCart));

            CartMenu savedCartMenu = cartMenuRepository.save(cartMenu);

            cartMenuList.add(savedCartMenu);

        }

        return cartMenuList;

    }

    public void updateCartToCartMenu(List<CartMenu> cartMenuList, Cart savedCart) {

        for (CartMenu cartMenu : cartMenuList) {

            cartMenu.updateCart(savedCart);

        }

    }

    @Override
    public CartMenuResponseDto getCartMenu(Long cartMenuId) {

        CartMenu selectedCartMenu = cartMenuRepository.findById(cartMenuId)
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 장바구니 메뉴가 존재하지 않습니다. (cartId: " + cartMenuId + ")"));

        return setCartMenuResponseDto(selectedCartMenu);

    }

    @Override
    public void deleteCartMenu(Long cartMenuId, CartRequestDto cartRequestDto) {

        CartMenu cartMenu = cartMenuRepository.findById(cartMenuId)
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 장바구니 메뉴가 존재하지 않습니다. (cartMenuId: " + cartRequestDto.getCartMenuId() + ")"));
        Long cartId = cartMenu.getCart().getId();

        if (!cartId.equals(cartRequestDto.getCartId())) {
            throw new EntityNotFoundException
                    ("입력한 장바구니 ID가 삭제 요청 메뉴의 장바구니 ID와 일치하지 않습니다. (입력한 cartId: "
                            + cartRequestDto.getCartId() + ", 삭제 요청 메뉴의 cartID: " + cartId + ")");
        }

        Long memberId = cartMenu.getCart().getMember().getId();

        if (memberId == null) {
            throw new EntityNotFoundException
                    ("해당 장바구니의 회원을 찾을 수 없습니다. (cartId: "
                            + cartMenu.getCart().getId() + ", memberId: null)");
        } else if (!memberId.equals(cartRequestDto.getMemberId())) {
            throw new EntityNotFoundException
                    ("삭제 요청 회원의 ID가 해당 장바구니 회원의 ID와 일치하지 않습니다. (요청 ID: "
                            + cartRequestDto.getMemberId() + ", 장바구니 회원 ID: " + memberId + ")");
        }

        cartMenuRepository.delete(cartMenu);

    }

    @Override
    public OrderResponseDto orderCartMenu(CartRequestDto cartRequestDto) {

        OrderRequestDto orderRequestDto = new OrderRequestDto();

        CartMenu cartMenuToOrder = cartMenuRepository.findById(cartRequestDto.getCartMenuId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 장바구니 메뉴가 존재하지 않습니다. (cartMenuId: " + cartRequestDto.getCartMenuId() + ")"));

        Long memberId = cartMenuToOrder.getCart().getMember().getId();

        if (memberId == null) {
            throw new EntityNotFoundException
                    ("해당 장바구니의 회원을 찾을 수 없습니다. (cartId: "
                            + cartMenuToOrder.getCart().getId() + ", memberId: null)");
        } else if (!memberId.equals(cartRequestDto.getMemberId())) {
            throw new EntityNotFoundException
                    ("주문 요청 회원의 ID가 해당 장바구니 회원의 ID와 일치하지 않습니다. (입력한 이메일 주소: "
                            + cartRequestDto.getMemberId() + ", 장바구니 회원 이메일 주소: " + memberId + ")");
        }

        Map<Long, Integer> menuIdAndQuantityMap = new HashMap<>();
        menuIdAndQuantityMap.put(cartMenuToOrder.getMenu().getId(), cartMenuToOrder.getMenuQuantity());
        orderRequestDto.setMenuIdAndQuantityMap(menuIdAndQuantityMap);

        orderRequestDto.setMemberId(orderRequestDto.getMemberId());
        OrderResponseDto cartMenuOrder = orderService.takeOrder(cartRequestDto.getShopId(), orderRequestDto);

        cartMenuRepository.delete(cartMenuToOrder);

        return cartMenuOrder;

    }

    private CartMenuResponseDto setCartMenuResponseDto(CartMenu cartMenu) {

        CartMenuResponseDto cartMenuResponseDto = new CartMenuResponseDto();

        cartMenuResponseDto.setShopName(cartMenu.getCart().getShop().getName());
        cartMenuResponseDto.setCartMenuId(cartMenu.getId());
        cartMenuResponseDto.setMenu(cartMenu.getMenu());
        cartMenuResponseDto.setMenuQuantity(cartMenu.getMenuQuantity());
        cartMenuResponseDto.setTotalCartMenuPrice(cartMenu.getTotalCartMenuPrice());
        cartMenuResponseDto.setRegisteredTime(cartMenu.getRegisteredTime());
        cartMenuResponseDto.setUpdatedTime(cartMenu.getUpdatedTime());

        return cartMenuResponseDto;

    }

}
