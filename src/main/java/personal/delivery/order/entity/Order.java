package personal.delivery.order.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.constant.OrderStatus;
import personal.delivery.member.eneity.Member;

import java.time.LocalDateTime;
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
    private List<OrderMenu> orderMenuList;

    private int totalOrderPrice;

    @Column(length = 200)
    private String orderRequest;

    @Column(length = 200)
    private String deliveryRequest;

    private LocalDateTime registrationTime;
    private LocalDateTime updateTime;

    @Builder
    public Order(LocalDateTime orderTime, OrderStatus orderStatus, Member member, List<OrderMenu> orderMenuList,
                 int totalOrderPrice, String orderRequest, String deliveryRequest, LocalDateTime registrationTime) {

        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.member = member;
        this.orderMenuList = orderMenuList;
        this.totalOrderPrice = totalOrderPrice;
        this.orderRequest = orderRequest;
        this.deliveryRequest = deliveryRequest;
        this.registrationTime = registrationTime;

    }

    public void updateOrderStatus(OrderStatus orderStatus, LocalDateTime updateTime) {

        this.orderStatus = orderStatus;
        this.updateTime = updateTime;

    }

}
