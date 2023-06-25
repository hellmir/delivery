package personal.delivery.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.cart.dto.CartMenuDto;
import personal.delivery.cart.dto.CartMenuResponseDto;
import personal.delivery.cart.entity.Cart;
import personal.delivery.cart.entity.CartMenu;
import personal.delivery.cart.repository.CartMenuRepository;
import personal.delivery.cart.repository.CartRepository;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.member.domain.Member;
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
    public CartMenuResponseDto addCart(CartMenuDto cartMenuDto) {

        Menu menu = menuRepository.getById(cartMenuDto.getMenuId());
        Member member = memberRepository.findByEmail(cartMenuDto.getEmail());

        Cart cart = cartRepository.findByMemberId(member.getId());

        if (cart == null) {
            cart = createCart(member);
            cartRepository.save(cart);
        }

        CartMenu savedCartMenu = cartMenuRepository.findByCartIdAndMenuId(cart.getId(), menu.getId());

        if (savedCartMenu != null) {

            cartMenuService.addMenuQuantity(cartMenuDto.getMenuQuantity());

            CartMenuResponseDto cartMenuResponseDto = beanConfiguration.modelMapper()
                    .map(savedCartMenu, CartMenuResponseDto.class);

            return cartMenuResponseDto;

        } else {

            CartMenu cartMenu = cartMenuService.createCartMenu(cart, menu, cartMenuDto.getMenuQuantity());

            CartMenu newCartMenu = cartMenuRepository.save(cartMenu);

            CartMenuResponseDto cartMenuResponseDto = beanConfiguration.modelMapper()
                    .map(newCartMenu, CartMenuResponseDto.class);

            return cartMenuResponseDto;

        }

    }

}
