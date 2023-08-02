package personal.delivery.cart.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.base.BaseEntity;
import personal.delivery.exception.TryToAddCartOfDifferentShopMenuException;
import personal.delivery.member.entity.Member;
import personal.delivery.shop.entity.Shop;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "cart")
@NoArgsConstructor
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartMenu> cartMenuList;

    @Column
    private int totalCartPrice;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

    @Builder
    private Cart(Shop shop, Member member, List<CartMenu> cartMenuList, int totalCartPrice,
                 LocalDateTime registrationTime, LocalDateTime updateTime) {

        this.shop = shop;
        this.member = member;
        this.cartMenuList = cartMenuList;
        this.totalCartPrice = totalCartPrice;
        this.registrationTime = registrationTime;
        this.updateTime = updateTime;

    }

    public void validateShopIsSameWithExistingOne(Shop shop) {

        if (shop.getId() != this.shop.getId()) {
            throw new TryToAddCartOfDifferentShopMenuException
                    ("한 가게의 메뉴만 장바구니에 추가할 수 있습니다. 추가하려는 메뉴의 shopId: " + shop.getId()
                            + " 기존에 추가된 메뉴의 shopId: " + this.shop.getId());
        }

    }

}
