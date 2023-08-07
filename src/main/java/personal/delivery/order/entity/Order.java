package personal.delivery.order.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.base.BaseEntity;
import personal.delivery.constant.OrderStatus;
import personal.delivery.member.entity.Member;
import personal.delivery.shop.entity.Shop;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderTime;

    private LocalTime estimatedArrivalTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member-id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderMenu> orderMenuList;

    @Column(nullable = false)
    private Integer totalOrderPrice;

    @Column(length = 200)
    private String orderRequest;

    @Column(length = 200)
    private String deliveryRequest;

    @Builder
    private Order(LocalDateTime orderTime, OrderStatus orderStatus, Shop shop, Member member,
                  List<OrderMenu> orderMenuList, int totalOrderPrice, String orderRequest,
                  String deliveryRequest, LocalDateTime registeredTime, LocalDateTime updatedTime) {

        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.shop = shop;
        this.member = member;
        this.orderMenuList = orderMenuList;
        this.totalOrderPrice = totalOrderPrice;
        this.orderRequest = orderRequest;
        this.deliveryRequest = deliveryRequest;

        setRegisteredTime(registeredTime);

        setUpdatedTime(updatedTime);

    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void updateEstimatedArrivalTime(Integer estimatedRequiredTime) {
        estimatedArrivalTime = LocalTime.now().plusMinutes(estimatedRequiredTime);
    }

}
