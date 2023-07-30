package personal.delivery.order.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.delivery.constant.OrderStatus;
import personal.delivery.member.entity.Member;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Column(nullable = false)
    private LocalDateTime orderTime;

    private LocalTime estimatedArrivalTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

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

    @Column(nullable = false)
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

    public void updateEstimatedArrivalTime(Integer estimatedRequiredTime) {
        estimatedArrivalTime = LocalTime.now().plusMinutes(estimatedRequiredTime);
    }

}
