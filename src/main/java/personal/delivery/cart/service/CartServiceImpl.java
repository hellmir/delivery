package personal.delivery.cart.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.cart.dto.CartRequestDto;
import personal.delivery.cart.dto.CartResponseDto;
import personal.delivery.cart.entity.Cart;
import personal.delivery.cart.entity.CartMenu;
import personal.delivery.cart.repository.CartRepository;
import personal.delivery.member.entity.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.menu.Menu;
import personal.delivery.menu.repository.MenuRepository;
import personal.delivery.shop.entity.Shop;
import personal.delivery.shop.repository.ShopRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final CartRepository cartRepository;
    private final CartMenuService cartMenuService;

    @Override
    public CartResponseDto addCart(Long memberId, CartRequestDto cartRequestDto) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 회원을 찾을 수 없습니다. memberId: " + cartRequestDto.getMemberId()));

        Shop shop = shopRepository.findById(cartRequestDto.getShopId())
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 가게를 찾을 수 없습니다. shopId: " + cartRequestDto.getShopId()));

        Map<Menu, Integer> menuToAddCartMap = createMenuToAddCartMap(cartRequestDto);

        List<CartMenu> cartMenuList = cartMenuService.createCartMenuOrAddQuantity(menuToAddCartMap);

        Cart cart = createCartIfNotExist(shop, member, cartMenuList);

        Cart savedCart = cartRepository.save(cart);

        cartMenuService.updateCartToCartMenu(cartMenuList, savedCart);

        return setCartResponseDto(savedCart);

    }

    @Override
    public CartResponseDto getCart(Long id) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException
                        ("해당 장바구니를 찾을 수 없습니다. (cartId: " + id + ")"));

        return setCartResponseDto(cart);

    }

    private Map<Menu, Integer> createMenuToAddCartMap(CartRequestDto cartRequestDto) {

        Map<Menu, Integer> menuToAddCartMap = new HashMap<>();

        Long shopId = cartRequestDto.getShopId();

        for (Long menuId : cartRequestDto.getMenuIdAndQuantityMap().keySet()) {

            Menu menu = menuRepository.findById(menuId)
                    .orElseThrow(() -> new EntityNotFoundException
                            ("해당 메뉴를 찾을 수 없습니다. (menuId: " + menuId + ")"));

            menuToAddCartMap.put(menu, cartRequestDto.getMenuIdAndQuantityMap().get(menuId));

            validateShopIsExistAndSame(menu, shopId);

        }

        return menuToAddCartMap;

    }

    private void validateShopIsExistAndSame(Menu menu, Long shopId) {

        if (menu.getShop() == null) {
            throw new EntityNotFoundException
                    ("해당 메뉴를 판매하는 가게를 찾을 수 없습니다. (menuId: " + menu.getId() + ")");
        }

        if (!menu.getShop().getId().equals(shopId)) {
            throw new IllegalStateException
                    ("입력된 가게 정보와 입력된 메뉴의 가게 정보가 다릅니다. (입력된 shopId: "
                            + shopId + ", Menu의 shopId: " + menu.getShop().getId() + ")");
        }

    }

    private Cart createCartIfNotExist(Shop shop, Member member, List<CartMenu> cartMenuList) {

        Cart cart = cartRepository.findByMemberId(member.getId());

        if (cart == null) {
            return createCart(shop, member, cartMenuList);
        }

        return cart;

    }

    private Cart createCart(Shop shop, Member member, List<CartMenu> cartMenuList) {

        Cart cart = Cart.builder()
                .shop(shop)
                .member(member)
                .cartMenuList(cartMenuList)
                .totalCartPrice(cartMenuList.stream().mapToInt(CartMenu::getTotalCartMenuPrice).sum())
                .build();

        return cart;

    }

    private CartResponseDto setCartResponseDto(Cart cart) {

        CartResponseDto cartResponseDto = new CartResponseDto();

        cartResponseDto.setCartId(cart.getId());
        cartResponseDto.setShopName(cart.getShop().getName());

        for (CartMenu cartMenu : cart.getCartMenuList()) {

            List<String> menuDetails = new ArrayList<>();

            menuDetails.add("menuName: " + cartMenu.getMenu().getName());
            menuDetails.add("menuPrice: " + cartMenu.getMenu().getPrice());
            menuDetails.add("menuQuantity: " + cartMenu.getMenuQuantity());
            menuDetails.add("cartMenuTotalPrice: " + cartMenu.getTotalCartMenuPrice());

            cartResponseDto.addMenuDetails(menuDetails);

        }

        cartResponseDto.setTotalCartPrice(cart.getTotalCartPrice());
        cartResponseDto.setRegisteredTime(cart.getRegisteredTime());
        cartResponseDto.setUpdatedTime(cart.getUpdatedTime());

        return cartResponseDto;

    }

}
