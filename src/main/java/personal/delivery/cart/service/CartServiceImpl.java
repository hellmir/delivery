package personal.delivery.cart.service;

import jakarta.persistence.EntityNotFoundException;
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
import personal.delivery.member.Member;
import personal.delivery.member.repository.MemberRepository;
import personal.delivery.menu.Menu;
import personal.delivery.menu.repository.MenuRepository;
import personal.delivery.order.dto.OrderDto;
import personal.delivery.order.dto.OrderResponseDto;
import personal.delivery.order.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final OrderService orderService;

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
    public OrderResponseDto orderCartMenu(CartMenuDto cartMenuDto) {

        OrderDto orderDto = new OrderDto();

        Optional<CartMenu> cartMenuToOrder = cartMenuRepository.findById(cartMenuDto.getMenuId());

        if (cartMenuToOrder.isPresent()) {

            orderDto.setMenuId(cartMenuDto.getMenuId());
            orderDto.setOrderQuantity(cartMenuToOrder.get().getMenuQuantity());
            orderDto.setEmail(cartMenuDto.getEmail());

        } else {

            throw new EntityNotFoundException();

        }

        OrderResponseDto cartMenuOrder = orderService.takeOrder(orderDto);

        cartMenuRepository.delete(cartMenuToOrder.get());

        return cartMenuOrder;

    }

}
