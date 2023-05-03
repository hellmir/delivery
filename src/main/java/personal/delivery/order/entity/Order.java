package personal.delivery.order.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.constant.OrderStatus;
import personal.delivery.member.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    private Integer totalPrice;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

    @Builder
    public Order(LocalDateTime orderTime, OrderStatus orderStatus, Member member,
                 LocalDateTime registrationTime) {

        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.member = member;
        this.registrationTime = registrationTime;

    }

    public void computeTotalPrice(Integer totalPrice) {

        this.totalPrice = totalPrice;

    }

    public void addOrderMenu(OrderMenu orderMenu) {

        orderMenus.add(orderMenu);
        orderMenu.updateOrder(this);

    }

}
