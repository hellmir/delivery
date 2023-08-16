package personal.delivery.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {

    WAITING("접수 대기"),
    REFUSED("주문 거절"),
    CANCELED("주문 취소"),
    REFUNDED("환불 완료"),
    COOKING("조리 중"),
    IN_DELIVERY("배달 중"),
    DELIVERED("배달 완료"),
    REVIEWED("리뷰 완료");

    private final String description;

}
