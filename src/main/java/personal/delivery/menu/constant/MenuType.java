package personal.delivery.menu.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MenuType {

    MAIN("메인 메뉴"),
    SIDE("사이드 메뉴"),
    DESSERT("후식"),
    BEVERAGE("음료"),
    DRINK("주류");

    private final String description;

}
