package personal.delivery.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.cart.dto.CartMenuRequestDto;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.entity.Cart;
import personal.delivery.cart.entity.CartMenu;
import personal.delivery.cart.repository.CartMenuRepository;
import personal.delivery.cart.repository.CartRepository;
import personal.delivery.configuration.BeanConfiguration;
import personal.delivery.member.entity.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.menu.Menu;
import personal.delivery.menu.repository.MenuRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final MenuRepository menuRepository;
    private final CartRepository cartRepository;
    private final CartMenuRepository cartMenuRepository;
    private final MemberRepository memberRepository;
    private final CartMenuService cartMenuService;
    private final BeanConfiguration beanConfiguration;

    @Override
    public Cart createCart(Member member) {

        Cart cart = Cart.builder()
                .member(member)
                .build();

        return cart;

    }

    @Override
    public CartMenuResponseDto addCart(CartMenuRequestDto cartMenuRequestDto) {

        Menu menu = menuRepository.findById(cartMenuRequestDto.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException
                        ("해당 메뉴를 찾을 수 없습니다. menuId: " + cartMenuRequestDto.getMenuId()));

        Member member = memberRepository.findByEmail(cartMenuRequestDto.getEmail());

        validateMemberIsExist(cartMenuRequestDto, member);

        Cart cart = cartRepository.findByMemberId(member.getId());

        Cart existingCart = checkCartIsExist(cart, member);

        CartMenu cartMenu = cartMenuRepository.findByCartIdAndMenuId(existingCart.getId(), menu.getId());

        return checkCartMenuIsExist(cartMenuRequestDto, menu, cart, cartMenu);

    }

    private void validateMemberIsExist(CartMenuRequestDto cartMenuRequestDto, Member member) {

        if (member == null) {
            throw new IllegalArgumentException("해당 회원을 찾을 수 없습니다. email: " + cartMenuRequestDto.getEmail());
        }

    }

    private Cart checkCartIsExist(Cart cart, Member member) {

        if (cart == null) {
            cart = createCart(member);
            cartRepository.save(cart);
        }

        return cart;

    }

    private CartMenuResponseDto checkCartMenuIsExist
            (CartMenuRequestDto cartMenuRequestDto, Menu menu, Cart cart, CartMenu cartMenu) {

        if (cartMenu != null) {

            cartMenuService.addMenuQuantity(cartMenuRequestDto.getMenuQuantity());

            CartMenuResponseDto cartMenuResponseDto = beanConfiguration.modelMapper()
                    .map(cartMenu, CartMenuResponseDto.class);

            return cartMenuResponseDto;

        } else {

            CartMenu newCartMenu = cartMenuService.createCartMenu(cart, menu, cartMenuRequestDto.getMenuQuantity());

            CartMenu savedCartMenu = cartMenuRepository.save(cartMenu);

            CartMenuResponseDto cartMenuResponseDto = beanConfiguration.modelMapper()
                    .map(savedCartMenu, CartMenuResponseDto.class);

            return cartMenuResponseDto;

        }

    }

}
