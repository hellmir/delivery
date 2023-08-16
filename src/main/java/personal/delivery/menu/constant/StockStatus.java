package personal.delivery.menu.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StockStatus {

    AVAILABLE("재고 있음"),
    LOW_STOCK("재고 부족"),
    OUT_OF_STOCK("재고 없음");

    private final String description;

}
