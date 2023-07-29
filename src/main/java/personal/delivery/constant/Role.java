package personal.delivery.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    ADMINISTRATOR("관리자"),
    SELLER("판매자"),
    CUSTOMER("고객");

    private final String description;

}
